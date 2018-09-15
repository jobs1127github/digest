package com.tentinet.weixin.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 读取项目中的properties中的配置项
 * @author jobs1127
 */
public class ConfigUtils {
	private static final Logger logger = Logger.getLogger(ConfigUtils.class);

	private static Properties projectProperties = null;
	//通过getResourceAsStream根据项目的相对路径，获取文件资源
	static {
		//该文件在classpath路径下
		String configFileRelativePath = "/properties/weixin-config.properties";
		/**
		 * ConfigUtils.class.getResourceAsStream默认是从当前的ConfigUtils.class相同的路径
		 * 查找文件
		 */
		InputStream in = ConfigUtils.class.getResourceAsStream(configFileRelativePath);
		projectProperties = new Properties();
		try {
			/***
			 * 以流的方式把硬盘上的文件，通过Properties属性文件加载到程序内存中。
			 */
			projectProperties.load(in);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public static String read_wx_menu_txt() {
		// 读取txt内容为字符串
		StringBuffer txtContent = new StringBuffer();
		InputStream in = null;
		// 每次读取的byte数，把wx_menu.txt文件读取到内存
		byte[] b = new byte[8 * 1024];
		try {
			// 文件输入流
			String configFileRelativePath = "/properties/wx_menu.txt";
			in = ConfigUtils.class.getResourceAsStream(configFileRelativePath);
			if(in != null) {
				while (in.read(b) != -1) {
					// 字符串拼接，把读取到的字节包装成String
					txtContent.append(new String(b,"utf-8"));
				}
				in.close();// 关闭流
			} else {
				logger.error("文件输入流 为null");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return txtContent.toString();
	}
	/**
	 * get system parameter by config file
	 * @param key
	 * @return codeValue
	 */
	public static String getValue(String key) {
		String Str = "";
		try {
			Str = new String((projectProperties.getProperty(key)).getBytes("ISO8859_1"),"UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return Str;
	}
	public static String getValueByKey(String key) {
		return projectProperties.getProperty(key);
	}
	/**
	 * get system parameter by config file
	 * @param key
	 * @param seperator
	 * @return codeValues
	 */
	public static List<String> getValues(String key, String seperator) {
		List<String> paraList = new ArrayList<String>();
		String value = getValue(key);
//		方式1
//		if (value != null) {
//			StringTokenizer st = new StringTokenizer(value,
//					StringUtils.isBlank(seperator) ? "," : seperator);
//			while (st.hasMoreElements()) {
//				paraList.add(st.nextElement().toString().trim());
//			}
//		}
		if(value != null) {
			for(String s:value.split(",")) {
				if(!paraList.contains(s)) {
					paraList.add(s);
				}
			}
		}
		return paraList;
	}
}
