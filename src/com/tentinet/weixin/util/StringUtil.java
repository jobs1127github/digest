package com.tentinet.weixin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/***
 * 自定义String类的工具类，
 * 1、判断一个字符串或多个字符串是否为空或为null。
 * 2、判断某个字符串是否为ip字符串。
 * 3、过滤字符串的所有特殊字符，替换成""空字符串。
 * 4、把空格隔开的字符串，转换成","隔开的字符串，用于SQL。
 * @author Jobs1127
 *
 */
public class StringUtil {
	/***
	 * 
	 * @param ss String...String类的可变参数
	 * 所有的参数，只要有一个为空或为null，返回false。
	 */
	public static boolean isEmptyAll(String... ss) {
		for (String s : ss) {
			if (isNotEmpty(s)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotEmpty(String s) {
		return s != null && s.trim().length() > 0;
	}

	/***
	 * 判断某个字符串是否是 ip
	 * @param s
	 * @return
	 */
	public static boolean isIP(String s) {
		return s.matches("^(0?0?[1-9]|0?[1-9]\\d|1\\d\\d|2[01]\\d|22[0-3])(\\.([01]?\\d?\\d|2[0-4]\\d|25[0-5])){3}$");
	}
	
	/**
	 * 过滤特殊字符  清除掉所有特殊字符
	 * @param str
	 * @return
	 */
	public static String StringFilter(String str)  {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符，regEx正则表达式
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		//通过Pattern编译这个正则表达式
		Pattern p = Pattern.compile(regEx);
		//通过Pattern去匹配目标字符串，符合正则表达式的字符都存放到Matcher里
		Matcher m = p.matcher(str);
		//把所有匹配成功的，并存放到Matcher里的所有字符，都替换成""空字符串。
		return m.replaceAll("").trim();
	}
	
	/***
	 * 将空格隔开的id字符串，转换成逗号隔开的id字符串，用于sql语句
	 * @param strId
	 * @return
	 */
	public static String spaceIdSplitDouHaoId(String strId) {
		StringBuilder str = new StringBuilder();
		if(strId != null && strId.length() > 0) {
			String[] ids = strId.trim().split(" ");
			for(String s:ids) {
				str.append(s+",");
			}
			str.deleteCharAt(str.length()-1);
		}
		return str.toString();
	}
}
