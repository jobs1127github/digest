package com.tentinet.weixin.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.tentinet.weixin.entity.WXUrl;
/**
 * 
 * @author Jobs1127
 *
 */
public class HttpConntionUtil {
	private static HttpURLConnection conn;

	//HttpURLConnection 把目标URL连接起来，通过HttpURLConnection方式完成，也可以用HttpClient来完成，很多方法
	public static HttpURLConnection buildConn() {
		try {
			//统一下单url
			String unifiedorder_url = WXUrl.UNIFIEDORDER_URL;
			URL url = new URL(unifiedorder_url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(100000);
			conn.setReadTimeout(100000);
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			conn.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
			conn.setRequestMethod("POST");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	/**
	 * 向微信发送订单信息
	 * @param xml
	 */
	public static void sendWXRequestXml(String xml) {
		buildConn();
		try {
			OutputStream out = conn.getOutputStream();
			out.write(xml.getBytes("UTF-8"));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到微信的返回报文
	 * 
	 * @return
	 */
	public static String getWXResponseXml() {
		String resultXml = null;
		InputStream in = null;
		BufferedReader read = null;
		try {
			if(conn != null) {
				in = conn.getInputStream();
				read = new BufferedReader(new InputStreamReader(in, "UTF-8"));
				String valueStr = null;
				StringBuffer bufferRes = new StringBuffer();
				while ((valueStr = read.readLine()) != null) {
					bufferRes.append(valueStr);
				}
				resultXml = bufferRes.toString();
				System.out.println("resultXml:" + resultXml);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				read.close();
				if (conn != null) {
					conn.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultXml;
	}
	
	/**
	 * 创建HttpClient对象。(通过证书获得)。
	 * 欢迎使用微信支付！
		微信支付API共四份（证书pkcs12格式、证书pem格式、证书密钥pem格式、CA证书）,为接口中强制要求时需携带的证书文件。
		证书属于敏感信息，请妥善保管不要泄露和被他人复制。
		不同开发语言下的证书格式不同，以下为说明指引：
		证书pkcs12格式（apiclient_cert.p12）
		包含了私钥信息的证书文件，为p12(pfx)格式，由微信支付签发给您用来标识和界定您的身份
		部分安全性要求较高的API需要使用该证书来确认您的调用身份
		windows上可以直接双击导入系统，导入过程中会提示输入证书密码，证书密码默认为您的商户ID（如：10010000）
		证书pem格式（apiclient_cert.pem）
		从apiclient_cert.p12中导出证书部分的文件，为pem格式，请妥善保管不要泄漏和被他人复制
		部分开发语言和环境，不能直接使用p12文件，而需要使用pem，所以为了方便您使用，已为您直接提供
		您也可以使用openssl命令来自己导出：openssl pkcs12 -clcerts -nokeys -in apiclient_cert.p12 -out apiclient_cert.pem
		证书密钥pem格式（apiclient_key.pem）
		从apiclient_cert.p12中导出密钥部分的文件，为pem格式
		部分开发语言和环境，不能直接使用p12文件，而需要使用pem，所以为了方便您使用，已为您直接提供
		您也可以使用openssl命令来自己导出：openssl pkcs12 -nocerts -in apiclient_cert.p12 -out apiclient_key.pem
		CA证书（rootca.pem）
		微信支付api服务器上也部署了证明微信支付身份的服务器证书，您在使用api进行调用时也需要验证所调用服务器及域名的真实性
		该文件为签署微信支付证书的权威机构的根证书，可以用来验证微信支付服务器证书的真实性
		某些环境和工具已经内置了若干权威机构的根证书，无需引用该证书也可以正常进行验证，这里提供给您在未内置所必须根证书的环境中载入使用
	 */
	private static CloseableHttpClient getHttpClient() {
		FileInputStream instream =null;
		KeyStore keyStore=null;
		CloseableHttpClient httpclient=null;
		try {
			keyStore = KeyStore.getInstance("PKCS12");
			instream = new FileInputStream(new File(ConfigUtils.getValue("cert_path")));
			keyStore.load(instream, ConfigUtils.getValue("mch_id").toCharArray());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				instream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// Trust own CA and all self-signed certs相信自己的CA和所有自签名的证书 
		try {
			SSLContext sslcontext = SSLContexts.custom()
					.loadKeyMaterial(keyStore, ConfigUtils.getValue("mch_id").toCharArray()).build();
			// Allow TLSv1 protocol only
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslcontext, new String[] { "TLSv1" }, null,
					SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			 httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return httpclient;
	}
	
	/**
	 * 请求发送红包url,是通过HttpClient的方式完成的
	 * @param requestXml 请求发红包的URL时，提交给服务器端的参数
	 * @return 红包发送后得到相应的返回值，并拼成xml格式String
	 * 
	 * 使用HttpClient发送请求、接收响应很简单，一般需要如下几步即可。
		1. 创建HttpClient对象。
		2. 创建请求方法的实例，并指定请求URL。如果需要发送GET请求，创建HttpGet对象；如果需要发送POST请求，创建HttpPost对象。
		3. 如果需要发送请求参数，可调用HttpGet、HttpPost共同的setParams(HetpParams params)方法来添加请求参数；对于HttpPost对象而言，也可调用setEntity(HttpEntity entity)方法来设置请求参数。
		4. 调用HttpClient对象的execute(HttpUriRequest request)发送请求，该方法返回一个HttpResponse。
		5. 调用HttpResponse的getAllHeaders()、getHeaders(String name)等方法可获取服务器的响应头；调用HttpResponse的getEntity()方法可获取HttpEntity对象，该对象包装了服务器的响应内容。程序可通过该对象获取服务器的响应内容。
		6. 释放连接。无论执行方法是否成功，都必须释放连接。
	 */
	public static String sendHBRequest(String requestXml) {
		StringBuffer resultXml=new StringBuffer();
		CloseableHttpClient httpclient = getHttpClient();// 拿到httpclient
		HttpPost httpPost = new HttpPost(WXUrl.HB_URL);// 创建HttpPost对象
		CloseableHttpResponse response = null;
		try {
			httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)");
			// 用逗号分隔显示可以同时接受多种编码
			httpPost.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
			httpPost.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
			httpPost.setEntity(new StringEntity(requestXml));
			//调用HttpClient对象的execute(HttpUriRequest request)发送请求
			response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			if (entity != null) {
				System.out.println("Response content length: "+ entity.getContentLength());
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
				String text;
				int i=0;
				resultXml.append("<xml>");
				while ((text = bufferedReader.readLine()) != null) {
					System.out.println("text="+text+"\n\n");
					if(i==1 || i==3){
						resultXml.append(text);
					}
					i++;
				}
				resultXml.append("</xml>");
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("--------resultXml--------="+resultXml.toString());
		return resultXml.toString();
	}
}