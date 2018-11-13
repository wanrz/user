package com.util;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

/**
 * spring对象工具类
 * @author zhuyf
 *
 */
public class SpringBeanUtil {
	
	/**
	 * spring应用程序上下文
	 */
	public static ApplicationContext context = null;

	/**
	 * 获取spring上下文容器的对象
	 * 该方法不能在对象初始化时使用
	 * @param beanName 对象名称
	 * @param classType 类型
	 * @return
	 */
	public static <T> T getBean(String beanName,Class<T> classType) {
		if(context == null) {
			context = ContextLoader.getCurrentWebApplicationContext();
		}
		if(context == null) { 
			return null;
		}
		return context.getBean(beanName, classType);
	}
	
	/**
	 * 设置应用程序上下文对象
	 * @param context
	 */
	public static void setWebApplicationContext(ApplicationContext context) {
		SpringBeanUtil.context = context;
	}
}
