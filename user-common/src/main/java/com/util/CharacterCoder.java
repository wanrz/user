/**
 * Project Name:cloudwalk-common
 * File Name:CharacterCoder.java
 * Package Name:cn.cloudwalk.common.net
 * Date:2016年3月25日上午10:43:17
 * Copyright @ 2010-2016 Cloudwalk Information Technology Co.Ltd  All Rights Reserved.
 *
*/

package com.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName:CharacterCoder <br/>
 * Description: CharacterCoder工具类. <br/>
 * Date: 2016年3月25日 上午10:43:17 <br/>
 *
 * @author 陈腾
 * @version 1.0.0
 * @since JDK 1.7
 */
public class CharacterCoder {
	
	private static final String ISO_8859_1 = "ISO-8859-1";
	private static final String UTF_8 = "UTF-8";
	private static Logger logger = LoggerFactory.getLogger(CharacterCoder.class);
	
	private CharacterCoder(){}

  public static String decodeASCII(byte[] src) {
    try {
      return new String(src, "US-ASCII");
    } catch (UnsupportedEncodingException e) {
    	logger.error("decodeASCII", e);
    }
    return null;
  }

  public static String decodeGBK(byte[] src) {
    try {
      return new String(src, "GBK");
    } catch (UnsupportedEncodingException e) {
    	logger.error("decodeGBK", e);
    }
    return null;
  }

  public static String decodeUCS2(byte[] src) {
    return decodeUTF16BE(src);
  }

  /**
   * 将Unicode字符串转成汉字,如将 '\\u6d4b\\u8bd5' 转换为 '测试'
   *
   * @param oldValue String 要转换的字符串
   * @return String
   */
  public static String decodeUnicode(String oldValue) {
    StringBuilder decodeStr = new StringBuilder();
    String[] hexValue = oldValue.split("\\\\u");
    for (int i = 1; i < hexValue.length; i++) {
      String subStr = hexValue[i];
      char c = (char) Integer.parseInt(subStr, 16);
      decodeStr.append(c);
    }
    return decodeStr.toString();
  }

  public static String decodeUrl(String url) {
    try {
      return URLDecoder.decode(url, UTF_8);
    } catch (UnsupportedEncodingException e) {
    	logger.error("decodeUrl", e);
    }
    return null;
  }

  public static String decodeUTF16BE(byte[] src) {
    try {
      return new String(src, "UTF-16BE");
    } catch (UnsupportedEncodingException e) {
    	logger.error("decodeUTF16BE", e);
    }
    return null;
  }

  public static String decodeUTF8(byte[] src) {
    try {
      return new String(src, UTF_8);
    } catch (UnsupportedEncodingException e) {
    	logger.error("decodeUTF8", e);
    }
    return null;
  }

  //UTF-16BE --> ASCII
  public static byte[] encodeASCII(String src) {
    try {
      return src.getBytes("US-ASCII");
    } catch (UnsupportedEncodingException e) {
    	logger.error("encodeASCII", e);
    }
    return new byte[]{};
  }

  //UTF-16BE --> GBK
  public static byte[] encodeGBK(String src) {
    try {
      return src.getBytes("GBK");
    } catch (UnsupportedEncodingException e) {
    	logger.error("encodeGBK", e);
    }
    return new byte[]{};
  }

  //UTF-16BE --> UCS2
  //UTF-16和UCS2都是双字节， 很相似。 在有些情况下UTF-16比UCS2多了两个字节的字序，当然这两个字节的字序对UTF-16是可选的，不是必须的。
  //而UCS2没有字序的规定。FEFF表示BE，FFFE表示LE
  /*
   * UTF-8以字节为编码单元，没有字节序的问题。UTF-16以两个字节为编码单元， 在解释一个UTF-16文本前，首先要弄清楚每个编码单元的字节序。
   * 例如“奎”的Unicode编码是594E，“乙”的Unicode编码是4E59。 如果我们收到UTF-16字节流“594E”，那么这是“奎”还是 “乙”？
   * Unicode规范中推荐的标记字节顺序的方法是BOM(Byte Order Mark). 在UCS编码中有一个叫做"ZERO WIDTH NO-BREAK SPACE"
   * 的字符，它的编码是FEFF。 而FFFE在UCS中是不存在的字符，所以不应该出现在实际传输中。 UCS规范建议我们在传输字节流前，先传输字符
   * "ZERO WIDTH NO-BREAK SPACE"。 这样如果接收者收到FEFF，就表明这个字节流是Big-Endian的；
   * 如果收到FFFE，就表明这个字节流是Little-Endian的。 因此字符"ZERO WIDTH NO-BREAK SPACE"又被称作BOM。
   */
  public static byte[] encodeUCS2(String src) {
    return encodeUTF16BE(src);
  }

  public static String encodeUrl(String url) {
    try {
      return URLEncoder.encode(url, UTF_8);
    } catch (UnsupportedEncodingException e) {
    	logger.error("encodeUrl", e);
    }
    return null;
  }

  //Java class文件是UTF-8, 运行时是UTF-16BE
  //UTF-16BE: 双字节
  //US-ASCII: 单字节
  //GBK: 单字节，双字节
  //GB18030: 单字节，双字节，四字节
  //UTF-8: 单字节，双字节, 三字节

  public static byte[] encodeUTF16BE(String src) {
    try {
      return src.getBytes("UTF-16BE");
    } catch (UnsupportedEncodingException e) {
    	logger.error("encodeUTF16BE", e);
    }
    return new byte[]{};
  }

  public static byte[] encodeUTF8(String src) {
    try {
      return src.getBytes(UTF_8);
    } catch (UnsupportedEncodingException e) {
    	logger.error("encodeUTF8", e);
    }
    return new byte[]{};
  }

  public static String getSystemEncoding() {
    return System.getProperty("file.encoding");
  }

  /**
   * 将字符串转换为unicode标量值,如将 '测试' 转换为 '\u6d4b\u8bd5'
   *
   * @param str String
   * @return String
   */
  public static String getUnicodeEncoding(String str) {
    StringBuilder unicodeEncoding = new StringBuilder();
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      int intValue = c;
      String hexValue = Integer.toHexString(intValue);
      unicodeEncoding.append("\\u").append(hexValue);
    }
    return unicodeEncoding.toString();
  }

  public static String isoToUtf8(String oldValue) {
    String newValue = null;
    try {
      byte[] data = oldValue.getBytes(ISO_8859_1);
      newValue = new String(data, UTF_8);
    } catch (UnsupportedEncodingException e) {
    	logger.error("isoToUtf8", e);
    }
    return newValue;
  }

  public static String toGBK(String value) {
    try {
      return new String(value.getBytes(), "GBK");
    } catch (UnsupportedEncodingException e) {
    	logger.error("toGBK", e);
    }
    return null;
  }

  public static String toUTF8(String oldValue, String encoding) {
    String newValue = null;
    byte[] data = null;
    try {
      data = oldValue.getBytes(encoding);
      newValue = new String(data, UTF_8);
    } catch (UnsupportedEncodingException e) {
    	logger.error("toUTF8", e);
    }
    return newValue;
  }

  public static String utf8ToIso(String oldValue) {
    String newValue = null;
    try {
      byte[] data = oldValue.getBytes(UTF_8);
      newValue = new String(data, ISO_8859_1);
    } catch (UnsupportedEncodingException e) {
    	logger.error("utf8ToIso", e);
    }
    return newValue;
  }

}
