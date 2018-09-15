package com.tentinet.app.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
/**
 * 请求返回时的工具包
 * @author jobs1127
 *
 */
public class ResonseUtils {
	/**
	 * 把Json数据写入到response
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
	 * 重写writeResponseByJson()方法，没有Request对象参数，把Json数据写入到response
	 */
	public static void writeResponseByJson(HttpServletResponse response, JSONObject oj) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(oj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 往response对象里写入json数据，因为前台页面的ajax请求的dataType='json',故应该把数据包装成JSON。
	 * @param response
	 * @param data
	 */
	public static void writeJsonResult(HttpServletResponse response, Object data) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";
		try {
			// 通过ObjectMapper对象，把一个Map对象作为字符串写入值，把一个对象转换成JSON对象
			jsonString = mapper.writeValueAsString(data);
			//System.out.println("jsonString="+jsonString);
			/**
			 * 
			 * {
			    "data": {
			        "user_id": 20150004,
			        "user_name": "lvfen",
			        "user_sex": "2",
			        "user_idcard": "431128199011274612",
			        "user_birthday": "2016-08-01",
			        "user_mail": "553116954@qq.com",
			        "user_tentinet_mail": "553116954",
			        "user_weixin_no": null,
			        "status": "Y",
			        "user_pass": "95b2db783c88a3eb503c345b30b13ca4",
			        "user_role": "20150004",
			        "createdBy": null,
			        "updatedBy": null,
			        "createdTime": null,
			        "upadtedTime": null
			    }
			 }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.print(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}
}