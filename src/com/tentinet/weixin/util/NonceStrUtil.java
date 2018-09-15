package com.tentinet.weixin.util;

import java.util.Random;
/**
 * 随机码 
 * @author jobs1127
 *
 */
public class NonceStrUtil {
	 public static String getRandomStringByLength() {
	        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
	        Random random = new Random();
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < 31; i++) {
	            int number = random.nextInt(base.length());
	            sb.append(base.charAt(number));
	        }
	        return sb.toString();
	    }
	 public static void main(String[] args) {
		System.out.println(getRandomStringByLength());
	}
}
