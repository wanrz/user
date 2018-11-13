package com.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 反射工具类. 提供调用getter/setter方法, 访问私有变量, 调用私有方法,
 * 获取泛型类型Class, 被AOP过的真实类等工具函数.
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class Reflections {
	/**
	 * 将反射时的checked exception转换为unchecked exception.
	 */
//	public static RuntimeException convertReflectionExceptionToUnchecked(
//			Exception e) {
//		if (e instanceof IllegalAccessException
//				|| e instanceof IllegalArgumentException
//				|| e instanceof NoSuchMethodException) {
//			return new IllegalArgumentException(e);
//		} else if (e instanceof InvocationTargetException) {
//			return new RuntimeException(
//					((InvocationTargetException) e).getTargetException());
//		} else if (e instanceof RuntimeException) {
//			return (RuntimeException) e;
//		}
//		return new RuntimeException("Unexpected Checked Exception.", e);
//	}

	/**
	 * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问. 如向上转型到Object仍无法找到, 返回null.
	 */
	public static Field getAccessibleField(final Object obj,
			final String fieldName) {
		Validate.notNull(obj, "object can't be null");
		Validate.notBlank(fieldName, "fieldName can't be blank");
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				makeAccessible(field);
				return field;
			} catch (NoSuchFieldException e) {// NOSONAR
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问. 如向上转型到Object仍无法找到, 返回null.
	 * 匹配函数名+参数类型。 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj,
	 * Object... args)
	 */
	public static Method getAccessibleMethod(final Object obj,
			final String methodName, final Class<?>... parameterTypes) {
		Validate.notNull(obj, "object can't be null");
		Validate.notBlank(methodName, "methodName can't be blank");

		for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType
				.getSuperclass()) {
			try {
				Method method = searchType.getDeclaredMethod(methodName,
						parameterTypes);
				makeAccessible(method);
				return method;
			} catch (NoSuchMethodException e) {
				// Method不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问. 如向上转型到Object仍无法找到, 返回null. 只匹配函数名。
	 * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object...
	 * args)
	 */
	public static Method getAccessibleMethodByName(final Object obj,
			final String methodName) {
		Validate.notNull(obj, "object can't be null");
		Validate.notBlank(methodName, "methodName can't be blank");

		for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType
				.getSuperclass()) {
			Method[] methods = searchType.getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					makeAccessible(method);
					return method;
				}
			}
		}
		return null;
	}

	/**
	 * 通过反射, 获得Class定义中声明的泛型参数的类型, 注意泛型必须定义在父类处 如无法找到, 返回Object.class. eg.
	 * public UserDao extends HibernateDao<User>
	 *
	 * @param clazz
	 *            The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be
	 *         determined
	 */
	public static <T> Class<T> getClassGenricType(final Class clazz) {
		return getClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射, 获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class. 如public UserDao
	 * extends HibernateDao<User,Long>
	 *
	 * @param clazz
	 *            clazz The class to introspect
	 * @param index
	 *            the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be
	 *         determined
	 */
	public static Class getClassGenricType(final Class clazz, final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getSimpleName()
					+ "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			logger.warn("Index: " + index + ", Size of "
					+ clazz.getSimpleName() + "'s Parameterized Type: "
					+ params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getSimpleName()
					+ " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}

	/**
	 * 获取字段名称. <br/>
	 * @param pojoClass
	 * @return
	 */
	public static Set<String> getFieldNames(Class<?> pojoClass) {
		Set<String> propertyNames = new HashSet<String>();
		Class<?> clazz = pojoClass;
		do {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (!Modifier.isStatic(field.getModifiers())) {
					propertyNames.add(field.getName());

				}
			}
			clazz = clazz.getSuperclass();
		} while (clazz != null
				&& !clazz.getSimpleName().equalsIgnoreCase("Object"));
		return propertyNames;
	}
	/**
	 * 获取字段名称. <br/>
	 * @param pojoClass
	 * @return
	 */
	public static Set<String> getFieldNames(Object instance) {
		Class<?> pojoClass = instance.getClass();
		return getFieldNames(pojoClass);
	}

	/**
	 * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
	 */
	public static Object getFieldValue(final Object obj, final String fieldName) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field ["
					+ fieldName + "] on target [" + obj + "]");
		}

		Object result = null;
		try {
			result = field.get(obj);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常{}", e.getMessage());
		}
		return result;
	}
	/**
	 * 获取属性名
	 */
	public static Set<String> getSimpleFieldNames(Class<?> pojoClass) {
		Set<String> propertyNames = new HashSet<String>();
		Class<?> clazz = pojoClass;
		do {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (!Modifier.isStatic(field.getModifiers())
						&& (field.getType().isPrimitive()
								|| isWrapClass(field.getType())
								|| field.getType().isAssignableFrom(
										Timestamp.class)
										|| field.getType().isAssignableFrom(Date.class)
										|| field.getType().isAssignableFrom(
												String.class) || field.getType()
												.isAssignableFrom(Calendar.class))) {
					propertyNames.add(field.getName());
				}
			}
			clazz = clazz.getSuperclass();
		} while (clazz != null
				&& !clazz.getSimpleName().equalsIgnoreCase("Object"));
		return propertyNames;
	}

	/**
	 * 获取用户类型.
	 */
	public static Class<?> getUserClass(Object instance) {
		Assert.notNull(instance, "Instance must not be null");
		Class clazz = instance.getClass();
		if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
			Class<?> superClass = clazz.getSuperclass();
			if (superClass != null && !Object.class.equals(superClass)) {
				return superClass;
			}
		}
		return clazz;

	}

	/**
	 * 调用Getter方法.
	 * @throws Exception
	 */
	public static Object invokeGetter(Object obj, String propertyName) throws Exception {
		String getterMethodName = GETTER_PREFIX
				+ StringUtils.capitalize(propertyName);
		return invokeMethod(obj, getterMethodName, new Class[] {},
				new Object[] {});
	}

	/**
	 * 调用Getter方法.
	 * @throws Exception 
	 */
	public static List<Object> invokeGetter(Object obj, String[] propertyNames) throws Exception {
		List<Object> list = new ArrayList<Object>(propertyNames.length);
		for (String propertyName : propertyNames) {
			Object propertyValue = null;
			if (StringUtils.contains(propertyName, ".")) {
				String[] propertyNamePaths = StringUtils.split(propertyName,
						".");
				Object temp = obj;
				for (String propertyNamePath : propertyNamePaths) {
					if (temp == null) {
						break;
					}
					temp = Reflections.invokeGetter(temp, propertyNamePath);
				}
				propertyValue = temp;
			} else {
				propertyValue = Reflections.invokeGetter(obj, propertyName);
			}
			list.add(propertyValue);
		}
		return list;
	}

	/**
	 * 调用Getter方法.
	 * @throws Exception 
	 */
	public static List<String> invokeGetterToString(Object obj,
			String[] propertyNames) throws Exception {
		List<Object> list = invokeGetter(obj, propertyNames);
		List<String> result = new ArrayList<String>(list.size());
		for (Object object : list) {
			if (object == null) {
				result.add(null);
			} else if (object instanceof Date) {
				result.add(DateUtil.formatDate((Date) object,
						DateUtil.PRINT_FORMAT));
			} else if (object instanceof Calendar) {
				result.add(DateUtil.formatDate(((Calendar) object).getTime(),
						DateUtil.PRINT_FORMAT));
			} else {
				result.add(object.toString());
			}
		}
		return result;
	}

	/**
	 * 直接调用对象方法, 无视private/protected修饰符.
	 * 用于一次性调用的情况，否则应使用getAccessibleMethod()函数获得Method后反复调用. 同时匹配方法名+参数类型，
	 * @throws Exception
	 */
	public static Object invokeMethod(final Object obj,
			final String methodName, final Class<?>[] parameterTypes,
			final Object[] args) throws Exception {
		Method method = getAccessibleMethod(obj, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method ["
					+ methodName + "] on target [" + obj + "]");
		}

		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
//			throw convertReflectionExceptionToUnchecked(e);
			throw e;
		}
	}

	/**
	 * 直接调用对象方法, 无视private/protected修饰符，
	 * 用于一次性调用的情况，否则应使用getAccessibleMethodByName()函数获得Method后反复调用.
	 * 只匹配函数名，如果有多个同名函数调用第一个。
	 * @throws Exception 
	 */
	public static Object invokeMethodByName(final Object obj,
			final String methodName, final Object[] args) throws Exception {
		Method method = getAccessibleMethodByName(obj, methodName);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method ["
					+ methodName + "] on target [" + obj + "]");
		}

		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
//			throw convertReflectionExceptionToUnchecked(e);
			throw e;
		}
	}

	/**
	 * 调用Setter方法, 仅匹配方法名。
	 * @throws Exception 
	 */
	public static void invokeSetter(Object obj, String propertyName,
			Object value) throws Exception {
		String setterMethodName = SETTER_PREFIX
				+ StringUtils.capitalize(propertyName);
		invokeMethodByName(obj, setterMethodName, new Object[] { value });
	}
	/**
	 * 是否包含类. <br/>
	 * @return
	 */
	public static boolean isWrapClass(Class clz) {
		try {
			return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 改变private/protected的成员变量为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。
	 */
	public static void makeAccessible(Field field) {
		if ((!Modifier.isPublic(field.getModifiers())
				|| !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier
				.isFinal(field.getModifiers())) && !field.isAccessible()) {
			field.setAccessible(true);
		}
	}

	/**
	 * 改变private/protected的方法为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。
	 */
	public static void makeAccessible(Method method) {
		if ((!Modifier.isPublic(method.getModifiers()) || !Modifier
				.isPublic(method.getDeclaringClass().getModifiers()))
				&& !method.isAccessible()) {
			method.setAccessible(true);
		}
	}

	/**
	 * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
	 */
	public static void setFieldValue(final Object obj, final String fieldName,
			final Object value) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field ["
					+ fieldName + "] on target [" + obj + "]");
		}

		try {
			field.set(obj, value);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}
	}

	private static final String SETTER_PREFIX = "set";

	private static final String GETTER_PREFIX = "get";

	private static final String CGLIB_CLASS_SEPARATOR = "$$";

	private static Logger logger = LoggerFactory.getLogger(Reflections.class);
}
