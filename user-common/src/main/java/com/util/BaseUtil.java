package com.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.common.BaseConstants;
import com.user.entity.User;
/**
 * Project Name：abc-ibis20170115  <br/>
 * File Name：BaseUtil.java  <br/>
 * Package Name：com.cloudwalk.common.util  <br/>
 * Description: 基础工具类.  <br/>
 * @date: 2018年4月11日 下午3:58:11 
 * @version 
 * @since JDK 1.7
 ************************************************************
 *序号       修改时间            修改人           修改内容
 * 1
 ************************************************************
 *@Copyright: @2010-2018 重庆中科云丛科技有限公司  All Rights Reserved.
 */
public class BaseUtil {

	/**
	 * getCurrentUser:获取系统当前登录用户信息 <br/>
	 * 适用条件：获取当前用户信息<br/>
	 * 执行流程：类调用<br/>
	 * 适用条件：获取当前用户信息<br/>
	 *
	 * @param request http请求
	 * @return User 用户信息
	 */

	public static final User getCurrentUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(BaseConstants.USER);
	}

	/**
	 * setCurrentUser:将当前登录用户信息放入session中 <br/>
	 * 适用条件：将当前用户信息放入session中<br/>
	 * 执行流程：类调用<br/>
	 * 适用条件：存入当前用户信息<br/>
	 *
	 * @param request
	 *            http请求
	 * @return User 用户信息
	 */
	public static final void setCurrentUser(HttpServletRequest request, User user) {
		request.getSession().setAttribute(BaseConstants.USER, user);
	}
	
	/**
	 * 读取session的属性值
	 * @param request
	 * @param attrName
	 * @return
	 */
	public static final String getSessionAttr(HttpServletRequest request,String attrName) {
		HttpSession session = request.getSession();
		return (String) session.getAttribute(attrName);
	}
}
