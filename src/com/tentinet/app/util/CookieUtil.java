package com.tentinet.app.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import com.tentinet.weixin.util.UrlUtil;

public class CookieUtil {
	/**
	 * 获得cookie,cookie的值进行URL编码，获取时进行解码，防止中文乱码
	 * @param request
	 * @return
	 * 【ServletActionContext.getRequest()】这是struts2中的写法，servlet直接request即可
	 * 
	 * 浏览器去请求服务器，则在request请求对象中去获取通过浏览器存放在本地电脑上的cookie。
	 */
    public static String getCookie(HttpServletRequest request,String name){
        Cookie cookies[] = request.getCookies();
        for(int i=0;cookies!=null && i<cookies.length;i++){
            Cookie cookie = cookies[i];
            if(cookie.getName().equals(name)){
            	return UrlUtil.decode(cookie.getValue());
            }
        }
        return null;
    }
}
