package com.java.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.java.user.entity.User;
import com.java.user.service.UserService;



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
	
	/**
	 * 登录
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public String login(User user,HttpServletRequest request){
		User resultUser=userService.login(user);
		logger.info(JSON.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss"));
		if(resultUser==null){
			request.setAttribute("user", user);
			request.setAttribute("errorMsg", "用户名或密码错误");
			logger.error("用户名{}或密码错误",user.getUserName());
			return "index";
		}else{
			HttpSession session=request.getSession();
			session.setAttribute("currentUser", resultUser);
			logger.info("用户名[{}]登录成功",user.getUserName());
			return "redirect:/success.jsp";
		}
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request){
		HttpSession session=request.getSession();
		session.setAttribute("currentUser", null);
		return "redirect:/index.jsp";
	}
	
	@RequestMapping(value="/{id}/showUser")
	public String showUser(@PathVariable String id,HttpServletRequest request){
		User u = userService.findById(id);
		request.setAttribute("user", u);
		return "showUser";
	}
}
