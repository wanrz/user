package com.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * config工具类, 读取config.properties
 * 
 * @author Chunjie He
 * @version 1.0
 * @since 1.7
 */
public class PropsUtil {
	private static Properties props = new Properties();

	static {
		ClassLoader loader = PropsUtil.class.getClassLoader();
		InputStreamReader reader = null;
		
		try {
			reader = new InputStreamReader(loader.getResourceAsStream("spring/config.properties"), "UTF-8");
			props.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
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
	
	/**
	 * 根据键获取参数值(int)
	 * @param key
	 * @return
	 */
	public static int getIntProperty(String key) {
		return Integer.valueOf(getProperty(key));
	}
}
