package cn.jobs1127.utils;

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

	private static Properties projectProperties = null;

	static {
		String configFileRelativePath = "/properties/config.properties";
		InputStream in = ConfigUtil.class.getResourceAsStream(configFileRelativePath);
		projectProperties = new Properties();
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

	/**
	 * get system parameter by config file
	 * 根据key和分隔符，获取value集合
	 * @param key
	 * @param seperator
	 * @return codeValues
	 */
	public static List<String> getValues(String key, String seperator) {
		List<String> paraList = new ArrayList<String>();
		String value = getValue(key);
		if (value != null) {
			//java字符串分解
			StringTokenizer st = new StringTokenizer(value,StringUtils.isBlank(seperator) ? "," : seperator);
			while (st.hasMoreElements()) {
				paraList.add(st.nextElement().toString().trim());
			}
		}
		return paraList;
	}
}
