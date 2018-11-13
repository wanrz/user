package com.utility.recg;

import com.common.Constants;
import com.util.DesUtil;
import com.util.PropsParam;
import com.util.PropsUtil;

/**
 * 对请求数据进行解密操作
 * @author 何春节
 * @version 1.0
 */
public class DataEncryptUtils {
	
	/**
	 * 水印文件保存路径
	 */
	public static String IS_ENCRYPT = PropsUtil.getProperty(PropsParam.IS_ENCRYPT);
	
	/**
	 * 加密秘钥
	 */
	public static String ENCRYPTKEY = PropsUtil.getProperty(PropsParam.ENCRYPT_KEY);
	
	/**
	 * DES解密接受的字符串
	 * @param requestData 请求的报文
	 * @return
	 * @throws Exception 
	 */
	public static String decrypt(String requestData) throws Exception {
		
		if (Constants.PARAM_ISENCRYPT.equals(IS_ENCRYPT)) {
			return DesUtil.decrypt(requestData, ENCRYPTKEY);
		}
		
		return requestData;
	}
	
	/**
	 * 加密
	 * @param requestData
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String requestData) throws Exception {
		if (Constants.PARAM_ISENCRYPT.equals(IS_ENCRYPT)) {
			return DesUtil.encrypt(requestData, ENCRYPTKEY);
		}
		
		return requestData;
	}
}
