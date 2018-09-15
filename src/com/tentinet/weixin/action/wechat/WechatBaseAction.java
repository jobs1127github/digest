package com.tentinet.weixin.action.wechat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tentinet.app.util.Page;

@Controller
public class WechatBaseAction {
	protected static Logger log = Logger.getLogger(WechatBaseAction.class);

	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DecimalFormat df = new DecimalFormat("######0.00");

	/**
	 * 得到项目路径
	 * 
	 * @return
	 */
	public static String getBasePath(HttpServletRequest request) {
		StringBuffer basePath = new StringBuffer();
		basePath.append(request.getContextPath());
		return basePath.toString();
	}

	/**
	 * Json返回方法构建
	 */
	public static void writeResponseByJson(HttpServletRequest request,
			HttpServletResponse response, JSONObject oj) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(oj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * String转时间
	 */
	public static Date strToDate(String str) {
		try {
			return format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 时间转String
	 */
	public static String dateToStr(Date date) {
		String result = "";
		result = format.format(date);
		return result;
	}

	/**
	 * list中根据page的值实现分页
	 * 
	 * @param <T>
	 */
	public static <T> List<T> getPageList(List<T> list, Page page) {
		List<T> newList = null;
		if (CollectionUtils.isNotEmpty(list)) {// 先确认list是否为空
			newList = new ArrayList<T>();
			if (page.getPageNo() * page.getPageSize() > list.size()) {// 说明list的集合数比
																		// page中的总数要小
				if (page.getPageNo() > 1) {// 说明不是首页
					newList.addAll(list.subList(
							(page.getPageNo() - 1) * page.getPageSize(),
							list.size()));
				} else {// 说明是首页 小说15
					newList.addAll(list.subList(0, list.size()));
				}
			} else {// 说明当前还有下一页
				newList.addAll(list.subList(
						(page.getPageNo() - 1) * page.getPageSize(),
						page.getPageNo() * page.getPageSize()));
			}
		}
		return newList;
	}
	
	/**
	 * 根据String构建List
	 * @param str
	 * @return
	 */
	public static  List<String> buildListFromStr(String str){
		String[]objs=str.split(",");
		List<String>result=new ArrayList<String>();
		for(String text:objs){
			result.add(text);
		}
		return result;
	}
	
	public static void main(String[] args) {
		buildListFromStr("1,2,3");
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
	 * 获取openId
	 * @return
	 */
	public static String getCurrentOpenId(){
		String openId = (String) getRequest().getSession().getAttribute("openId");
		// log.info("-------------openid-------->" + openId);
		return openId;
	}
}
