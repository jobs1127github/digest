package com.tentinet.weixin.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tentinet.app.excel.model.Person;
//import com.yeepay.Configuration;
//import com.yeepay.DigestUtil;
//import com.yeepay.PaymentForOnlineService;
/***
 * 获取access_token，返回一个json数据结构的数据，
 * 包含：access_token，expires_in，refresh_token，openid，scope。
 * 
 * @author jobs1127
 */
public class HttpClientUtil {
	private final static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
	/**
	 * HttpClient以post请求url方式，获取返回的json数据
	 * @param reqURL 请求的URL
	 * @param params 请求的URL参数
	 * @param encodeCharset 编码
	 * @return
	 */
	public static String sendPostSSLRequest(String reqURL,Map<String, String> params, String encodeCharset) {
		String responseContent = "";
		//new 一个默认的httpClient请求客户端对象
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,20000);
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] chain,String authType) throws CertificateException {
			}
			@Override
			public void checkServerTrusted(X509Certificate[] chain,String authType) throws CertificateException {
			}
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
			@Override
			public void verify(String host, SSLSocket ssl) throws IOException {
			}
			@Override
			public void verify(String host, X509Certificate cert)throws SSLException {
			}
			@Override
			public void verify(String host, String[] cns, String[] subjectAlts)throws SSLException {
			}
			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
		};
		try {
			SSLContext sslContext = SSLContext.getInstance(SSLSocketFactory.TLS);
			sslContext.init(null, new TrustManager[] { trustManager }, null);
			//根据请求的URL new一个httpPost对象
			HttpPost httpPost = new HttpPost(reqURL);
			if (null != params) {
				List<NameValuePair> formParams = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> entry : params.entrySet()) {
					formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(formParams,encodeCharset));
			}
			//请求的客户端，执行post请求
			HttpResponse response = httpClient.execute(httpPost);
			//获取response的实体对象，把response对象里的数据转换成字符串返回。
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity,encodeCharset);
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}
	
	/**
	 * 通过HttpClient以get请求url方式，获取返回的json数据
	 * @param reqURL
	 * @param params get方式，参数在url里?后面
	 * @param encodeCharset
	 * @return
	 */
	public static String get_return_json_string(String url) {  
		DefaultHttpClient httpclient = new DefaultHttpClient();  
		String body = null;  
		HttpGet get = new HttpGet(url);  
		body = invoke(httpclient, get);  
		httpclient.getConnectionManager().shutdown();  
		return body;  
	}  
	private static String invoke(DefaultHttpClient httpclient,HttpUriRequest httpost) {  
        HttpResponse response = sendRequest(httpclient, httpost);  
        String body = paseResponse(response);  
        return body;  
    }
	/**
	 * 通过HttpClient以post请求url方式，获取返回的json数据
	 * @param reqURL
	 * @param params 提交给服务器的参数
	 * @param encodeCharset
	 * @return
	 */
	public static String post_return_json_string(String url, Map<String, String> params) {  
		if(params == null) {
    		params = new HashMap<String,String>();
    	}
        DefaultHttpClient httpclient = new DefaultHttpClient();  
        String body = null;  
        HttpPost post = postForm(url, params);  
        body = invoke(httpclient, post);  
        httpclient.getConnectionManager().shutdown();  
        return body;  
    }  
	private static String p1_MerId 			= "10001126856"; 			// 商家ID
	private static String queryRefundReqURL = "https://www.yeepay.com/app-merchant-proxy/command";	// 请求地址
	private static String keyValue 			= "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";				// 商家密钥
	private static String query_Cmd  		= "QueryOrdDetail";       										// 订单查询请求，固定值” QueryOrdDetail”
	private static String buy_Cmd 	 		= "Buy";       		   											// 订单查询请求，固定值” Buy”
	private static String refund_Cmd 		= "RefundOrd";			   										// 退款请求，固定值 ” RefundOrd”
	private static String decodeCharset 	= "GBK";			   											// 定义编码格式
	private static String EMPTY = "";
	public static void main(String[] args) {
		List responseStr = null; 
		String url = queryRefundReqURL;
		Map<String,String> reParams = new HashMap<String,String>();
		reParams.put("p0_Cmd", query_Cmd);
		reParams.put("p1_MerId", p1_MerId);
		reParams.put("p2_Order", "jobs1127_999");
		String hmac = DigestUtil.getHmac(new String[] {query_Cmd,p1_MerId,"jobs1127_999"},keyValue);
		reParams.put("hmac", hmac);
		String xml = post_return_json_string(url,reParams);
		System.out.println(xml);
		System.out.println("--------------------");
		String[] data = xml.split("=");
		Map<String,String> map = new LinkedHashMap<String,String>();
		for(int i=0;i<data.length;i++){
			String[] has  = data[i].split("\n");
			for(int j=0;j<has.length;j++){
				//System.out.println(has[j]);
				map.put(has[j], has[j]);
			}
		}
		List<String> list = new ArrayList<String>();
		for(String key:map.keySet()){
			list.add(key);
		}
		System.out.println("list.szie="+list.size());
		System.out.println(list.get(24));
	}
	private static HttpPost postForm(String url, Map<String, String> params){  
    	if(params == null) {
    		params = new HashMap<String,String>();
    	}
    	HttpPost httpost = new HttpPost(url);  
        List<NameValuePair> nvps = new ArrayList <NameValuePair>();  
        Set<String> keySet = params.keySet();  
        for(String key : keySet) {  
            nvps.add(new BasicNameValuePair(key, params.get(key)));  
        }  
        try {  
            httpost.setEntity(new UrlEncodedFormEntity(nvps,HTTP.UTF_8));  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return httpost;  
    }  
	//解析response对象里的数据
	private static String paseResponse(HttpResponse response) {  
        HttpEntity entity = response.getEntity();  
        String charset = EntityUtils.getContentCharSet(entity);  
        String body = null;  
        try {  
            body = EntityUtils.toString(entity);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return body;  
    }
	//发送请求
	private static HttpResponse sendRequest(DefaultHttpClient httpclient, HttpUriRequest httpost) {  
        HttpResponse response = null;  
        try {  
            response = httpclient.execute(httpost);  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return response;  
    }  
	
	/** 
     * 发送get请求  返回json对象
     * @param url 路径 
     * @return 返回json对象
     */  
    public static JSONObject httpGet_return_JSONObject(String url){  
        //get请求返回结果  
        JSONObject jsonResult = null;  
        try {  
            DefaultHttpClient client = new DefaultHttpClient();  
            //发送get请求  
            HttpGet request = new HttpGet(url);  
            HttpResponse response = client.execute(request);  
            /**请求发送成功，并得到响应**/  
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                /**读取服务器返回过来的json字符串数据**/  
                String strResult = EntityUtils.toString(response.getEntity());  
                /**把json字符串转换成json对象**/  
                jsonResult = JSONObject.fromObject(strResult);  
                url = URLDecoder.decode(url, "UTF-8");  
            } else {  
                System.out.println("get请求提交失败:" + url);  
            }  
        } catch (IOException e) {  
        	e.printStackTrace();
        }  
        return jsonResult;  
    } 
    
    /** 
     * post请求  返回json对象
     * @param url url地址 
     * @param jsonParam 参数 
     * @param noNeedResponse 不需要返回结果 
     * @return 
     */  
    public static JSONObject httpPost_return_JSONObject(String url,JSONObject jsonParam){  
        //post请求返回结果  
        DefaultHttpClient httpClient = new DefaultHttpClient();  
        JSONObject jsonResult = null;  
        HttpPost method = new HttpPost(url);  
        try {  
            if (null != jsonParam) {  
                //解决中文乱码问题  
                StringEntity entity = new StringEntity(jsonParam.toString(),"utf-8");  
                entity.setContentEncoding("UTF-8");  
                entity.setContentType("application/json");  
                method.setEntity(entity);  
            }  
            HttpResponse result = httpClient.execute(method);  
            url = URLDecoder.decode(url, "UTF-8");  
            /**请求发送成功，并得到响应**/  
            if (result.getStatusLine().getStatusCode() == 200) {  
                String str = "";  
                try {  
                    /**读取服务器返回过来的json字符串数据**/  
                    str = EntityUtils.toString(result.getEntity());  
                    /**把json字符串转换成json对象**/  
                    jsonResult = JSONObject.fromObject(str);  
                } catch (Exception e) {  
                   e.printStackTrace();
                }  
            }  
        } catch (IOException e) {  
        	 e.printStackTrace();
        }  
        return jsonResult;  
    }  
    
    //测试方式 post
    private static void postRequestTest() {  
        String url = "http://localhost:8080/SpringMVC/process";  
        Person userDTO = new Person();  
        userDTO.setName("zhangshang");  
        userDTO.setSex('男');
        /** 把一个普通对象，转换成Json对象 */
        JSONObject jsonParam = JSONObject.fromObject(userDTO);  
        JSONObject responseJSONObject = httpPost_return_JSONObject(url, jsonParam);  
        if(responseJSONObject != null && "SUCCESS".equals(responseJSONObject.get("status"))) {  
            JSONObject userStr = (JSONObject) responseJSONObject.get("userDTO");  
            /** Json对象转换成普通对象 */
            userDTO = (Person) JSONObject.toBean(userStr, Person.class);  
        } else {  
            System.out.println("http Post request process fail");  
        }  
    } 
    //测试方式 get
    private static void getRequestTest() {  
        String url = "http://localhost:8080/SpringMVC/greet?name=lisi";  
        JSONObject jsonObject = httpGet_return_JSONObject(url);  
        if(jsonObject != null) {  
            String userName = (String) jsonObject.get("userName");  
            log.info("http Get request process sucess");  
            log.info("userName:" + userName);  
        } else {  
        	log.info("http Get request process fail");  
        }  
    }  
}