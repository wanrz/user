package com.util.exception;

/**
 * Project Name：abc-ibis20170115  <br/>
 * File Name：ServiceException.java  <br/>
 * Package Name：com.cloudwalk.common.util.exception  <br/>
 * Description: 系统公用服务异常类.  <br/>
 * @date: 2018年4月11日 下午4:14:24 
 * @author 冯德志
 * @version 
 * @since JDK 1.7
 ************************************************************
 *序号       修改时间            修改人           修改内容
 * 1
 ************************************************************
 *@Copyright: @2010-2018 重庆中科云丛科技有限公司  All Rights Reserved.
 */
public class ServiceException extends Exception {
	private static final long serialVersionUID = 2378887843446159790L;
	
	protected String errorMessage;
	
	protected int errorCode;
	
	public ServiceException() {
		super();
	}
	 
    public ServiceException(String errorMessage) {
         this.errorMessage = errorMessage;
    }
    
    public ServiceException(int errorCode,String errorMessage) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
   }
 
    public String toString() {
         return errorMessage;
    }
 
    public String getMessage() {
         return errorMessage;
    }

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
    
    
}
