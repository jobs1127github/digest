package com.tentinet.app.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class Utils {
	public static final String SUCCESS_CODE = "00";
	public static final String ERROR_CODE = "01";
	public static final String SUCCESS_MSG = "操作成功";
	public static final String ERROR_MSG = "操作失败";

	public static Map<String, Object> resutlMessage(Object data, String code,
			String msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", data);
		map.put("success", code);
		map.put("msg", msg);
		return map;
	}

	/**
	 * 错误信息
	 * 
	 * @param msg
	 * @return
	 */
	public static Map<String, Object> resutlMessageError(String msg) {
		return resutlMessage("", ERROR_CODE, msg);
	}

	/**
	 * 成功信息
	 * 
	 * @param data
	 * @return
	 */
	public static Map<String, Object> resutlMessageSuccess(Object data) {
		return resutlMessage(data, SUCCESS_CODE, SUCCESS_MSG);
	}

	/**
	 * 获取HttpServletRequest 对象
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request;
	}

	/**
	 * 获取访问者IP
	 */
	public static String getAccessIp() {
		HttpServletRequest request = getRequest();
		String ipAddress = null;
		// ipAddress = this.getRequest().getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (("127.0.0.1").equals(ipAddress)) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}

		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
}
