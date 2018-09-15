package com.tentinet.weixin.util;

import java.util.UUID;

/**
 * UUID生成工具
 * @author Administrator
 *
 */
public class UUIDUtils {
	/**
	 * 生成UUID
	 * @return
	 */
  public static  String getUUID(){
	    UUID uuid = UUID.randomUUID();
	  return uuid.toString();
  }
}
