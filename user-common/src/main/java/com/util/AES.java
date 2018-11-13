/**  
* <p>Project Name:cweis-web</p>
* <p>File Name: AES.java</p>  
* <p>Package Name:com.cweis.common.util.security </p>  
* @date 2018年10月29日 下午2:33:26  
* <p>Copyright @ 2010-2016 重庆中科云丛科技有限公司  All Rights Reserved.</p>  
*/  
package com.util;

import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

//import org.apache.commons.codec.binary.Base64;
import org.apache.commons.net.util.Base64;
import org.apache.log4j.Logger;

/**
 * <p>ClassName: AES</p>
 * Description:AES/ECB/PKCS5Padding加密工具<br/>
 * @date 2018年10月29日 下午2:39:45 
 * @author yckj0914
 * @version 1.0
 * @since JDK 1.7
 */ 
public class AES {
	protected static final Logger LOGGER = Logger.getLogger(AES.class);

    // 加密
    public static String Encrypt(String content, String key) throws Exception {
        if (key == null) {
            LOGGER.error("Key为空null");
            return "1";
        }
        
        // 判断Key是否为16位
        if (key.length() != 16) {
            LOGGER.error("Key长度不是16位");
            return "2";
        }
        
        //"算法/模式/补码方式"
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        int blockSize = cipher.getBlockSize();
        byte[] dataBytes = content.getBytes("UTF-8");
	    int plaintextLength = dataBytes.length;
	    if (plaintextLength % blockSize != 0) {
	        plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
	    }
	    byte[] plaintext = new byte[plaintextLength];
	    System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
	    
	    SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("utf-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(plaintext);

        //此处使用BASE64做转码功能，同时能起到2次加密的作用。
        return new Base64().encodeToString(encrypted);
    }

    // 解密
    public static String Decrypt(String content, String key) throws Exception {
        try {
            // 判断Key是否正确
            if (key == null) {
            	LOGGER.error("Key为空null");
                return "1";
            }
            // 判断Key是否为16位
            if (key.length() != 16) {
            	LOGGER.error("Key长度不是16位");
                return "2";
            }
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("utf-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new Base64().decode(content);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString.trim();
            } catch (Exception e) {
                LOGGER.error(e.toString());
                return "3";
            }
        } catch (Exception ex) {
        	LOGGER.error(ex.toString());
            return "3";
        }
    }
    
    /**  
  	 * <p>Title: genRandomNum</p>  
  	 * <p>Description:生成8位随机字符串，数字+英文字母 </p>  
  	 */  
  	public static String genRandomNum() {
  		int maxNum = 36;
  		int i;
  		int count = 0;
  		char[] str = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
  				'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
  		StringBuffer pwd = new StringBuffer("");
  		Random r = new Random();
  		while (count < 8) {
  			i = Math.abs(r.nextInt(maxNum));
  			if (i >= 0 && i < str.length) {
  				pwd.append(str[i]);
  				count++;
  			}
  		}
  		return pwd.toString();
  	}

    public static void main(String[] args) throws Exception {
        /*
         * 此处使用AES-128-ECB加密模式，key需要为16位。
         */
        String key = "94C69163DA021234";
        // 需要加密的字串
        String content = "NDExOTE3bm9kZXZpY2Vjd2F1dGhvcml6Zbbn5ubn5+Lq3+bg5efm5ef74ubn4Obg5Yjm5uvl5ubrkeXm5uvl5uai6+Xm5uvl5uTm6+Xm5ufi/ebk5uI=";
        System.out.println(content);
        // 加密
        String enString = AES.Encrypt(content, key);
        System.out.println("加密后的字串是：" + enString.replaceAll("[\b\r\n\t]*", ""));
        // 解密
        String DeString = AES.Decrypt(enString, key);
        System.out.println("解密后的字串是：" + DeString);
    }
    
  
}
