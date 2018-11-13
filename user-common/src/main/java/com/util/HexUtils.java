package com.util;
/**
 * Project Name：abc-ibis20170115  <br/>
 * File Name：HexUtils.java  <br/>
 * Package Name：com.cloudwalk.common.util  <br/>
 * Description: 进制转换.  <br/>
 * @date: 2018年4月11日 下午4:15:20 
 * @version 
 * @since JDK 1.7
 ************************************************************
 *序号       修改时间            修改人           修改内容
 * 1
 ************************************************************
 *@Copyright: @2010-2018 重庆中科云丛科技有限公司  All Rights Reserved.
 */
public class HexUtils {

	/**
	 * byte数组转换成16进制字符串
	 * 
	 * @param src
	 * @return
	 */
	public static String bytesToHex(byte[] src) {
		StringBuilder str = new StringBuilder(); 
		
        if (src == null || src.length <= 0) {     
            return null;     
        }
        
        String hv = null;
        for (int i = 0; i < src.length; i++) {
        	hv = Integer.toHexString(src[i] & 0xFF);
            if (hv.length() < 2) {
            	str.append("0");
            }
            
            str.append(hv);     
        }
        
        return str.toString();     
	}
}
