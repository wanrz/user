package com.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * ClassName:DateUtil <br/>
 * Description: 时间工具类 <br/>
 * Date: 2014年11月21日 下午3:22:48 <br/>
 *
 * @author 焦少平
 * @version 1.0
 * @since JDK 1.7
 * @see
 */
public class DateUtil {
	
	/**
	 * 字符串转换成时间戳. <br/>
	 */
	public static Timestamp valueOf(String date, Locale locale, String pattern) {
	    try {
	    	if ((date == null) || (locale == null) || (pattern == null)) {
	    		return null;
	    	}
	    	
	    	SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
	    	return new Timestamp(sdf.parse(date).getTime());
	    } catch (Exception ex) {
	    	ex.printStackTrace();
	    }
	    
	    return null;
	}
	
	/**
	 * 字符串转换成时间戳. <br/>
	 */
	public static Timestamp valueOf(String date) {
		return valueOf(date, Locale.getDefault(), "yyyy-MM-dd");
	}

	/**
	 * betweenDate:比较日期是否介于两者之间 <br/>
	 * 适用条件：比较日期是否介于两者之间<br/>
	 * 执行流程：工具类调用<br/>
	 *
	 * @author:焦少平 Date: 2015年4月15日 下午3:39:34
	 * @param betweenDate 比较日期
	 * @param toStartDate  开始日期
	 * @param toEndDate  结束日期
	 * @return boolean 布尔值
	 * @since JDK 1.7
	 */
	public static boolean betweenDate(Date betweenDate, Date toStartDate, Date toEndDate) {
		if (toStartDate.getTime() <= betweenDate.getTime() && betweenDate.getTime() <= toEndDate.getTime()) {
			return true;
		}
		
		return false;
	}

	/**
	 * compareDate:比较两个日期的大小 <br/>
	 * 适用条件：比较两个日期的大小<br/>
	 * 执行流程：工具类调用<br/>
	 *
	 * @author:焦少平 Date: 2015年4月15日 下午3:39:40
	 * @param firstDate
	 *            第一个日期值
	 * @param lastDate
	 *            第二个日期值
	 * @return boolean 布尔值
	 * @since JDK 1.7
	 */
	public static boolean compareDate(Date firstDate, Date lastDate) {
		if (firstDate.getTime() == lastDate.getTime()) {
			return true;
		}
		return false;
	}

	/**
	 * formatDate:格式化日期，返回yyyyMMdd<br/>
	 * 适用条件：格式化日期，返回yyyyMMdd<br/>
	 * 执行流程：内部调用<br/>
	 * 注意事项：字符串必须是日期格式<br/>
	 *
	 * @author:焦少平 Date: 2014年12月31日 上午10:24:14
	 * @param date
	 *            日期
	 * @return String 日期字符串
	 * @since JDK 1.7
	 */
	public static String formatDate(Date date) {
		return DATE_FORMAT_OBJ.format(date);
	}

	/**
	 *
	 * formatDate:根据指定类型格式化日期. <br/>
	 *
	 * @author:朱云飞 Date: 2015年9月9日 上午10:47:26
	 * @param date
	 * @param patten
	 * @return
	 * @since JDK 1.7
	 */
	public static String formatDate(Date date, String patten) {
		return new SimpleDateFormat(patten).format(date);
	}

	/**
	 * formatTime:格式化时间，返回HHmmss <br/>
	 * 适用条件：格式化时间，返回HHmmss<br/>
	 * 执行流程：内部调用<br/>
	 * 注意事项：字符串必须是日期格式<br/>
	 *
	 * @author:焦少平 Date: 2014年12月31日 上午10:23:39
	 * @param date
	 *            日期
	 * @return String 时间字符串
	 * @since JDK 1.7
	 */
	private static String formatTime(Date date) {
		return TIME_FORMAT_OBJ.format(date);
	}

	/**
	 * getBillMonth: 时间格式改为年月(yyyyMM)模式. <br/>
	 * 适用条件：得到的日期格式为yyyyMM<br/>
	 * 传入参数date为Date类型.<br/>
	 * 执行流程：外部调用.<br/>
	 *
	 * @author:杨鹏 Date: 2015年4月17日 下午7:03:05
	 * @param date
	 *            日期
	 * @return int 时间数值
	 * @since JDK 1.7
	 */
	public static int getBillMonth(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		int billMonth = 0;
		if (date != null) {
			// 传入时间(如 2015年12月 --> 201512)
			billMonth = Integer.parseInt(format.format(date));

		} else {
			// 当前年月(如 2015年12月 --> 201512)
			billMonth = Integer.parseInt(format.format(new Date()));
		}
		return billMonth;
	}

	/**
	 * getBillSerials:暂时用作生成单据流水号. <br/>
	 * 适用条件：得到的日期格式为yyyyMMddHHmmss<br/>
	 * 执行流程：外部调用.<br/>
	 *
	 * @author:杨鹏 Date: 2015年4月17日 下午6:54:08
	 * @return String 时间字符串
	 * @since JDK 1.7
	 */
	public static String getBillSerials() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	/**
	 * getBudgetDialUpSerials:生成上拨流水号 <br/>
	 * 适用条件:<br/>
	 * 执行流程:<br/>
	 * 使用方法:<br/>
	 * 注意事项:<br/>
	 *
	 * @author:李佳俊 Date: 2015年5月4日 下午7:51:10
	 * @param str
	 *            参数待定
	 * @return
	 */
	public static String getBudgetDialUpSerials(String str) {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	/**
	 * getControlDate:(这里用一句话描述这个方法的作用)获得年份数据. <br/>
	 * 执行流程：外部调用.<br/>
	 *
	 * @author:杨鹏 Date: 2015年4月13日 上午11:03:08
	 * @param before
	 *            获得当前年份之前多少年的年份
	 * @param alter
	 *            获得当前年份之后多少的年份
	 * @return 所需的年份的list
	 * @since JDK 1.7
	 */
	public static List<String> getControlDate(int before, int alter) {
		// 获取日历对象
		Calendar calendar = Calendar.getInstance();
		// 获得当前年份
		int year = calendar.get(Calendar.YEAR);
		// 创建年份的list对象
		List<String> years = new ArrayList<String>();
		// 判断是否需要获得当前日期之前的年份
		if (before > 0) {
			int howBefore = before; // 记录需要当前年份多少之前多少年的年份
			int i = 0;
			for (i = 0; i < howBefore; i++) {
				years.add(String.valueOf(year - before)); // 当前年份之前的年份
				before--;
			}
		}
		// 当前年份
		years.add(String.valueOf(year));
		// 判断是否需要获得当前日期之前后的年份
		if (alter > 0) {
			int j = 0;
			for (j = 0; j < alter - 1; j++) {
				years.add(String.valueOf(year + 1)); // 当前月份之后的年份
				year++;
			}
		}
		return years;
	}

	/**
	 * getCurrentDate:得到系统当日日期，其中日期的格式为：yyyyMMdd，例如：20051101 <br/>
	 * 适用条件：格式化时间，返回HHmmss<br/>
	 * 执行流程：外部调用<br/>
	 *
	 * @author:焦少平 Date: 2014年11月21日 下午6:27:20
	 * @return String 时间字符串
	 * @since JDK 1.7
	 */
	public static String getCurrentDate() {
		return formatDate(new Date());
	}

	/**
	 * getCurrentFormatDate:得到系统当前日期，类型为Date型 <br/>
	 * 适用条件：格式化时间，返回HHmmss<br/>
	 * 执行流程：外部调用<br/>
	 *
	 * @author:焦少平 Date: 2014年11月21日 下午7:01:30
	 * @return Date 系统当前日期
	 * @since JDK 1.7
	 */
	public static Date getCurrentFormatDate() {
		return new Date();
	}

	/**
	 * getCurrentTime:得到系统当日日期，其中日期的格式为：HHmmss，例如：112003 <br/>
	 * 适用条件：格式化时间，返回HHmmss<br/>
	 * 执行流程：外部调用<br/>
	 *
	 * @author:焦少平 Date: 2014年11月21日 下午6:31:12
	 * @return String 时间字符串
	 * @since JDK 1.7
	 */
	public static String getCurrentTime() {
		return formatTime(new Date());
	}

	/**
	 * getMonth:获取当前月份. <br/>
	 * 执行流程：外部调用.<br/>
	 *
	 * @author:杨鹏 Date: 2015年4月17日 下午6:51:34
	 * @return int 月份
	 * @since JDK 1.7
	 */
	public static int getMonth() {
		// 获取日历对象
		Calendar calendar = Calendar.getInstance();
		// 获取当前月份
		int month = calendar.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * getNormalDate:得到常用的系统日期 <br/>
	 * 适用条件：得到的日期格式为yyyy-MM-dd HH:mm:ss<br/>
	 * 执行流程：外部调用<br/>
	 *
	 * @author:焦少平 Date: 2014年12月31日 上午10:29:51
	 * @return String 时间字符串
	 * @since JDK 1.7
	 */
	public static String getNormalDate() {
		return PRINT_FORMAT_OBJ.format(new Date());
	}

	/**
	 * getQuarte:获取当前季度. <br/>
	 * 执行流程：外部调用.<br/>
	 *
	 * @author:杨鹏 Date: 2015年4月17日 下午6:49:52
	 * @return int 季度
	 * @since JDK 1.7
	 */
	public static int getQuarte() {
		// 获取日历对象
		Calendar calendar = Calendar.getInstance();
		int quarte = calendar.get(Calendar.MONTH) / 3;
		return (quarte + 1);
	}

	/**
	 * getStringDate:将传入时间已指定的格式输出. <br/>
	 * 传入参数date为Date类型.<br/>
	 * 执行流程：外部调用.<br/>
	 * 参数formatString一定要为.<br/>
	 *
	 * @author:杨鹏 Date: 2015年4月20日 上午10:17:49
	 * @param date
	 *            时间
	 * @param formatString
	 *            所需的格式 如:yyyyMMdd、yyyy-MM-dd等
	 * @return String 时间字符
	 * @since JDK 1.7
	 */
	public static String getStringDate(Date date, String formatString) {
		return new SimpleDateFormat(formatString).format(date);
	}

	/**
	 * 获取当前年份
	 */
	public static int getYear() {
		// 获取日历对象
		Calendar calendar = Calendar.getInstance();
		// 获取当前月份
		int year = calendar.get(Calendar.YEAR);
		return year;
	}

	/**
	 * praseDate:格式化日期，将String格式化成Date型 <br/>
	 * 适用条件：转换日期类型<br/>
	 * 执行流程：外部调用<br/>
	 * 使用方法：传入String日期<br/>
	 *
	 * @author:焦少平 Date: 2015年4月15日 下午2:01:25
	 * @param date
	 *            日期
	 * @return Date 日期型格式
	 * @throws ParseException
	 *             转换异常
	 * @since JDK 1.7
	 */
	public static Date praseDate(String date) throws ParseException {
		return DATE_FORMAT_OBJ.parse(date);
	}
	
	/**
	 * 将日期对象转换为8个字符的整形
	 * @param date
	 * @return
	 */
	public static Integer parseInt(Date date) {
		return Integer.valueOf(DATE_FORMAT_OBJ.format(date));
	}

	/**
	 * sysTime:系统当前时间，返回yyyy年MM月dd日格式 <br/>
	 * 适用条件：主页面的时间显示<br/>
	 * 执行流程：外部方法调用<br/>
	 * 使用方法：外部方法调用<br/>
	 *
	 * @author:焦少平 Date: 2015年4月21日 上午11:01:27
	 * @return String 返回格式化之后的时间
	 */
	public static String sysTime() {
		return LOGIN_FORMAT_OBJ.format(new Date());
	}

	public static final String DATE_FORMAT_1 = "yyyyMMdd";

	public static final String TIME_FORMAT = "HHmmss";

	public static final String PRINT_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String LOGIN_FORMAT = "yyyy年MM月dd日";

	private static final SimpleDateFormat PRINT_FORMAT_OBJ = new SimpleDateFormat(PRINT_FORMAT);

	private static final SimpleDateFormat DATE_FORMAT_OBJ = new SimpleDateFormat(DATE_FORMAT_1);

	private static final SimpleDateFormat TIME_FORMAT_OBJ = new SimpleDateFormat(TIME_FORMAT);

	private static final SimpleDateFormat LOGIN_FORMAT_OBJ = new SimpleDateFormat(LOGIN_FORMAT);
}
