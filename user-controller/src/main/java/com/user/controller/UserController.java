package com.user.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.common.Constants;
import com.enums.EnumClass;
import com.user.entity.User;
import com.user.service.JdbcTemplateService;
import com.user.service.UserService;
import com.util.UUIDUtils;
import com.util.file.FileUtils;

import redis.clients.jedis.Jedis;



/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserService userService;
	
	@Autowired
	private JdbcTemplateService jdbcTemplateService;
	
	/**
	 * 登录
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public String login(User user,HttpServletRequest request){
		logger.info(JSON.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss"));
		//加入redis校验
		Jedis jedis=null;
		try{
			jedis=new Jedis("127.0.0.1", 6379);
			String userName=jedis.get(user.getUserName());
			if("".equals(userName) || userName==null){
				User resultUser=userService.login(user);
				if(resultUser==null){
					request.setAttribute("user", user);
					request.setAttribute("errorMsg", "用户名或密码错误");
					logger.error("用户名{}或密码错误",user.getUserName());
					return "index";
				}else{
					//redis存储登录人信息
					jedis.set(resultUser.getUserName(), resultUser.getPassword());
					logger.info("用户名[{}]缓存到redis",user.getUserName());
					
					HttpSession session=request.getSession();
					session.setAttribute("currentUser", resultUser);
					return "redirect:/main.jsp";
				}
			}else{
				User resultUser=userService.login(user);
				HttpSession session=request.getSession();
				session.setAttribute("currentUser", resultUser);
				logger.info("用户名[{}]redis已存在登录信息,登录成功,",user.getUserName());
				return "redirect:/main.jsp";
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			jedis.close();
		}
		return "index";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request){
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("currentUser");
		if(user!=null){
			Jedis jedis=new Jedis("127.0.0.1", 6379);
			jedis.set(user.getUserName(), "");
			jedis.close();
		}
		session.setAttribute("currentUser", null);
		return "redirect:/index.jsp";
	}
	
	@RequestMapping(value="/{id}/showUser")
	public String showUser(@PathVariable String id,HttpServletRequest request){
		User u = userService.findById(id);
		request.setAttribute("user", u);
		return "showUser";
	}
	
	@RequestMapping(value="/showHtmlLab")
	public String showHtmlLab(HttpServletRequest request){
		return "showHtmlLab";
	}
	
	@RequestMapping(value="/updateData")
	public String updateData(HttpServletRequest request){
		String sql = request.getParameter("sql");
		try{
			int result=jdbcTemplateService.update(sql);
			System.out.println(result);
			return "redirect:/main.jsp";
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
			return "main";
		}
	}
	
	@RequestMapping(value="/saveImg")
	public String saveImg(String fileUrl,HttpServletRequest request) throws UnsupportedEncodingException{
		File f=new File(fileUrl); 
		if(f.exists()){
			String folder=f.getAbsolutePath().replace("F:\\迅雷下载\\", "");
			for(String path:f.list()){
				try{
					String sql="insert  into `t_img`(`id`,`path`) values ('"+UUIDUtils.get32UUID()+"','"+"/"+folder+"/"+path+"')";
					jdbcTemplateService.update(sql);
				}catch(Exception e){
					e.printStackTrace();
					request.setAttribute("error", e.getMessage());
					return "main";
				}
			}
		}
		return "main";
	}
	
	@RequestMapping(value="/showImg")
	public String showImg(HttpServletRequest request) throws UnsupportedEncodingException{
		baseFilePath="F:\\迅雷下载";
//		File f=new File(baseFilePath);
//		print(f);
//		String[] list=fileName.split(",");
		String sql="select path from t_img";
		List<String> list = jdbcTemplateService.query(sql);
		
		request.setAttribute("baseFilePath", baseFilePath);
		request.setAttribute("list", list);
		request.setCharacterEncoding("UTF-8");
		return "showImg";
	}
	private String fileName="";
	private String baseFilePath="";
	
    public void print(File f){
        if(f!=null){
            if(f.isDirectory()){
                File[] fileArray=f.listFiles();
                if(fileArray!=null){
                    for (int i = 0; i < fileArray.length; i++) {
                        //递归调用
                        print(fileArray[i]);
                    }
                }
            }
            else{
                fileName=fileName+f.getAbsolutePath().replace(baseFilePath, "")+",";
            }
        }
    }
	
	/**
	 * 根据文件相对路径获取文件信息
	 * @param requestData
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getFile")
	public void getFile(String fileUrl, HttpServletRequest request,
			HttpServletResponse response) {
		
		//文件数据		
		byte[] binaryData = null;
		//文件格式
		if(!StringUtils.isBlank(fileUrl)) {
			String fileName = FilenameUtils.getName(fileUrl);
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ fileName + "\"");
		}
		
		//文件存储方式
		String fileStoreway = Constants.Config.FILE_STORAGE_WAY;
		try {
			if(EnumClass.FileAccessEnum.LOCAL.getValue().equals(fileStoreway)) {
				//本地获取时，路径为全路径
				binaryData = FileUtils.getFileDataByPath(baseFilePath+fileUrl);
//				binaryData = FileUtils.getFileDataByPath(Constants.Config.IBIS_FILE_PATH+fileUrl);
			} else if(EnumClass.FileAccessEnum.HTTP.getValue().equals(fileStoreway)) {
				binaryData = FileUtils.getFileDataByHttp(fileUrl);
			} else if(EnumClass.FileAccessEnum.HTTPS.getValue().equals(fileStoreway)) {
				binaryData = FileUtils.getFileDataByHttps(fileUrl);
			}
			//返回文件流
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(binaryData);
			outputStream.flush();
			
		} catch(Exception e) {
			logger.error(e.getMessage());
		}
		
	}
}
