package com.tentinet.app.action;

import java.io.IOException;
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

import com.tentinet.app.util.Page;

/**
 * 1、得到项目路径 
 * 2、日期 字符串互转
 * 3、Json返回方法构建，通过Json对象把数据写入到response 
 * 
 * @author jobs1127
 */
@Controller
public class BaseAction {
	protected static Logger log = Logger.getLogger(BaseAction.class);

	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");

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
	 * Json返回方法构建，通过Json把数据写入到response
	 */
	public static void writeResponseByJson(HttpServletRequest request,HttpServletResponse response, JSONObject oj) {
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
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		list.add("7");
		list.add("8");
		list.add("9");
		if(CollectionUtils.isNotEmpty(list)) {
			List<String> newList = list.subList(0,3);
			for(String e:newList) {
				System.out.println(e);
			}
		}
		//System.out.println(CollectionUtils.size(list));
	}

	/**
	 * list容器中根据page的值实现分页,list已经查询出了所有的数据对象，通过分页显示，展示list中某个视图。
	 * 泛型的应用
	 * @param <T>
	 * List<T> java.util.List.subList(int fromIndex, int toIndex)
	 * fromIndex从哪个下标开始，toIndex到哪个下标 fromIndex <= x < toIndex
	 */
	public static <T> List<T> getPageList(List<T> list, Page page) {
		List<T> newList = null;
		if (CollectionUtils.isNotEmpty(list)) {// 先确认list是否为空
			newList = new ArrayList<T>();
			if (page.getPageNo()*page.getPageSize()>list.size()) {//说明没有下一页了。
				/**极端的情况，比如pageNo=1，list.size=9,但是pageSize=15*/
				if (page.getPageNo() > 1) {// 说明不是首页
					int fromIndex = (page.getPageNo()-1)*page.getPageSize();
					int toIndex = list.size();
					newList.addAll(list.subList(fromIndex,toIndex));
				} else {// 如果只有一页的情况下，即pageNo=1那就拿取所有的数据。
					newList.addAll(list.subList(0, list.size()));
				}
			} else {// 说明当前还有下一页
				int fromIndex = (page.getPageNo()-1)*page.getPageSize();
				int toIndex = (page.getPageNo()*page.getPageSize());
				newList.addAll(list.subList(fromIndex,toIndex));
			}
		}
		return newList;
	}
}
