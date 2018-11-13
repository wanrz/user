package com.utility.recg;

import java.util.Map;

import org.apache.commons.net.util.Base64;

import com.alibaba.fastjson.JSONArray;
import com.util.HttpRequestProxy;
import com.util.PropsParam;
import com.util.PropsUtil;

/**
 * Project Name：abc-ibis20170115  <br/>
 * File Name：RecogniseUtils.java  <br/>
 * Package Name：com.cloudwalk.utility.recg  <br/>
 * Description: RecogniseUtils.  <br/>
 * @date: 2018年4月11日 下午7:31:40 
 * @version 
 * @since JDK 1.7
 ************************************************************
 *序号       修改时间            修改人           修改内容
 * 1
 ************************************************************
 *@Copyright: @2010-2018 重庆中科云丛科技有限公司  All Rights Reserved.
 */
public class RecogniseUtils {

	/**
	 * 云之眼APPID
	 */
	private static String EOC_APP_ID;
	
	/**
	 * 云之眼APPS SECRET
	 */
	private static String EOC_APP_SECRET;
	
	/**
	 * 云之眼基础服务地址
	 */
	private static String EOC_SERVICE_URL;
	
	/**
	 * 初始化参数值
	 */
	static {
		EOC_APP_ID = PropsUtil.getProperty(PropsParam.RECOG_APP_ID);
		EOC_APP_SECRET = PropsUtil.getProperty(PropsParam.RECOG_APP_SECRET);
		EOC_SERVICE_URL = PropsUtil.getProperty(PropsParam.RECOG_SERVER_URL);
	}
	
	/**
	 * 将字节数组进行base64编码，然后替换换行和回车等字符
	 * @param str
	 * @return
	 */
	public static String clearBase64String(String str) {
		return str.replaceAll("\r\n","").replaceAll("\r", "").replaceAll("\n", "").replaceAll("\\s", "+");
	}
	
	/**
	 * 将字节数组进行base64编码，然后替换换行和回车等字符
	 * @param b
	 * @return
	 */
	public static String encodeBase64String(byte[] b) {
		return clearBase64String(Base64.encodeBase64String(b));
	}
	
	/**
	 * 对base64编码后的字符串进行反解
	 * @param str
	 * @return
	 */
	public static byte[] decodeBase64(String str) {
		return Base64.decodeBase64(clearBase64String(str));
	}
	
	/**
	 * 发送HTTP的POST请求
	 * @param url
	 * @param params
	 * @return 
	 * @return
	 */
	private static <T> T send(String url, Map<String, String> params, Class<T> clz) {
		params.put("app_id",		EOC_APP_ID);
		params.put("app_secret",	EOC_APP_SECRET);
		
		String ret = HttpRequestProxy.post(EOC_SERVICE_URL + url, params, "UTF-8");
		return JSONArray.parseObject(ret, clz);
	}
	
	
}
