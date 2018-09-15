package com.tentinet.weixin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	/** 日期格式：HHmmssSSS */
	public static final String FMT_S_TIMES = "HHmmssSSS";
	private static SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 格式化日期
	 * 
	 * @param date
	 *            日期
	 * @param dateFormat
	 *            日期格式化模式
	 * @return 日期字符串
	 */
	public static String format(Date date, String dateFormat) {
		return new SimpleDateFormat(dateFormat).format(date);
	}

	/**
	 * String转时间
	 */
	public static Date strToDate(String str) {
		try {
			return format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 时间转String
	 */
	public static String dateToStr(Date date) {
		String result = "";
		result = format.format(date);
		return result;
	}

	/**
	 * 时间字符串转换
	 * 
	 * @param s
	 * @return
	 */
	public static String DatetoString(Date dateTime, String model) {
		SimpleDateFormat dfy = new SimpleDateFormat(model);
		StringBuffer result = new StringBuffer();
		result.append(dfy.format(dateTime));
		return result.toString();

	}
}
