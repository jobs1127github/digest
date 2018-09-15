package cn.jobs1127.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

/**
 * 日期工具类
 * 如果需要和数据库方面的日期打交道，可以联想下Timestamp
 * @author jobs1127
 *
 */
public class DateTools {
	/**
	 * 时间字符串转换
	 * DatetoString(new Date(),
	 * @param s model:"yyyy-MM-dd HH:mm:ss" "yyyy/MM/dd HH:mm:ss"
	 * @return 字符串日期 可以满足各种形式的日期字符串，如4位的年yyyy,或时间15:28等
	 */
	public static String dateToString(Date dateTime, String model) {
		SimpleDateFormat dfy = new SimpleDateFormat(model);
		StringBuffer result = new StringBuffer();
		result.append(dfy.format(dateTime));
		return result.toString();
	}
	/**
	 * 获取系统时间
	 * 
	 * @param model 可以为(blank) 空字符串、null
	 * @return 预制要返回的时间  默认 yyyy-MM-dd HH:mm:ss
	 */
	public static String getSysDate(String model) {
		Date now = new Date();
		if (StringUtils.isBlank(model)) {
			model = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(model);
		return dateFormat.format(now);
	}
	/**
	 * String转Date
	 * model:"yyyy-MM-dd HH:mm:ss" "yyyy/MM/dd HH:mm:ss"
	 */
	public static Date strToDate(String str,String model) {
		SimpleDateFormat format = new SimpleDateFormat(model);
		try {
			return format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 指定日期拿到星期几
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekOfDate(Date date) {
		String[] weekOfDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		return weekOfDays[w];
	}
	/**
	 * 获取本月的第一天，日期格式为model:"yyyy-MM-dd" "yyyy/MM/dd"
	 * @return 本月的第一天
	 */
	public static String getBenYueStartTime(String model) {
		SimpleDateFormat S = new SimpleDateFormat(model);
		Calendar yuec = Calendar.getInstance();
		yuec.add(Calendar.MONTH, 0);
		yuec.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		String jyuesdate = S.format(yuec.getTime()); // 月开始时间
		return jyuesdate;
	}
	/**
	 * 拿到本月的最后一天,日期格式为model:"yyyy-MM-dd" "yyyy/MM/dd"
	 * @return
	 */
	public static String getBenYueEndTime(String model) {
		SimpleDateFormat S = new SimpleDateFormat(model);
		Calendar yuec = Calendar.getInstance();
		yuec.add(Calendar.MONTH, 0);
		yuec.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		Calendar yueca = Calendar.getInstance();
		String jyueedate = S.format(yueca.getTime()); // 月结束时间
		return jyueedate;
	}
	/**
	 * 根据参数日期，获取跟该日期相关季度的开始日期
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfQuarter(Date date) {
		Calendar cDay = Calendar.getInstance();
		cDay.setTime(date);
		int curMonth = cDay.get(Calendar.MONTH);
		if (curMonth >= Calendar.JANUARY && curMonth <= Calendar.MARCH) {
			cDay.set(Calendar.MONTH, Calendar.JANUARY);
		}
		if (curMonth >= Calendar.APRIL && curMonth <= Calendar.JUNE) {
			cDay.set(Calendar.MONTH, Calendar.APRIL);
		}
		if (curMonth >= Calendar.JULY && curMonth <= Calendar.SEPTEMBER) {
			cDay.set(Calendar.MONTH, Calendar.JULY);
		}
		if (curMonth >= Calendar.OCTOBER && curMonth <= Calendar.DECEMBER) {
			cDay.set(Calendar.MONTH, Calendar.OCTOBER);
		}
		cDay.set(Calendar.DAY_OF_MONTH,cDay.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cDay.getTime();
	}
	/**
	 * 根据参数日期，获取跟该日期相关季度的结束日期
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfQuarter(Date date) {
		Calendar cDay = Calendar.getInstance();
		cDay.setTime(date);
		int curMonth = cDay.get(Calendar.MONTH);
		if (curMonth >= Calendar.JANUARY && curMonth <= Calendar.MARCH) {
			cDay.set(Calendar.MONTH, Calendar.MARCH);
		}
		if (curMonth >= Calendar.APRIL && curMonth <= Calendar.JUNE) {
			cDay.set(Calendar.MONTH, Calendar.JUNE);
		}
		if (curMonth >= Calendar.JULY && curMonth <= Calendar.SEPTEMBER) {
			cDay.set(Calendar.MONTH, Calendar.SEPTEMBER);
		}
		if (curMonth >= Calendar.OCTOBER && curMonth <= Calendar.DECEMBER) {
			cDay.set(Calendar.MONTH, Calendar.DECEMBER);
		}
		cDay.set(Calendar.DAY_OF_MONTH,cDay.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cDay.getTime();
	}
	/**
	 * 拿到指定日期的第几周
	 * @return
	 */
	public static String getDiJiZhou(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		String wdate = df.format(date);
		try { 
			date = df.parse(wdate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		SimpleDateFormat d = new SimpleDateFormat("w");
		String dijizhou = d.format(date); // 第几周
		SimpleDateFormat z = new SimpleDateFormat("E");
		String dizhou = z.format(date); // 周几
		if (dizhou.equals("星期日")) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			date = calendar.getTime();
			dijizhou = String.valueOf(Integer.valueOf(dijizhou) - 1);
		}
		return dijizhou;
	}
	/**
	 * 参数日期，获取季度字符串
	 * @param date
	 * @return
	 */
	public static String getJidu(Date date) {
		//根据需要的字符串进行修改
		String q1 = "Q1";
		String q2 = "Q2";
		String q3 = "Q3";
		String q4 = "Q4";
		SimpleDateFormat df = new SimpleDateFormat("MM");
		int month = Integer.parseInt(df.format(date));
		if (month >= 1 && month <= 3) {
			return q1;
		} else if (month >= 4 && month <= 6) {
			return q2;
		} else if (month >= 7 && month <= 9) {
			return q3;
		} else if (month >= 10 && month <= 12) {
			return q4;
		}
		return null;
	}
	/**
	 * 当前的日期+多少天或减去多少天 得到日期字符串
	 * @param day
	 * @param return_model 日期格式为model:"yyyy-MM-dd" "yyyy/MM/dd"
	 * @return
	 */
	public static String getJia_or_jian_Date(int day,String return_model) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, +day);
		return dateToString(calendar.getTime(),return_model);
	}
	/**
	 * 指定的日期+多少天 得到日期字符串
	 * @param day
	 * @param return_model 日期格式为model:"yyyy-MM-dd" "yyyy/MM/dd"
	 * @return
	 */
	public static String getJia_Date(int day,Date date,String return_model) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, +day);
		return dateToString(calendar.getTime(),return_model);
	}
	/**
	 * 指定的日期+多少天或减去多少天 得到日期字符串
	 * @param day
	 * @param return_model 日期格式为model:"yyyy-MM-dd" "yyyy/MM/dd"
	 * @return
	 */
	public static String getJian_Date(int day,Date date,String return_model) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -day);
		return dateToString(calendar.getTime(),return_model);
	}
	/**
	 * 获取指定日期的年份
	 * @param date
	 * @return int year
	 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}	
	/**
	 * 获取指定日期的月份
	 * @param date
	 * @return int mouth
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH)+1;
	}
	/**
	 * 根据当前时间，+-运算，得到上个（年）月或下一个（年）月
	 * @param i
	 * @return
	 */
	public static int getYunShuanMonth(int i) {
		Calendar calendar = Calendar.getInstance(); 
		calendar.add(Calendar.DATE, i);    //得到前一天 
		calendar.add(Calendar.MONTH, i);    //得到前一个月 
		int year = calendar.get(Calendar.YEAR); 
		int month = calendar.get(Calendar.MONTH)+1; 
		return month;
	}
	/**
	 * 获取指定日期的天
	 * @param date
	 * @return int day
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}	
	/**
	 * 获取其他国家的时间
	 * ID 各个时区的可用的ID
	 * @return
	 */
	public static int getJapanHours(String ID) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone(ID));
		return calendar.get(calendar.HOUR_OF_DAY);
	}
}
