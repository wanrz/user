package com.util;

import java.util.UUID;

/**
 * UUID工具类
 * @author Chunjie He
 * @version 1.0
 * @since 1.6
 */
public class UUIDUtils {

	/**
	 * 获取32位UUID
	 * @return
	 */
	public static String get32UUID() {
		return get36UUID().replace("-", "");
	}
	
	/**
	 * 获取32位小写的UUID
	 * @return
	 */
	public static String get32LowerCase() {
		return get32UUID().toLowerCase();
	}
	
	/**
	 * 获取36位UUID
	 * @return
	 */
	public static String get36UUID() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 获取36位小写的UUID
	 * @return
	 */
	public static String get36LowerCase() {
		return get36UUID().toLowerCase();
	}
}
