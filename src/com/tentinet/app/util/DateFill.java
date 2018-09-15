package com.tentinet.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateFill {
	/**
	 * 时间字符串转换
	 * DatetoString(new Date(),"yyyy-MM-dd HH:mm:ss") 
	 * @param s
	 * @return 字符串日期
	 */
	public static String DatetoString(Date dateTime, String model) {
		SimpleDateFormat dfy = new SimpleDateFormat(model);
		StringBuffer result = new StringBuffer();
		result.append(dfy.format(dateTime));
		return result.toString();
	}

	/**
	 * 获取系统时间
	 * 
	 * @param dateStyle
	 * @return 预制要返回的时间  默认 yyyy-MM-dd HH:mm:ss
	 */
	public static String getSysDate(String dateStyle) {
		Date now = new Date();
		if (StringUtils.isBlank(dateStyle)) {
			dateStyle = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateStyle);
		return dateFormat.format(now);
	}
	public static void main(String[] args) {
	}
}
