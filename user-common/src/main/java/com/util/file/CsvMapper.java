package com.util.file;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.util.DateUtil;
import com.util.Reflections;

/**
 * CSV文件简单Mapper
 *
 *
 */
public class CsvMapper {

	/**
	 * 典型测试main方法
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String line = "a,b,c,,\"asdfasdf,asdfas\",\"asd\"\"12\"\"123\"";
		System.out.println(line);
		List<String> result = CsvMapper.unmarshal(line);
//		System.out.println(result.size() + ": " + result);
//		System.out.println(CsvMapper.marshal(result));
	}

	/**
	 * 编列集合为CSV行数据
	 *
	 * @param data
	 * @return
	 */
	public static String marshal(List<String> data) {
		StringBuilder sb = new StringBuilder();
		String field = null;
		for (int i = 0; i < data.size(); i++) {
			field = StringUtils.trimToEmpty(data.get(i));
			if (StringUtils.contains(field, "\"")) {
				field = StringUtils.replace(field, "\"", "\"\"");
				field = "\"" + field + "\"";
			} else if (StringUtils.contains(field, ",")) {
				field = "\"" + field + "\"";
			}
			sb.append(field).append(i == data.size() - 1 ? "" : ",");
		}
		return sb.toString();
	}

	/**
	 * 增强CSV功能，支持对MAP数据的marshal
	 *
	 * @param row
	 * @return
	 */

	@SuppressWarnings("rawtypes")
	public static String marshal(Map row) {
		List<String> result = new ArrayList<String>();
		for (Object object : row.values()) {
			if (object instanceof Date) {
				result.add(object != null ? DateUtil.formatDate((Date) object)
						: null);
			} else {
				result.add(object != null ? object.toString() : null);
			}
		}
		return marshal(result);
	}
	/**
	 * marshal
	 * @param row
	 * @return
	 */
	public static String marshal(Object data, String[] propertyNames) throws Exception {
		List<String> list = Reflections.invokeGetterToString(data,
				propertyNames);
		return marshal(list);
	}
	/**
	 * marshals
	 * @param row
	 * @return
	 */
	public static List<String> marshals(List<List<String>> datas) {
		List<String> lines = new ArrayList<String>(datas.size());
		for (List<String> data : datas) {
			lines.add(marshal(data));
		}
		return lines;
	}
	/**
	 * marshals
	 * @param row
	 * @return
	 */
	public static List<String> marshals(List<Object> datas,
			String[] propertyNames) throws Exception {
		List<String> lines = new ArrayList<String>(datas.size());
		for (Object data : datas) {
			lines.add(marshal(data, propertyNames));
		}
		return lines;

	}

	/**
	 * 反编列CSV行为集合
	 *
	 * @param line
	 * @return
	 */
	public static List<String> unmarshal(String line) {
		List<String> result = new LinkedList<String>();
		String regex = "(\"[^\"]*(\"{2})*[^\"]*\")?[^,]*[,]?";
		Matcher matcher = Pattern.compile(regex).matcher(line);
		String str = null;
		while (matcher.find()) {
			str = matcher.group();
			if (StringUtils.isBlank(str)) {
				continue;
			}
			str = StringUtils.removeEnd(str, ",");
			if (StringUtils.startsWith(str, "\"")) {
				str = StringUtils.removeStart(str, "\"");
			}
			if (StringUtils.endsWith(str, "\"")) {
				str = StringUtils.removeEnd(str, "\"");
			}
			str = StringUtils.replace(str, "\"\"", "\"");
			result.add(str);
		}
		matcher = null;
		return result;
	}
	/**
	 * unmarshals
	 * @param line
	 * @return
	 */
	public static List<List<String>> unmarshals(List<String> lines) {
		List<List<String>> results = new ArrayList<List<String>>(lines.size());
		for (String line : lines) {
			results.add(unmarshal(line));
		}
		return results;
	}

}
