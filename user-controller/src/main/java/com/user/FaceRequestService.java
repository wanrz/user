/**
 * Project Name:IBIS-platform
 * File Name:FaceRequestService.java
 * Package Name:com.cloudwalk.ibis.service.face
 * Date:2015年9月6日下午8:01:45
 * Copyright @ 2015-2015 重庆中科云丛科技有限公司  All Rights Reserved.
 *
 */

package com.user;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * ClassName:FaceRequestService <br/>
 * Description: 提供三种访问方式：webservice,socket,http. <br/>
 * Date: 2015年9月6日 下午8:01:45 <br/>
 *
 * @author zhuyf
 * @version
 * @since JDK 1.7
 * @see
 */
@Service("faceRequestService")
public class FaceRequestService {
	protected static final Logger LOG = Logger.getLogger(FaceRequestService.class);

//	@Autowired
//	private UserService userService;
	
	AddService addService=new AddService();
	UpdateService updateService=new UpdateService();
	QueryService queryService=new QueryService();
	/**
	 * invoke:通过三种方式调用人脸识别接口. <br/>
	 *
	 * @author:朱云飞 Date: 2015年9月6日 下午8:09:50
	 * @param requestWay
	 * @param requeString
	 * @param method
	 * @return
	 * @since JDK 1.7
	 */
	public String invoke(int requestWay, String requeString, String methodName) {

		Object object = null;
		if (requestWay == 1) {
			object = new AddService();
		} else if (requestWay == 2) {
			object = updateService;
		} else if (requestWay == 3) {
			object = queryService;
		}
		
		String retString = null;
		try {
			Method method = object.getClass().getDeclaredMethod(methodName, new Class[]{ String.class });
			method.setAccessible(true);
			
			retString = (String) method.invoke(object, new Object[] { requeString });
		} catch (Exception e) {
			LOG.error("调用接口发生异常，原因：" + e.getMessage(), e);
		}
		
		return retString;
	}
	
	public static void main(String[] args) {
		FaceRequestService frs=new FaceRequestService();
		frs.invoke(1, "测试了", "add");
	}
}

class AddService{
	public String add(){
		return "新增";
	}
}

class UpdateService{
	public String syahello(){
		return "更新";
	}
}

class QueryService{
	public String syahello(){
		return "查询";
	}
}
