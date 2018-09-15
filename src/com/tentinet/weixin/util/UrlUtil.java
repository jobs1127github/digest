package com.tentinet.weixin.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.lang.CharEncoding;
/**
 * 对url进行encode和decode
 * 
 * @author jobs1127
 *
 */
public class UrlUtil {
	/**
	 * 对url进行encode
	 * @param CharEncoding.UTF_8
	 * @param encoding
	 * @return
	 */
	public static String encode(String s) {
		return encode(s, CharEncoding.UTF_8);
	}
	/**
	 * 对url进行encode
	 * @param s
	 * @param encoding
	 * @return
	 */
	public static String encode(String s, String encoding) {
		try {
			return URLEncoder.encode(s, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	/**
	 * 对url进行decode
	 * @param s CharEncoding.UTF_8
	 * @param encoding
	 * @return
	 */
	public static String decode(String s) {
		return decode(s, CharEncoding.UTF_8);
	}
	/**
	 * 对url进行decode
	 * @param s
	 * @param encoding
	 * @return
	 */
	public static String decode(String s, String encoding) {
		try {
			return URLDecoder.decode(s, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
}