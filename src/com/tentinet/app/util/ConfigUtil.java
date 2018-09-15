package com.tentinet.app.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
/**
 * 处理属性文件,把配置文件，加载到内存 key value 
 */

public class ConfigUtil {
	private static final Logger logger = Logger.getLogger(ConfigUtil.class);

	private static Properties projectProperties = new Properties();

	static {
		InputStream in = ConfigUtil.class.getResourceAsStream("upload_config.properties");
		try {
			projectProperties.load(in);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	/**
	 * get system parameter by config file
	 * 根据key拿到value
	 * @param key
	 * @return codeValue
	 */
	public static String getValue(String key) {
		return projectProperties.getProperty(key);
	}
	public static void main(String[] args) {
		for(String s:getValues("","")) {
			System.out.println(s);
		}
	}

	/**
	 * get system parameter by config file
	 * 根据key和分隔符，获取value集合
	 * @param key
	 * @param seperator
	 * @return codeValues
	 */
	public static List<String> getValues(String key, String separatorChar) {
		List<String> paraList = new ArrayList<String>();
		String value = getValue(key);
		//value = "hah,aa,bb,cc";
		if (value != null) {
			//java字符串分解 方式1
			/**
			StringTokenizer st = new StringTokenizer(value,StringUtils.isBlank(separatorChar) ? "," : separatorChar);
			while (st.hasMoreElements()) {
				paraList.add(st.nextElement().toString().trim());
			}
			**/
			/**
			 * 没传递过来分隔符，默认用","分隔。方式2
			 */
			String[] ss = StringUtils.split(value, StringUtils.isBlank(separatorChar) ? "," : separatorChar);
			for(String s:ss) {
				paraList.add(s);
			}
		}
		return paraList;
	}
}
