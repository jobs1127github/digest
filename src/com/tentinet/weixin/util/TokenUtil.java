package com.tentinet.weixin.util;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.CharEncoding;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.tentinet.weixin.entity.WXUrl;

/**
 * access_token是公众号的全局唯一接口调用凭据，
 * 公众号调用各接口时都需使用access_token。开发者需要进行妥善保存。
 * access_token的存储至少要保留512个字符空间。
 * access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。
 * 每一个用户进入你的公众平台，公众平台要获取相关的数据，
 * 都是通过公众号用access_token去请求微信服务器的接口完成的。
 * 微信token是取得微信请求的基础,但一天只能取2000次,所以要处理，充分利用已经生成的access_token。
 * 
 * @author Jobs1127
 */
public class TokenUtil {
	/**
	 * 只要这个类被加载，Java虚拟机就能根据类名在运行时数据区的方法区内定找到他们。
	 * 因此，static对象可以在它的任何对象创建之前访问，无需引用任何对象。 
	 * 用public修饰的static成员变量和成员方法本质是全局变量和全局方法，当声明它类的对象时，
	 * 不生成static变量的副本，而是类的所有实例共享同一个static变量。 
	 */
	public static Map<String, String> tokanMap = new HashMap<String, String>();
	//单例
	private static TokenUtil instance=new TokenUtil();
	private static final String timeFormat = "yyyy-MM-dd hh:mm:ss";

	private TokenUtil(){
		
	}
	
	public static TokenUtil getInstance() {
		return instance;
	}
	static {
		//静态代码块，只在类加载时被初始化一次。
		initTokanMap();
	}
	/**
	 * 初始化Map的内容，存放token字符串和该token创建的时间
	 */
	private static void initTokanMap() {
		try {
			String tokenStr = getTokenFromWXService();
			tokanMap.put("tokenStr", tokenStr);
			SimpleDateFormat format = new SimpleDateFormat(timeFormat);
			tokanMap.put("tokenCreatedTime", format.format(new Date()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从微信服务平台获取token
	 * 通过请求微信接口（token接口对应的URL）获取token
	 * @return
	 */
	private static String getTokenFromWXService() {
		String tokenStr = null;
		/**
		 * 从属性配置文件中获取
		 */
		String appid = ConfigUtils.getValue("weixin.appId");
		String secret = ConfigUtils.getValue("weixin.secret");
		/**
		 * 通过MessageFormat 可以自动把占位符补全
		 */
		String tokenUrl = MessageFormat.format(WXUrl.ACCESSTOKEN_URL, appid,secret);
		//System.out.println("tokenUrl:" + tokenUrl);
		/**
		 * 请求URL,获取Json数据（请求后的返回结果）
		 */
		//get方式获得请求后的json数据
		//String result = HttpClientUtil.get_return_json_string(tokenUrl);
		//post方式获得请求后的json数据1
		//String result = HttpClientUtil.sendPostSSLRequest(tokenUrl, null,CharEncoding.UTF_8);
		//post方式获得请求后的json数据2
		//String result = HttpClientUtil.post_return_json_string(tokenUrl,null);
		
		String result=null;
		try {
			//result = HttpClientUtil2.doGet_ruturn_String(tokenUrl, null);
			//result = HttpClientUtil2.doGet2_ruturn_String(tokenUrl, null);
			//result = HttpClientUtil2.doPost_return_string(tokenUrl, null,null,true);
			result = HttpRequestUtil.httpRequest(tokenUrl, "POST",null).toString();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//System.out.println("result="+result);
		/**
		 * 通过ObjectMapper 来readTree读取json，
		 * 可以拿到Json的结点，通过结点来访问该结点(key)对应的值（value）。
		 */
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode jsonNode = mapper.readTree(result);
			tokenStr = jsonNode.path("access_token").getTextValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tokenStr;
	}

	/**
	 * 从Map中得到对应的token
	 * map相当于一个缓存容器
	 * @return
	 */
	public static String getToken() {
		String resultStr = null;
		// 比较当前时间,token的创建时间是否在2小时之内
		boolean result = isvalid();
		if (result) {// 说明当前的toke值还可以用
			resultStr = tokanMap.get("tokenStr");
		} else {// 说明当前的token值已经不可以再次使用,这时需要重新获取access_token
			initTokanMap();
			resultStr = tokanMap.get("tokenStr");
		}
		return resultStr;
	}

	/**
	 * token是否可用，判断token是否过期，当前时间-之前保存的时间，若大于2小时则表示过期了。
	 * @return
	 */
	private static boolean isvalid() {
		boolean result = false;
		SimpleDateFormat format = new SimpleDateFormat(timeFormat);
		try {
			Date createdTime = format.parse(tokanMap.get("tokenCreatedTime"));
			//getTime()单位：毫秒
			long longtime = (new Date()).getTime() - createdTime.getTime();
			long time = longtime / (1000 * 60 * 60);
			//System.out.println(time);
			if (time >= 2) {
				result = false;
			} else {// 说明当前的token可以使用不用从微信平台再次取
				result = true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println(getToken());
	}
}