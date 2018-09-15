package com.tentinet.weixin.util;

import net.sf.json.JSONObject;

/**
 * Json结果
 * @author Jobs1127
 */
public class JsonResult {
	public static JSONObject success() {
		JSONObject map = new JSONObject();
		map.put("code", 0);
		return map;
	}
	//把一个Object对象封装成了Json对象
	public static JSONObject success(Object data) {
		JSONObject map = new JSONObject();
		map.put("code", 0);
		map.put("data", data);
		return map;
	}

	public static JSONObject fail() {
		JSONObject map = new JSONObject();
		map.put("code", -1);
		map.put("data", "error");
		return map;
	}
	//把一个Object对象封装成了Json对象
	public static JSONObject fail(String msg) {
		JSONObject map = new JSONObject();
		map.put("code", -1);
		map.put("data", msg);
		return map;
	}
}