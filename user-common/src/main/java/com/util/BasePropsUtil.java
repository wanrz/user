package com.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * ClassName: BasePropsUtil<br/>
 * Description: config工具类 读取base-config.properties. <br/>
 * Date: 2017年08月12日 上午10:18:03 <br/>
 *
 * @author Chunjie He
 * @version 1.0.0
 * @since 1.7
 */
public class BasePropsUtil {
	private static Properties props = new Properties();

	static {
		ClassLoader loader = BasePropsUtil.class.getClassLoader();
		InputStream in = loader.getResourceAsStream("spring/base-config.properties");
		
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 根据键获取参数值
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		return props.getProperty(key);
	}
}
