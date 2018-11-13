package com.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
 * 访问在当前类声明的private/protected成员变量及private/protected函数的BeanUtils.
 * 注意,因为必须为当前类声明的变量,通过继承获得的protected变量将不能访问, 
 * 需要转型到声明该变量的类才能访问. 反射的其他功能请使用Apache
 * Jarkarta Commons BeanUtils
 */
@SuppressWarnings("rawtypes")
public abstract class BeanUtil {

	/**
	 * 两个对象之间相互拷贝
	 * 
	 * @param target
	 * @param source
	 * @throws RuntimeException
	 */
	public static void copyProperty(Object target, Object source) throws RuntimeException {
		PropertyDescriptor[] propertys = getPropertyDescriptors(source.getClass(), null);
		try {
			for (PropertyDescriptor property : propertys) {

				Method method = property.getReadMethod();
				Object value = method.invoke(source, EMPTY_PARAMS);

				PropertyDescriptor targetProperty = getPropertyDescriptor(property.getName(), target.getClass());
				if (value != null && value.toString().length() > 0 && targetProperty != null) {
					targetProperty.getWriteMethod().invoke(target, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 暴力获取对象变量值,忽略private,protected修饰符的限制.
	 *
	 * @throws NoSuchFieldException
	 *             如果没有该Field时抛出.
	 */
	public static Object forceGetProperty(Object object, String propertyName) throws NoSuchFieldException {
		
		Field field = getDeclaredField(object, propertyName);

		boolean accessible = field.isAccessible();
		field.setAccessible(true);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		field.setAccessible(accessible);
		return result;
	}

	/**
	 * 暴力设置对象变量值,忽略private,protected修饰符的限制.
	 *
	 * @throws NoSuchFieldException
	 *             如果没有该Field时抛出.
	 */
	public static void forceSetProperty(Object object, String propertyName, Object newValue)
			throws NoSuchFieldException {
		
		Field field = getDeclaredField(object, propertyName);
		boolean accessible = field.isAccessible();
		field.setAccessible(true);

		try {
			field.set(object, newValue);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		field.setAccessible(accessible);
	}

	/**
	 * 获得field的getter名称
	 */
	@SuppressWarnings("unchecked")
	public static Method getAccessor(Class type, String fieldName) {
		try {
			return type.getMethod(getAccessorName(type, fieldName));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得field的getter名称
	 */
	public static String getAccessorName(Class type, String fieldName) {
		Assert.hasText(fieldName, "FieldName required");
		Assert.notNull(type, "Type required");

		if (type.getName().equals("boolean")) {
			return "is" + StringUtils.capitalize(fieldName);
		} else {
			return "get" + StringUtils.capitalize(fieldName);
		}
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 *
	 * @throws NoSuchFieldException
	 *             如果没有该Field时抛出.
	 */
	public static Field getDeclaredField(Class clazz, String propertyName) throws NoSuchFieldException {
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(propertyName);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName() + '.' + propertyName);
	}

	/**
	 *获取对象的DeclaredField.
	 *
	 */
	public static Field getDeclaredField(Object object, String propertyName) throws NoSuchFieldException {
		return getDeclaredField(object.getClass(), propertyName);
	}

	/**
	 * 暴力获取当前类声明的private/protected变量
	 */
	public static Object getDeclaredProperty(Object object, Field field) throws IllegalAccessException {
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		Object result = field.get(object);
		field.setAccessible(accessible);
		return result;
	}

	/**
	 * 暴力获取当前类声明的private/protected变量
	 */
	public static Object getDeclaredProperty(Object object, String propertyName)
			throws IllegalAccessException, NoSuchFieldException {
		Field field = object.getClass().getDeclaredField(propertyName);
		return getDeclaredProperty(object, field);
	}

	/**
	 * 按Filed的类型取得Field列表
	 */
	public static List<Field> getFieldsByType(Object object, Class type) {
		ArrayList<Field> list = new ArrayList<Field>();
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getType().isAssignableFrom(type)) {
				list.add(field);
			}
		}
		return list;
	}

	/**
	 * 获取当前类声明的private/protected变量
	 */
	public static Object getPrivateProperty(Object object, String propertyName)
			throws IllegalAccessException, NoSuchFieldException {
		Field field = object.getClass().getDeclaredField(propertyName);
		field.setAccessible(true);
		return field.get(object);
	}

	/**
	 * 获取对象所有属性的名称
	 */
	public static String[] getProperties(Object object) {
		Field[] fields = object.getClass().getDeclaredFields();
		String[] properties = new String[fields.length];
		int i = 0;
		for (Field field : fields) {
			properties[i++] = field.getName();
		}
		return properties;
	}

	/**
	 * 获取属性描述. <br/>
	 * @param property
	 * @param clazz
	 * @return
	 */
	public static PropertyDescriptor getPropertyDescriptor(String property, Class<?> clazz) {
		PropertyDescriptor[] propertyDescriptors = BeanUtil.getPropertyDescriptors(clazz);
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			if (propertyDescriptor.getName().equals(property)) {
				return propertyDescriptor;
			}
		}
		return null;
	}
	
	/**
	 * 获取属性描述. <br/>
	 * @param clazz
	 * @return
	 */
	public static PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			return beanInfo.getPropertyDescriptors();
		} catch (IntrospectionException e) {
			return new PropertyDescriptor[0];
		}
	}

	/**
	 * getPropertyDescriptors
	 */
	public static PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz, String[] ignoreProperties) {
		List<PropertyDescriptor> propertys = new ArrayList<PropertyDescriptor>();
		PropertyDescriptor[] propertyDescriptors = BeanUtil.getPropertyDescriptors(clazz);
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;
		for (PropertyDescriptor property : propertyDescriptors) {
			String key = property.getName();
			if ("class".equals(key) || (ignoreList != null && ignoreList.contains(key))) {
				continue;
			}
			propertys.add(property);
		}
		return propertys.toArray(new PropertyDescriptor[propertys.size()]);
	}

	/**
	 * 按FiledName获得Field的类型.
	 */
	public static Class getPropertyType(Class type, String name) throws NoSuchFieldException {
		return getDeclaredField(type, name).getType();
	}

	/**
	 * 调用当前类声明的private/protected函数
	 */
	public static Object invokePrivateMethod(Object object, String methodName, Object param)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return invokePrivateMethod(object, methodName, new Object[] { param });
	}

	/**
	 * 调用当前类声明的private/protected函数
	 */
	public static Object invokePrivateMethod(Object object, String methodName, Object[] params)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Class[] types = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			types[i] = params[i].getClass();
		}
		Method method = object.getClass().getDeclaredMethod(methodName, types);
		method.setAccessible(true);
		return method.invoke(object, params);
	}

	/**
	 * 暴力设置当前类声明的private/protected变量
	 */
	public static void setDeclaredProperty(Object object, Field field, Object newValue) throws IllegalAccessException {
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		field.set(object, newValue);
		field.setAccessible(accessible);
	}

	/**
	 * 暴力设置当前类声明的private/protected变量
	 */
	public static void setDeclaredProperty(Object object, String propertyName, Object newValue)
			throws IllegalAccessException, NoSuchFieldException {
		Field field = object.getClass().getDeclaredField(propertyName);
		setDeclaredProperty(object, field, newValue);
	}

	/**
	 * 设置当前类声明的private/protected变量
	 */
	public static void setPrivateProperty(Object object, String propertyName, Object newValue)
			throws IllegalAccessException, NoSuchFieldException {
		Field field = object.getClass().getDeclaredField(propertyName);
		field.setAccessible(true);
		field.set(object, newValue);
	}

	/**
	 * 设置对象属性值（自动适配name和object的属性名称大小写）
	 *
	 * @param object
	 * @param name
	 * @param value
	 */
	public static void setProperty(Object object, String name, Object value)
			throws IllegalAccessException, NoSuchFieldException {
		String[] properties = getProperties(object);
		for (String p : properties) {
			if (p.equalsIgnoreCase(name)) {
				setDeclaredProperty(object, p, value);
				break;
			}
		}
	}

	private static final Object[] EMPTY_PARAMS = new Object[0];

}
