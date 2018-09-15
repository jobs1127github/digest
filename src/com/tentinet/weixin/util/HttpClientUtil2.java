package com.tentinet.weixin.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * 
 * @author jobs1127
 */
public class HttpClientUtil2 {
	private final static Logger log = LoggerFactory.getLogger(HttpClientUtil2.class);
	/**
	 * httpClient的get请求方式
	 * @param url 请求的URL
	 * @param charset 编码
	 * @return String 把请求后的结果包装到String字符串里返回。
	 * @throws Exception
	 */
	public static String doGet_ruturn_String(String url, String charset) throws Exception {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);
		if (null == url || !url.startsWith("http")) {
			throw new Exception("请求地址格式不对");
		}
		// 设置请求的编码方式
		if (null != charset) {
			method.addRequestHeader("Content-Type",
					"application/x-www-form-urlencoded; charset=" + charset);
		} else {
			method.addRequestHeader("Content-Type",
					"application/x-www-form-urlencoded; charset=" + "utf-8");
		}
		//httpClient执行get方法
		int statusCode = client.executeMethod(method);
		if (statusCode != HttpStatus.SC_OK) {// 打印服务器返回的状态
			System.out.println("Method failed: " + method.getStatusLine());
		}
		// 返回响应消息
		byte[] responseBody = 
				method.getResponseBodyAsString().getBytes(method.getResponseCharSet());
		// 在返回响应消息使用编码(utf-8或gb2312)
		String response = new String(responseBody, "utf-8");
		System.out.println("------------------response:" + response);
		// 释放连接
		method.releaseConnection();
		return response;
	}

	/**
	 * httpClient的get请求方式
	 * @param url 请求的URL
	 * @param charset 编码
	 * @return String 把请求后的结果包装到String字符串里返回。
	 * @throws Exception
	 */
	public static String doGet2_ruturn_String(String url, String charset) throws Exception {
		if(charset == null) {
			charset = CharEncoding.UTF_8;
		}
		/**
		 * 使用 GetMethod 来访问一个 URL 对应的网页,
		 * 实现步骤: 
		 * 1:生成一个 HttpClinet 对象并设置相应的参数。
		 * 2:生成一个 GetMethod 对象并设置响应的参数。 
		 * 3:用 HttpClinet 生成的对象来执行 GetMethod 生成的Get方法。
		 * 4:处理响应状态码。
		 * 5:若响应正常，处理 HTTP 响应内容。
		 * 6:释放连接。
		 */
		/* 1 生成 HttpClinet 对象并设置参数 */
		HttpClient httpClient = new HttpClient();
		// 设置 Http 连接超时为5秒
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		/* 2 生成 GetMethod 对象并设置参数 */
		GetMethod getMethod = new GetMethod(url);
		// 设置 get 请求超时为 5 秒
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
		// 设置请求重试处理，用的是默认的重试处理：请求三次
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
		String response = "";
		/* 3 执行 HTTP GET 请求 */
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			/* 4 判断访问的状态码 */
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "+ getMethod.getStatusLine());
			}
			/* 5 处理 HTTP 响应内容 */
			// HTTP响应头部信息，这里简单打印
			Header[] headers = getMethod.getResponseHeaders();
			for (Header h : headers) {
				System.out.println(h.getName() + "------------ " + h.getValue());
			}
			// 读取 HTTP 响应内容，这里简单打印网页内容
			byte[] responseBody = getMethod.getResponseBody();// 读取为字节数组
			response = new String(responseBody, charset);
			System.out.println("----------response:" + response);
			// 读取为 InputStream，在网页内容数据量大时候推荐使用
			// InputStream response = getMethod.getResponseBodyAsStream();
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace(); // 发生网络异常
		} finally {
			/* 6 .释放连接 */
			getMethod.releaseConnection();
		}
		return response;
	}

	/**
	 * httpClient的post请求方式 执行一个HTTP POST请求
	 * 
	 * @param url请求的URL地址
	 * @param params请求的查询参数,可以为null
	 * @param charset字符集
	 * @param pretty 是否美化
	 */
	public static String doPost_return_string(String url,
			Map<String, Object> _params, String charset, boolean pretty) {
		if(charset == null) {
			charset = CharEncoding.UTF_8;
		}
		StringBuffer response = new StringBuffer();
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		// 设置Http Post数据
		if (_params != null) {
			for (Map.Entry<String, Object> entry : _params.entrySet()) {
				method.setParameter(entry.getKey(),
						String.valueOf(entry.getValue()));
			}
		}
		// 设置Http Post数据 方法二
		// if(_params != null) {
		// NameValuePair[] pairs = new NameValuePair[_params.size()];//纯参数了，键值对
		// int i = 0;
		// for (Map.Entry<String, Object> entry : _params.entrySet()) {
		// pairs[i] = new NameValuePair(entry.getKey(),
		// String.valueOf(entry.getValue()));
		// i++;
		// }
		// method.addParameters(pairs);
		// }
		try {
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				// 读取为 InputStream，在网页内容数据量大时候推荐使用
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(method.getResponseBodyAsStream(),
								charset));
				String line;
				while ((line = reader.readLine()) != null) {
					if (pretty) {
						response.append(line).append(
								System.getProperty("line.separator"));
					} else {
						response.append(line);
					}
				}
				reader.close();
			}
		} catch (IOException e) {
			System.out.println("执行HTTP Post请求" + url + "时，发生异常！");
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		System.out.println("--------------------" + response.toString());
		return response.toString();
	}

	/**
	 * HttpClient 发送xml数据请求服务器
	 * 
	 * @param url xml请求数据地址
	 * @param xmlString 发送的xml数据流
	 * @return null发送失败，否则返回响应内容
	 */
	public String post_send_xml(String url, String xmlFileName) {
		// 关闭
		System.setProperty("org.apache.commons.logging.Log","org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime","true");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient","stdout");
		// 创建httpclient工具对象
		HttpClient client = new HttpClient();
		// 创建post请求方法
		PostMethod myPost = new PostMethod(url);
		// 设置请求超时时间
		client.setConnectionTimeout(300 * 1000);
		String responseString = null;
		try {
			// 设置请求头部类型
			myPost.setRequestHeader("Content-Type", "text/xml");
			myPost.setRequestHeader("charset", "utf-8");
			// 设置请求体，即xml文本内容，注：这里写了两种方式，一种是直接获取xml内容字符串，一种是读取xml文件以流的形式
			// myPost.setRequestBody(xmlString);
			InputStream body = this.getClass().getResourceAsStream("/" + xmlFileName);
			myPost.setRequestBody(body);
			// xml 是string
			// myPost.setRequestEntity(new StringRequestEntity(xmlString,"text/xml","utf-8"));
			int statusCode = client.executeMethod(myPost);
			if (statusCode == HttpStatus.SC_OK) {
				BufferedInputStream bis = new BufferedInputStream(myPost.getResponseBodyAsStream());
				byte[] bytes = new byte[1024];
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				int count = 0;
				while ((count = bis.read(bytes)) != -1) {
					bos.write(bytes, 0, count);
				}
				byte[] strByte = bos.toByteArray();
				responseString = new String(strByte, 0, strByte.length, "utf-8");
				bos.close();
				bis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			myPost.releaseConnection();
		}
		return responseString;
	}

	/**
	 * 用传统的URI类进行请求 发送xml数据请求到server端
	 * @param urlStr
	 */
	public void post_send_xml(String urlStr) {
		try {
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Pragma:", "no-cache");
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Content-Type", "text/xml");
			OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
			String xmlInfo = getXmlInfo();
			System.out.println("urlStr=" + urlStr);
			System.out.println("xmlInfo=" + xmlInfo);
			out.write(new String(xmlInfo.getBytes("UTF-8")));
			out.flush();
			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				System.out.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 构建xml
	 * @return
	 */
	private String getXmlInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append("<Message>");
		sb.append(" <header>");
		sb.append("     <action>readMeetingStatus</action>");
		sb.append("     <service>meeting</service>");
		sb.append("     <type>xml</type>");
		sb.append("     <userName>admin</userName>");
		sb.append("     <password>admin</password>");
		sb.append("     <siteName>box</siteName>");
		sb.append(" </header>");
		sb.append(" <body>");
		sb.append("     <confKey>43283344</confKey>");
		sb.append(" </body>");
		sb.append("</Message>");
		return sb.toString();
	}
}