/**
 * Project Name:IBIS-commons
 * File Name:CsvFileUtil.java
 * Package Name:com.cloudwalk.common.util.file
 * Date:2015年9月8日下午3:10:06
 * Copyright @ 2015-2015 重庆中科云丛科技有限公司  All Rights Reserved.
 *
 */

package com.util.file;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.annotation.ExportField;
import com.util.DateUtil;
import com.util.ObjectUtils;
import com.util.Reflections;
import com.util.exception.ServiceException;

/**
 * ClassName:CsvFileUtil <br/>
 * Date: 2015年9月8日 下午3:10:06 <br/>
 *
 * @author zhuyf
 * @version
 * @since JDK 1.7
 * @see
 */
public class CsvFileUtil {

	/**
	 * 实现导出输出的程序模板，子类可以直接使用或根据自己的需求覆写
	 *
	 * @param list
	 * @param request
	 * @param response
	 * @throws ServiceException 
	 */
	public static <T> void doExportCsvBody(List<T> list, String fileName,
			String fieldString, Class<T> entityClass,
			HttpServletRequest request, HttpServletResponse response)
					throws ServiceException {

		doExportCsvHeader(request, response, fileName);
		PrintWriter p = null;
		try {
			p = new PrintWriter(new OutputStreamWriter(
					response.getOutputStream(), getCharsetName(request, "GBK")));
			// 获取需要导出的字段集合
			Map<String, String> entityFieldMap = getPropertyNameMap(
					entityClass, fieldString);
			if (entityFieldMap == null || entityFieldMap.values().size() < 1) {
				return;
			}

			// 输出导出数据的title
			List<String> entityFieldList = new ArrayList<String>();
			entityFieldList.addAll(entityFieldMap.values());
			p.println(CsvMapper.marshal(entityFieldList));
			p.flush();

			// 导出数据
			int flushThreshold = 1000;
			int sendCount = 0;
			for (T entity : list) {
				String[] propertyNames = new String[] {};
				propertyNames = entityFieldMap.keySet().toArray(propertyNames);
				p.println(CsvMapper.marshal(doMarshalEntityToCsv(entity,
						propertyNames)));
				sendCount++;
				if (sendCount % flushThreshold == 0) {
					p.flush();
				}
			}
			p.flush();
			p.close();
		} catch (Exception e) {
			LOG.warn("do export csv failure -> " + e.getMessage(), e);
			throw new ServiceException("CSV输出失败[" + e.getMessage() + "]");
		} finally {
			IOUtils.closeQuietly(p);
		}
	}

	/**
	 * 设置导出Excel文件的Http头，只要是设置下载文件的文件名和MITA信息
	 */
	private static void doExportCsvHeader(HttpServletRequest request,
			HttpServletResponse response, String fileName) {
		try {
			fileName = URLEncoder.encode(fileName.replace("\r","").replace("\n",""),
					getCharsetName(request, "UTF-8"));
		} catch (Exception e) {
		}
		response.setContentType("application/csv");
		response.setHeader("Content-Disposition", "attachment;filename=\""
				+ fileName + ".csv\"");
	}

	/**
	 * 编列实体对象为CSV格式
	 *
	 * @param entity
	 */
	protected static <T> List<String> doMarshalEntityToCsv(T entity,
			String[] propertyNames) {
		return invokeGetterToString(entity, propertyNames);
	}

	/**
	 *
	 * getCharsetName:获取字符编码名称. <br/>
	 *
	 * @author:朱云飞 Date: 2015年9月8日 下午3:13:33
	 * @param request
	 * @return
	 * @since JDK 1.7
	 */
	private static String getCharsetName(HttpServletRequest request,
			String charset) {
		charset = StringEscapeUtils.escapeHtml4(charset);
		if (StringUtils.isBlank(charset)) {
			charset = "UTF-8";
		}
		String requestCharset = StringUtils.trimToEmpty(StringEscapeUtils.escapeHtml4(request
				.getParameter("charset")));
		if (StringUtils.isNotBlank(requestCharset)) {
			charset = requestCharset;
		}
		return charset;
	}

	/**
	 *
	 * getPropertyNames:获取需要导出的字段名称. <br/>
	 *
	 * @author:朱云飞 Date: 2015年9月8日 下午6:13:35
	 * @param entity
	 * @return
	 * @since JDK 1.7
	 */
	private static <T> Map<String, String> getPropertyNameMap(
			Class<T> entityClass, String fieldString) {
		if (entityClass == null) {
			return null;
		}
		Map<String, String> fieldMap = new LinkedHashMap<String, String>();
		Class<?> claszz = entityClass;
		while (claszz != null && !claszz.getSimpleName().equalsIgnoreCase("Object")) {
			Field[] fields = claszz.getDeclaredFields();
			if (!ObjectUtils.isEmpty(fields)) {
				for (Field field : fields) {
					if (!Modifier.isStatic(field.getModifiers())) {

						if (!StringUtils.isBlank(fieldString)
								&& !fieldString.contains(field.getName())) {
							continue;
						}

						ExportField exportField = field
								.getAnnotation(ExportField.class);
						if (exportField != null
								&& !"".equals(exportField.name())) {
							fieldMap.put(field.getName(), exportField.name());
						}
					}
				}
			}
			claszz = claszz.getSuperclass();
		}
		return fieldMap;
	}

	/**
	 *
	 * invokeGetterToString:根据属性集合获取实体中的值. <br/>
	 *
	 * @author:朱云飞 Date: 2015年9月9日 上午10:52:21
	 * @param obj
	 * @param propertyNames
	 * @return
	 * @since JDK 1.7
	 */
	public static List<String> invokeGetterToString(Object obj,
			String[] propertyNames) {
		List<String> result = new ArrayList<String>();
		if (ObjectUtils.isEmpty(propertyNames)) {
			return result;
		}

		try {
			// 遍历属性
			for (String propertyName : propertyNames) {
				Object object = Reflections.invokeGetter(obj, propertyName);
				if (object == null) {
					result.add(null);
				} else if (object instanceof Date) {
					result.add(DateUtil.formatDate((Date) object,
							DateUtil.PRINT_FORMAT));
				} else if (object instanceof Calendar) {
					result.add(DateUtil.formatDate(
							((Calendar) object).getTime(),
							DateUtil.PRINT_FORMAT));
				} else {
					Field field = obj.getClass().getDeclaredField(propertyName);
					field.setAccessible(true);
					ExportField exportField = field
							.getAnnotation(ExportField.class);

					String val = object.toString();

					if (exportField != null) {
						String[] valTypes = exportField.valType();
						if (valTypes.length > 0) {
							for (String valType : valTypes) {
								if (!StringUtils.isBlank(valType)
										&& valType.contains(val)) {
									val = valType.split(":")[1];
								}
							}
						}
					}
					result.add(val);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	protected static final Log LOG = LogFactory.getLog(CsvFileUtil.class);

}
