package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * ClassName: DateUtils <br/>
 * Description: Date工具类. <br/>
 * Date: 2016年3月25日 上午10:18:03 <br/>
 *
 * @author Chunjie He
 * @version 1.0
 * @since 1.7
 */
public class DateUtils {
	
    public static final String DATE_FULL_STR  = "yyyy-MM-dd HH:mm";
    public static final String DATE_SMALL_STR = "yyyy-MM-dd";
    public static final String DATE_DAY_STR = "yyyyMMdd";
     
    /**
     * getHour 根据Date获取具体时间点
     *
     * @param inDate
     * @return int
     */
    public static int getHour(Date inDate) {
      Calendar aCal = GregorianCalendar.getInstance();
      aCal.setTime(inDate);
      return aCal.get(Calendar.HOUR_OF_DAY);
    }
    
    /**
     * 获取两个时间的分钟差
     * @param benginDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    public static Long getIntervalMinutes(Date benginDate,Date endDate) {  
    	if(benginDate == null || endDate == null){
    		return null;    	
    	}
    	long iIntervalMinutes = (endDate.getTime()-benginDate.getTime())/(1000*60);
    	return iIntervalMinutes;
    }
    
    /**
     * 使用预设格式提取字符串日期
     * @param strDate 日期字符串
     * @return
     */
	public static Date parse(String strDate) {
        return parse(strDate, DATE_FULL_STR);
    }
	
	/** 
     * 取得季度月 
     *  
     * @param date 
     * @return 
     */  
    public static Date[] getSeasonDate(Calendar c) {  
        Date[] season = new Date[3];  
  
        int nSeason = getSeason(c);  
        if (nSeason == 1) {// 第一季度  
            c.set(Calendar.MONTH, Calendar.JANUARY);  
            season[0] = c.getTime();  
            c.set(Calendar.MONTH, Calendar.FEBRUARY);  
            season[1] = c.getTime();  
            c.set(Calendar.MONTH, Calendar.MARCH);  
            season[2] = c.getTime();  
        } else if (nSeason == 2) {// 第二季度  
            c.set(Calendar.MONTH, Calendar.APRIL);  
            season[0] = c.getTime();  
            c.set(Calendar.MONTH, Calendar.MAY);  
            season[1] = c.getTime();  
            c.set(Calendar.MONTH, Calendar.JUNE);  
            season[2] = c.getTime();  
        } else if (nSeason == 3) {// 第三季度  
            c.set(Calendar.MONTH, Calendar.JULY);  
            season[0] = c.getTime();  
            c.set(Calendar.MONTH, Calendar.AUGUST);  
            season[1] = c.getTime();  
            c.set(Calendar.MONTH, Calendar.SEPTEMBER);  
            season[2] = c.getTime();  
        } else if (nSeason == 4) {// 第四季度  
            c.set(Calendar.MONTH, Calendar.OCTOBER);  
            season[0] = c.getTime();  
            c.set(Calendar.MONTH, Calendar.NOVEMBER);  
            season[1] = c.getTime();  
            c.set(Calendar.MONTH, Calendar.DECEMBER);  
            season[2] = c.getTime();  
        }
        
        return season;  
    }  
	
	/** 
     *  
     * 1 第一季度 2 第二季度 3 第三季度 4 第四季度 
     *  
     * @param date 
     * @return 
     */  
    public static int getSeason(Calendar c) { 
        int season = 0;  
        int month = c.get(Calendar.MONTH);  
        switch (month) {  
	        case Calendar.JANUARY:  
	        case Calendar.FEBRUARY:  
	        case Calendar.MARCH:  
	            season = 1;  
	            break;  
	        case Calendar.APRIL:  
	        case Calendar.MAY:  
	        case Calendar.JUNE:  
	            season = 2;  
	            break;  
	        case Calendar.JULY:  
	        case Calendar.AUGUST:  
	        case Calendar.SEPTEMBER:  
	            season = 3;  
	            break;  
	        case Calendar.OCTOBER:  
	        case Calendar.NOVEMBER:  
	        case Calendar.DECEMBER:  
	            season = 4;  
	            break;  
	        default: break;  
        }
        return season;  
    }  
    
    /** 
     * 取得月已经过的天数 
     *  
     * @param date 
     * @return 
     */  
    public static int getPassDayOfMonth(Calendar c) {  
        return c.get(Calendar.DAY_OF_MONTH);  
    } 
    
    /** 
     * 取得月天数 
     *  
     * @param date 
     * @return 
     */  
    public static int getDayOfMonth(Calendar c) {  
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);  
    }  
	
	/** 
     * 取得季度已过天数 
     *  
     * @param date 
     * @return 
     */  
    public static int getPassDayOfSeason(Calendar c) {  
        int month = c.get(Calendar.MONTH);  
  
        int day = 0;  
        if (month == Calendar.JANUARY || 
        	month == Calendar.APRIL   || 
        	month == Calendar.JULY    || 
        	month == Calendar.OCTOBER) { // 季度第一个月  
            day = getPassDayOfMonth(c);  
        } else if (month == Calendar.FEBRUARY || 
        		   month == Calendar.MAY      || 
        		   month == Calendar.AUGUST   || 
        		   month == Calendar.NOVEMBER) {// 季度第二个月  
            day = getDayOfMonth(c) + getPassDayOfMonth(c);  
        } else if (month == Calendar.MARCH || 
        		   month == Calendar.JUNE  || 
        		   month == Calendar.SEPTEMBER ||
        		   month == Calendar.DECEMBER) {// 季度第三个月  
            day = getDayOfMonth(c) + getDayOfMonth(c) + getPassDayOfMonth(c);  
        }  
        return day;  
    }  
	
	/**
	 * 获取当日日期字符串
	 * @return
	 */
	public static String getCurrentDay() {
		return DateUtils.parseStr(new Date(), DATE_DAY_STR);
	}
	
    /**
     * 使用用户格式提取字符串日期
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }
    
    /**
     * 使用用户格式提取字符串日期
     * @param date
     * @param pattern
     * @return
     */
    public static int parseInt(Date date, String pattern) {
    	SimpleDateFormat df = new SimpleDateFormat(pattern);
        return Integer.parseInt(df.format(date));
    }
    
    /**
     * 使用用户格式提取字符串日期
     * @param date
     * @param pattern
     * @return
     */
    public static String parseStr(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }
    
    /**
     * 时间戳转换为可阅读的字符串
     * @param date
     * @return
     */
    public static String parseStr(Long date) {
    	return new SimpleDateFormat(DATE_FULL_STR).format(new Date(date));
    }
     
    /**
     * 获取系统当前时间
     * @return
     */
    public static String getNowTime(String type) {
        return new SimpleDateFormat(type).format(new Date());
    }
    
    public static void main(String[] args) {
    	System.out.println(getHour(new Date()));
    }
}
