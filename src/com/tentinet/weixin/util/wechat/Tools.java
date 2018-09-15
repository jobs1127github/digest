package com.tentinet.weixin.util.wechat;

import java.io.InputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.tentinet.weixin.util.StringUtil;
/**
 * 自定义工具类
 * 1、把InputStream转换成String，应用场景：把一些其他编码的文件，通过包装成流，再把流读入到内存，包装成特定编码的字符串。
 * 2、 验证微信签名，校验请求是否来自于微信，把微信传递过来的参数，字典排序，拼成一个字符串，在SHA1加密。
 * 3、获取当前日期的星期，获取当天对应的星期。
 * 4、把Float类型数据保留2位小数点,向上保留2位。
 * 5、参数为日期，返回斜杠的年月日。
 * 6、过滤特殊字符串。
 * 7、跟日期相关的工具方法。
 * 
 * @author Jobs1127
 *
 */
public final class Tools {
	/**
	 * 把InputString转成String
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static final String inputStream2String(InputStream in) throws Exception {
		// 假如服务器无法保证在五秒内处理并回复，可以直接回复空串，微信服务器不会对此作任何处理，并且不会发起重试。
		if (in == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		byte[] b = new byte[4096];
		// byte[] b = new byte[in.available()];
		// 通过输入管道，把数据读入到内存，重新包装成UTF-8的String
		for (int n; (n = in.read(b)) != -1;) {
			sb.append(new String(b, 0, n, "UTF-8"));
		}
		return sb.toString();
	}

	/**
	 * 验证微信签名，校验请求是否来自于微信
	 * 
	 * @param token TOKEN
	 * @param signature  微信加密签名
	 * @param timestamp 时间戳
	 * @param nonce 随机数
	 * @return boolean 请求来自于微信返回true, 否则返回false 加密/校验流程如下：
	 *  1.将token、timestamp、nonce三个参数进行字典序排序 
	 *  2.将排好序的三个参数字符串拼接成一个字符串进行sha1加密 
	 *  3.开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
	 */
	public static final boolean checkSignature(String token, String signature,String timestamp, String nonce) {
		// 校验参数是否有空字符
		if (StringUtil.isEmptyAll(signature, timestamp, nonce)) {
			return false;
		}
		List<String> params = new ArrayList<String>();
		params.add(token);
		params.add(timestamp);
		params.add(nonce);
		// 通过list集合升序，按照字典排序，对一个装着String对象的List集合进行排序，匿名内部类给其一个比较器
		Collections.sort(params, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2); //return o2.compareTo(o1);与参数相反，为降序
			}
		});
		//将排好序的三个参数字符串拼接成一个字符串
		String temp = params.get(0) + params.get(1) + params.get(2);
		//进行sha1加密 
		return SHA1.encode(temp).equals(signature);
	}
	/**
	 * 根据参数的日期拿到星期
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekOfDate(Date date) {
		String[] weekOfDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		/***
		 * 拿到一个实例的日历类
		 */
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		//获取对应的DAY_OF_WEEK的值0~6
		int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		return weekOfDays[w];
	}
	/**
	 * 把Float类型数据保留2位小数点,向上保留2位
	 * @param data
	 * @return
	 */
	public static String float2wei(Float data) {
		DecimalFormat df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);
		return df.format(data);
	}
	
	/**
	 * 把Double类型数据保留2位小数点，向上保留2位
	 * @param data
	 * @return
	 */
	public static String double2wei(Double data) {
		DecimalFormat df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);
		return df.format(data);
	}

	/**
	 * 参数为日期，返回斜杠的年月日
	 * @param date
	 * @return String 
	 */
	public static String getYMD(Date date) {
		String timeFormat = "yyyy/MM/dd";
		SimpleDateFormat format = new SimpleDateFormat(timeFormat);
		return format.format(date);
	}

	/**
	 *  参数为日期，返回时分
	 * @param date
	 * @return
	 */
	public static String getHHmm(Date date) {
		String timeFormat = "HH:mm";
		SimpleDateFormat format = new SimpleDateFormat(timeFormat);
		return format.format(date);
	}
	/**
	 * 参数为日期，返回4位年
	 * @param date
	 * @return
	 */
	public static String getYear(Date date) {
		String timeFormat = "yyyy";
		SimpleDateFormat format = new SimpleDateFormat(timeFormat);
		return format.format(date);
	}
	
	/**
	 * 获取本月的第一天，日期格式为yyyy/MM/dd
	 * @return
	 */
	public static String getBenYueStartTime() {
		SimpleDateFormat S = new SimpleDateFormat("yyyy/MM/dd");
		Calendar yuec = Calendar.getInstance();
		yuec.add(Calendar.MONTH, 0);
		yuec.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		String jyuesdate = S.format(yuec.getTime()); // 月开始时间
		return jyuesdate;
	}
	
	/**
	 * 拿到本月的最后一天,日期格式为yyyy/MM/dd
	 * @return
	 */
	public static String getBenYueEndTime() {
		SimpleDateFormat S = new SimpleDateFormat("yyyy/MM/dd");
		Calendar yuec = Calendar.getInstance();
		yuec.add(Calendar.MONTH, 0);
		yuec.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		Calendar yueca = Calendar.getInstance();
		String jyueedate = S.format(yueca.getTime()); // 月结束时间
		return jyueedate;
	}

	// 获得当前季度的开始日期
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
		cDay.set(Calendar.DAY_OF_MONTH,
				cDay.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cDay.getTime();
	}

	// 获得当前季度的结束日期
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
		cDay.set(Calendar.DAY_OF_MONTH,
				cDay.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cDay.getTime();
	}

	/**
	 * 拿到本年的第几周
	 * @return
	 */
	public static String getDiJiZhou() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		String wdate = df.format(new Date());
		Date date = new Date();
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
		int month = date.getMonth() + 1;
		if (month >= 1 && month <= 3) {
			return "Q1";
		} else if (month >= 4 && month <= 6) {
			return "Q2";
		} else if (month >= 7 && month <= 9) {
			return "Q3";
		} else if (month >= 10 && month <= 12) {
			return "Q4";
		}
		return null;
	}

	/***
	 * 过滤特殊字符  清除掉所有特殊字符
	 * @param str
	 * @return
	 */
	public static String StringFilter(String str)  {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	public static void main(String[] args) {
//		String   str   =   "*adCVs*34_a _09_b5*[/435^*&城池()^$$&*).{}+.|.)%%*(*.中国}34{45[]12.fd'*&999下面是中文的字符￥……{}【】。，；’“‘”？"; 
//		System.out.println(StringFilter(str));
//		System.out.println(getBenYueStartTime());
//		System.out.println(getBenYueEndTime());
//		SimpleDateFormat S = new SimpleDateFormat("yyyy/MM/dd");
//		System.out.println(S.format(getFirstDayOfQuarter(new Date())));
//		System.out.println(S.format(getLastDayOfQuarter(new Date())));
//		String timeFormat = "HH:mm";
//		SimpleDateFormat format = new SimpleDateFormat(timeFormat);
//		System.out.println(format.format(new Date()));
		double a = 12.899321;
		String b = "!@#$%^&*ahah哈啊!@#$%^&*";
		System.out.println(StringFilter(b));
		System.out.println(getDiJiZhou());
		System.out.println(getBenYueStartTime());
		List<String> params = new ArrayList<String>();
		params.add("b");
		params.add("a");
		params.add("c");
		for(String h:params) {
			System.out.println(h);
		}
		// 通过list集合升序，按照字典排序
		Collections.sort(params, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o2.compareTo(o1);
			}
		});
		System.out.println("----排序后-----");
		for(String h:params) {
			System.out.println(h);
		}
	}
}