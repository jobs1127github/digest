package com.tentinet.weixin.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.tentinet.app.util.DateFill;

/**
 * 微信支付工具类
 * @author Jobs1127
 */
public class WxPayUtil {
	/**
	 * 生成商户订单号
	 * 商户订单号（每个订单号必须唯一）组成： mch_id+yyyymmdd+10位一天内不能重复的数字。
	 * 接口根据商户订单号支持重入， 如出现超时可再调用。
	 * @return
	 * 参考微信开发文档：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1459327193
	 */
	public static String build_mch_billno() {
		String mch_id = ConfigUtils.getValue("mch_id");
		String dateStr = DateFill.DatetoString(new Date(), "yyyymmdd");
		Random random = new Random();
		String result = "";
		for (int i = 0; i < 10; i++) {
			result += random.nextInt(10);
		}
		return mch_id + dateStr + result;
	}

	/**
	 * 构建网页端调起支付API 参数
	 * 
	 * @return
	 */
	public static Map<String, String> buildSendWXMap(Map<String, String> WXReturnMap) {
		String appId = ConfigUtils.getValue("weixin.appId");
		String nonceStr = NonceStrUtil.getRandomStringByLength();
		String prepay_id = WXReturnMap.get("prepay_id");
		String signType = "MD5";
		String paySign = WXReturnMap.get("sign");
		String timeStamp = String.valueOf(System.currentTimeMillis());
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("result_code", WXReturnMap.get("result_code"));
		dataMap.put("return_code", WXReturnMap.get("return_code"));
		dataMap.put("appId", appId);// 商户ID
		dataMap.put("prepay_id", prepay_id);
		dataMap.put("timeStamp", timeStamp);// 时间戳
		dataMap.put("nonceStr", nonceStr);// 随机字符串，不长于32位
		dataMap.put("signType", signType);// 签名方式
		dataMap.put("prepay_id", "prepay_id=" + prepay_id);
		dataMap.put("paySign", paySign);// 签名

		/*
		 * "appId" : data.appId, //公众号名称，由商户传入 "timeStamp" : data.timeStamp,
		 * //时间戳，自1970年以来的秒数 "nonceStr" : data.nonceStr, //随机串 "package" :
		 * data.prepay_id, "signType" : data.signType, //微信签名方式： "paySign" :
		 * data.paySign //微信签名
		 */
		String MD5str = "appId=" + appId + "&nonceStr=" + nonceStr
				+ "&package=" + "prepay_id=" + prepay_id + "&signType="
				+ signType + "&timeStamp=" + timeStamp;

		String sign_key = ConfigUtils.getValue("sign_key");
		StringBuffer signStr = new StringBuffer();
		signStr.append(MD5str).append("&key=").append(sign_key);
		System.out.println("signStr:" + signStr.toString());
		dataMap.put("paySign", bulidMD5Str(signStr.toString()));// 签名
		return dataMap;
	}

	/**
	 * 参考文档：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=7_7&index=6
	 * 构建统一订单报文方法
	 * @param orderPay
	 * @return
	 * 举例如下：
		<xml>
		   <appid>wx2421b1c4370ec43b</appid>
		   <attach>支付测试</attach> 变量名attach 必填：否
		   <body>JSAPI支付测试</body>
		   <mch_id>10000100</mch_id>
		   <detail><![CDATA[{ "goods_detail":[ { "goods_id":"iphone6s_16G", "wxpay_goods_id":"1001", "goods_name":"iPhone6s 16G", "quantity":1, "price":528800, "goods_category":"123456", "body":"苹果手机" }, { "goods_id":"iphone6s_32G", "wxpay_goods_id":"1002", "goods_name":"iPhone6s 32G", "quantity":1, "price":608800, "goods_category":"123789", "body":"苹果手机" } ] }]]></detail> 必填：否
		   <nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str>
		   <notify_url>http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php</notify_url>
		   <openid>oUpF8uMuAJO_M2pxb1Q9zNjWeS6o</openid>
		   <out_trade_no>1415659990</out_trade_no>
		   <spbill_create_ip>14.23.150.211</spbill_create_ip>
		   <total_fee>1</total_fee>
		   <trade_type>JSAPI</trade_type>
		   <sign>0CB01533B8C1EF103065174F50BCA001</sign>
		</xml>
	 */
	public static String buildWXOrderRequestXml(WXOrderPayDto orderPay) {
		String xml = null;
		StringBuffer str = new StringBuffer();
		/**
		 * a,b,c,d,e,f,g || h,i,j,k,l,m,n || o,p,q,r,s,t || u,v,w,x,y,z ||
		 * 
		 */
		String appid = ConfigUtils.getValue("weixin.appId");// 微信商户公众账号ID
		String body = orderPay.getBody();// 订单说明
		String device_info = "WEB";//PC网页或公众号内支付可以传"WEB"，必填：否
		String fee_type = orderPay.getFee_type();// 币种，必填：否
		String mch_id = ConfigUtils.getValue("mch_id");// 微信商户号
		String nonce_str = NonceStrUtil.getRandomStringByLength();// 随机码32位// znlb4i2ddh68w4l0ai7s2se29rfft4m
		// String nonce_str="znlb4i2ddh68w4l0ai7s2se29rfft4m";
		String notify_url = ConfigUtils.getValue("notify_url");// 回掉地址
		String openid = orderPay.getOpenid();// trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识
		String out_trade_no = orderPay.getOut_trade_no();// 商户订单号
		String product_id = orderPay.getProduct_id();// 商品号
		String sign = "";// 签名
		String spbill_create_ip = ConfigUtils.getValue("spbill_create_ip");// Native支付填调用微信支付API的机器IP
		String total_fee = String.valueOf(orderPay.getTotal_fee());//
		String trade_type = ConfigUtils.getValue("trade_type");
		String time_start = orderPay.getTime_start();
		String time_expire = orderPay.getTime_expire();

		String MD5str = "appid=" + appid + "&body=" + body + "&device_info="
				+ device_info + "&fee_type=" + fee_type + "&mch_id=" + mch_id
				+ "&nonce_str=" + nonce_str + "&notify_url=" + notify_url
				+ "&openid=" + openid + "&out_trade_no=" + out_trade_no
				+ "&product_id=" + product_id + "&spbill_create_ip="
				+ spbill_create_ip + "&time_expire=" + time_expire
				+ "&time_start=" + time_start + "&total_fee=" + total_fee
				+ "&trade_type=" + trade_type;
		String sign_key = ConfigUtils.getValue("sign_key");
		StringBuffer signStr = new StringBuffer();

		signStr.append(MD5str).append("&key=").append(sign_key);
		System.out.println("signStr:" + signStr.toString());
		String signStr1 = signStr.toString();
		// String
		// sign1="appid=wx1940aea5834562a0&body=易视商城视频产品&device_info=WEB&fee_type=CNY&mch_id=1264430901&nonce_str=64fb2biv343oy9otho2gt21bzzr26n8&notify_url=http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php&out_trade_no=213836&product_id=SDK-001&spbill_create_ip=172.16.10.37&time_expire=20151019195737&time_start=20151019175737&total_fee=1&trade_type=NATIVE&key=huatiwenhuayishi20151009szhuati1";
		sign = bulidMD5Str(signStr1);
		// sign=bulidMD5Str(sign1); AA156EF50C885FF4A6BE14ED7F5A707D
		System.out.println("nonce_str:" + nonce_str);
		System.out.println("sign======:" + sign);

		/**
		 * <xml> <appid>wx1940aea5834562a0</appid> <body>易视商城视频产品</body>
		 * <device_info>WEB</device_info> <mch_id>1264430901</mch_id>
		 * <nonce_str>6livv7kpw01lhxpd640p4xinhach1t</nonce_str>
		 * <notify_url>http
		 * ://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php</notify_url>
		 * <out_trade_no>2015082602</out_trade_no>
		 * <product_id>20150002</product_id>
		 * <spbill_create_ip>172.16.10.37</spbill_create_ip>
		 * <time_expire>20151009165559</time_expire>
		 * <time_start>20151009145559</time_start> <total_fee>1</total_fee>
		 * <trade_type>NATIVE</trade_type>
		 * <sign>958680F6CB441AAC3CAADB4606868E69</sign> </xml>
		 */
		str.append("<xml>");
		str.append("<appid>").append(appid).append("</appid>");
		str.append("<body>").append(body).append("</body>");
		str.append("<device_info>").append(device_info).append("</device_info>");
		str.append("<fee_type>").append(fee_type).append("</fee_type>");
		str.append("<mch_id>").append(mch_id).append("</mch_id>");
		str.append("<nonce_str>").append(nonce_str).append("</nonce_str>");// 加密算法生成
		str.append("<notify_url>").append(notify_url).append("</notify_url>");
		str.append("<openid>").append(openid).append("</openid>");
		str.append("<out_trade_no>").append(out_trade_no).append("</out_trade_no>");
		str.append("<product_id>").append(product_id).append("</product_id>");
		str.append("<spbill_create_ip>").append(spbill_create_ip).append("</spbill_create_ip>");
		str.append("<time_expire>").append(time_expire).append("</time_expire>");
		str.append("<time_start>").append(time_start).append("</time_start>");
		str.append("<total_fee>").append(total_fee).append("</total_fee>");
		str.append("<trade_type>").append(trade_type).append("</trade_type>");
		str.append("<sign>").append(sign).append("</sign>");// 将所有的参数加密MD5
		str.append("</xml>");
		xml = str.toString();
		System.out.println("requestXml:" + xml);
		return xml;
	}
	/**
	 * 构建MD5字符串
	 * @param key
	 * @return
	 */
	private static String bulidMD5Str(String key) {
		String isResult = null;
		isResult = MD5Util.MD5Encode(key, "UTF-8").toUpperCase();
		return isResult;
	}

	/**
	 * 解析返回xml报文
	 * @param resultXml,然后存放到Map中
	 * @return
	 */
	public static Map<String, String> buildXmlToMap(String resultXml) {
		Map<String, String> map = null;
		if (null == resultXml || "".equals(resultXml)) {
			return null;
		} else {
			try {
				map = new HashMap<String, String>();
				/***
				 * 通过ByteArrayInputStream流管道去对接byte数组的数据。
				 */
				InputStream in = new ByteArrayInputStream(resultXml.getBytes("UTF-8"));
				SAXBuilder builder = new SAXBuilder();
				Document doc = builder.build(in);
				Element root = doc.getRootElement();
				List list = root.getChildren();
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Element e = (Element) it.next();
					String k = e.getName();
					String v = "";
					List children = e.getChildren();
					if (children.isEmpty()) {
						v = e.getTextNormalize();
					} else {
						v = getChildrenText(children);
					}
					map.put(k, v);
				}
				// 关闭流
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return map;
		}
	}

	/**
	 * 获取子结点的xml
	 * k=appid,v=wx2421b1c4370ec43b
	 * <appid>
	 * 	wx2421b1c4370ec43b
	 * </appid>
	 * -------------------------------------------
	 * k=appid,v=<child>ddddd</child>
	 * <appid>
	 * 	<child>ddddd</child>
	 * </appid>
	 * ---------------------------------------------------------
	 * <appid>
	 * 	<child>
	 * 		<child2>dd2</child2>
	 * 	</child>
	 * </appid>
	 * 
	 * 
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if (!children.isEmpty()) {
			Iterator it = children.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if (!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		String resultXml="<xml><appid><child><child2>dd2</child2></child></appid></xml>";
		Map<String,String> map = buildXmlToMap(resultXml);
		for(String key:map.keySet()) {
			System.out.println("key="+key+"---value="+map.get(key));
		}
	}
	public static void main2(String[] args) {
		WXOrderPayDto orderPay = new WXOrderPayDto();
		orderPay.setBody("全泰支付");
		orderPay.setFee_type("CNY");
		orderPay.setOut_trade_no("2015112201");
		orderPay.setProduct_id("201500020002");
		orderPay.setOpenid("o8dchuAzO_r9Le-lxv1_bjoy3eJA");
		orderPay.setTime_expire("20151122182500");
		orderPay.setTime_start("20151122132500");
		orderPay.setTotal_fee(1);
		String requestXml = buildWXOrderRequestXml(orderPay);

		// String
		/**
		 * requestXml="<xml><appid>wxfe3ad8a96fc0bc99</appid>
		 * <body>全泰支付</body>
		 * <device_info>WEB</device_info>
		 * <fee_type>CNY</fee_type>
		 * <mch_id>1271026401</mch_id>
		 * <nonce_str>x1v3x0w61wlltoc59obh8v1ciyryii1</nonce_str>
		 * <notify_url>http://sukerwei.imwork.net/digest/notify.jsp</notify_url>
		 * <openid>o8dchuAzO_r9Le-lxv1_bjoy3eJA</openid>
		 * <out_trade_no>2015112201</out_trade_no>
		 * <product_id>201500020002</product_id>
		 * <spbill_create_ip>192.168.60.109</spbill_create_ip>
		 * <time_expire>20151122182500</time_expire>
		 * <time_start>20151122102500</time_start>
		 * <total_fee>1</total_fee>
		 * <trade_type>JSAPI</trade_type>
		 * <sign>E30B0522ADA7F050D22A56E8039412D1</sign></xml>";
		 */

		// 发送请求
		HttpConntionUtil.buildConn();
		HttpConntionUtil.sendWXRequestXml(requestXml);
		HttpConntionUtil.getWXResponseXml();
	}
}