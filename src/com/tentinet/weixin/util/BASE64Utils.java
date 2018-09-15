package com.tentinet.weixin.util;

import sun.misc.BASE64Decoder;

/**
 * 因为微信用户的名称，有的有图片，所以要进行加密与解密的动作
 * 
 * @author Jobs1127
 */
public class BASE64Utils {
	// 将 s 进行 BASE64 编码
	public static String getBASE64(String s) {
		if (s == null) {
			return null;
		} else {
			return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
		}
	}

	// 将 BASE64 编码的字符串 s 进行解码
	public static String getFromBASE64(String s) {
		if (s == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			return new String(b);
		} catch (Exception e) {
			return null;
		}
	}
	public static void main(String[] args) {
		System.out.println(getFromBASE64("4AvVhmFLUs0KTA3Kprsdag=="));
	}
}