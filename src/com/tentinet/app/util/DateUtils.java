package com.tentinet.app.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

/**
 * 日期工具类 如果需要和数据库方面的日期打交道，可以联想下Timestamp
 * 
 * @author jobs1127
 *
 */
@SuppressWarnings("all")
public class DateUtils {
	// 获取Calendar实例
	static Calendar cale = Calendar.getInstance();
	static int year = cale.get(Calendar.YEAR);
	static int month = cale.get(Calendar.MONTH) + 1;
	static int day = cale.get(Calendar.DATE);
	static int hour = cale.get(Calendar.HOUR_OF_DAY);
	static int minute = cale.get(Calendar.MINUTE);
	static int second = cale.get(Calendar.SECOND);
	static int dow = cale.get(Calendar.DAY_OF_WEEK);
	static int dom = cale.get(Calendar.DAY_OF_MONTH);
	static int doy = cale.get(Calendar.DAY_OF_YEAR);

	/**
	 * 时间字符串转换 DatetoString(new Date(),
	 * 
	 * @param s
	 *            model:"yyyy-MM-dd HH:mm:ss" "yyyy/MM/dd HH:mm:ss"
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
	 * @param model
	 *            可以为(blank) 空字符串、null
	 * @return 预制要返回的时间 默认 yyyy/MM/dd HH:mm:ss
	 */
	public static String getSysDate(String model) {
		Date now = new Date();
		if (StringUtils.isBlank(model)) {
			model = "yyyy/MM/dd HH:mm:ss";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(model);
		return dateFormat.format(now);
	}

	/**
	 * String转Date model:"yyyy-MM-dd HH:mm:ss" "yyyy/MM/dd HH:mm:ss"
	 * 
	 * @throws ParseException
	 */
	public static Date strToDate(String str, String model) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(model);
		return format.parse(str);
	}

	/**
	 * 指定日期，拿到星期几
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
	 * 
	 * @return 本月的第一天
	 */
	public static String getBenYueFirstDay(String model) {
		// 获取当月第一天
		SimpleDateFormat format = new SimpleDateFormat(model);
		String firstday, lastday;
		// 获取前月的第一天
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		firstday = format.format(cale.getTime());
		return firstday;
	}

	/**
	 * 拿到本月的最后一天,日期格式为model:"yyyy-MM-dd" "yyyy/MM/dd"
	 * 
	 * @return
	 */
	public static String getBenYueLastDay(String model) {
		// 获取当月第一天和最后一天
		SimpleDateFormat format = new SimpleDateFormat(model);
		String lastday;
		// 获取前月的最后一天
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		lastday = format.format(cale.getTime());
		return lastday;
	}

	/**
	 * 根据参数日期，获取日期类型的当前季度的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfQuarter(Date date, String model) {
		SimpleDateFormat format = new SimpleDateFormat(model);
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
		cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cDay.getTime();
	}

	/***
	 * 获取字符串类型的本季度的第一天
	 * 
	 * @param date
	 * @param model
	 * @return
	 */
	public static String getFirstDayStrOfQuarter(Date date, String model) {
		SimpleDateFormat format = new SimpleDateFormat(model);
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
		cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMinimum(Calendar.DAY_OF_MONTH));
		return format.format(cDay.getTime());
	}

	/**
	 * 根据参数日期，获取日期类型的当前季度的最后一天
	 * 
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
		cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cDay.getTime();
	}

	/***
	 * 获取字符串类型的本季度的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getLastDayStrOfQuarter(Date date, String model) {
		SimpleDateFormat format = new SimpleDateFormat(model);
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
		cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMaximum(Calendar.DAY_OF_MONTH));
		return format.format(cDay.getTime());
	}

	/**
	 * 拿到指定日期的第几周
	 * 
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
	 * 参数日期，获取季度字符串，如：Q1，Q2，Q3，Q4
	 * 
	 * @param date
	 * @return
	 */
	public static String getQn(Date date) {
		// 根据需要的字符串进行修改
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
	 * 
	 * @param day
	 * @param return_model
	 *            日期格式为model:"yyyy-MM-dd" "yyyy/MM/dd"
	 * @return
	 */
	public static String getJia_or_jian_Date(int day, String return_model) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, +day);
		return dateToString(calendar.getTime(), return_model);
	}

	/**
	 * 指定的日期+多少天 得到日期字符串
	 * 
	 * @param day
	 * @param return_model
	 *            日期格式为model:"yyyy-MM-dd" "yyyy/MM/dd"
	 * @return
	 */
	public static String getJia_or_jian_Date(int day, Date date, String return_model) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, +day);
		return dateToString(calendar.getTime(), return_model);
	}

	/**
	 * 获取指定日期的年份
	 * 
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
	 * 
	 * @param date
	 * @return int mouth
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取指定日期的字符串月份
	 * 
	 * @param date
	 * @return
	 */
	public static String getMonthStr(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int m = calendar.get(Calendar.MONTH) + 1;
		return getStringMonth(m);
	}

	/**
	 * 根据当前时间，获取上N个月
	 * 
	 * @param n
	 * @return
	 */
	public static int getPreNMonth(int n) {
		n = Math.abs(n);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -n); // 得到前一天
		calendar.add(Calendar.MONTH, -n); // 得到前一个月
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * 根据当前时间，获取下N个月
	 * 
	 * @param n
	 * @return
	 */
	public static int getNextNMonth(int n) {
		n = Math.abs(n);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, n); // 得到前一天
		calendar.add(Calendar.MONTH, n); // 得到前一个月
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * 根据当前时间，获取上N个月 字符串
	 * 
	 * @param n
	 * @return
	 */
	public static String getPreNMonthStr(int n) {
		n = Math.abs(n);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -n); // 得到前一天
		calendar.add(Calendar.MONTH, -n); // 得到前一个月
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		return getStringMonth(month);
	}

	/**
	 * 根据当前时间，获取下N个月 字符串
	 * 
	 * @param n
	 * @return
	 */
	public static String getNextNMonthStr(int n) {
		n = Math.abs(n);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, n); // 得到前一天
		calendar.add(Calendar.MONTH, n); // 得到前一个月
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		return getStringMonth(month);
	}

	/**
	 * 获取指定日期的天
	 * 
	 * @param date
	 * @return int day
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 获取其他国家的时间 ID 各个时区的可用的ID
	 * 
	 * @return
	 */
	public static int getJapanHours(String ID) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone(ID));
		return calendar.get(calendar.HOUR_OF_DAY);
	}

	/***
	 * 获取当前年 yyyy
	 * 
	 * @return
	 */
	public static int getCurrentYear() {
		return year;
	}

	/***
	 * 获取当前月
	 * 
	 * @return
	 */
	public static int getCurrentMonth() {
		return month;
	}

	/***
	 * 获取字符串 月份
	 * 
	 * @return
	 */
	public static String getStringMonth(int month) {
		if (month <= 9) {
			return "0" + month;
		}
		return month + "";
	}

	/***
	 * 获取当前天
	 * 
	 * @return
	 */
	public static int getCurrentDay() {
		return day;
	}

	/***
	 * 获取字符串天
	 * 
	 * @return
	 */
	public static String getStrDay(int day) {
		if (day <= 9) {
			return "0" + day;
		}
		return day + "";
	}

	/***
	 * 解析日期并判断日期是否符合正确的格式，格式不正确返回null
	 * 
	 * @param strDate
	 * @return
	 */
	public static String parseDate(String strDate) {
		strDate = StringUtil.trimAll(strDate);
		// 最终需要的格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sdf_year = new SimpleDateFormat("yyyy");

		// 用户录入的格式1
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		// 用户录入的格式2
		DateFormat formatter2 = new SimpleDateFormat("yyyy/MM/dd");
		try {
			// 尝试用第一种格式转换
			Date date = formatter.parse(strDate);
			String time = sdf.format(date);
			if (time.startsWith("0")) {
				return null;
			}
			if(time.length() != "yyyy/MM/dd".length()) {
				return null;
			} else {
				int y = Integer.parseInt(time.substring(0,"yyyy".length()));
				int cy = Integer.parseInt(sdf_year.format(new Date()));
				if(y<2014 || y>cy) {
					return null;
				}
			}
			return time;
		} catch (Exception e) {
			// 尝试用第二种格式转换
			try {
				Date date = formatter2.parse(strDate);
				String time = sdf.format(date);
				if (time.startsWith("0")) {
					return null;
				}
				if(time.length() != "yyyy/MM/dd".length()) {
					return null;
				} else {
					//主要是为了防止用户录入日期不是当年的或是下一年的。
					int y = Integer.parseInt(time.substring(0,"yyyy".length()));
					int cy = Integer.parseInt(sdf_year.format(new Date()));
					cy=cy+1;
					if(y<2014 || y>cy) {
						return null;
					}
				}
				return time;
			} catch (Exception e1) {
				return null;
			}
		}
	}

	/***
	 * 两个时间比较，是否大于等于 t1>=t2
	 * 
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static boolean isDateGt2Eq(String t1, String t2) {
		if (t1 == null || t2 == null) {
			return false;
		}
		SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Date d1 = SDF.parse(t1);
			Date d2 = SDF.parse(t2);
			if (d1.getTime() >= d2.getTime()) {
				return true;
			}
		} catch (ParseException e) {
			return false;
		}
		return false;
	}

	/***
	 * 两个时间比较，t1是否小于t2
	 * 
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static boolean isDateLt(String t1, String t2) {
		if (t1 == null || t2 == null) {
			return false;
		}
		SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Date d1 = SDF.parse(t1);
			Date d2 = SDF.parse(t2);
			if (d1.getTime() < d2.getTime()) {
				return true;
			}
		} catch (ParseException e) {
			return false;
		}
		return false;
	}
	public static String getYear(String date) {
		if(date == null) {
			return null;
		}
		if(date.contains("/") || date.contains("-")) {
			return date.substring(0,"yyyy".length());
		}
		return null;
	}

	public static void main(String[] args) {// TODO
		String sdate = "2019/01/01";
		System.out.println(parseDate(sdate));
	}
}
