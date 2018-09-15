/**
 * 
 */
package cn.jobs1127.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author jobs1127
 * 静态设计模式/单例设计模式,该Properties对象是一个单例模式
 */
public class PropertiesUtils {
	public static Properties properties = null;
	static {
		String classAndClassLoader_path = "/properties/classAndClassLoader.properties";
		String factory_model_path = "/properties/factory-model.properties";
		InputStream classAndClassLoader_path_in = PropertiesUtils.class.getResourceAsStream(classAndClassLoader_path);
		InputStream factory_model_path_in = PropertiesUtils.class.getResourceAsStream(factory_model_path);
		properties = new Properties();
		try {
			properties.load(classAndClassLoader_path_in);
			properties.load(factory_model_path_in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String getValue(String key) {
		return properties.getProperty(key);
	}
}
