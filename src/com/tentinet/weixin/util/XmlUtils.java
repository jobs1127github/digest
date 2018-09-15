package com.tentinet.weixin.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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
import com.tentinet.weixin.entity.RedPacket;

/**
 * 构建xml报文，解析返回报文
 *
 */
public class XmlUtils {
	/**
	 * 请求示例
	 * 构建发红包的报文
	 * 数据示例：参考微信支付 商户平台开发文档
		<xml>
		<sign><![CDATA[E1EE61A91C8E90F299DE6AE075D60A2D]]></sign>
		<mch_billno><![CDATA[0010010404201411170000046545]]></mch_billno>
		<mch_id><![CDATA[888]]></mch_id>
		<wxappid><![CDATA[wxcbda96de0b165486]]></wxappid>
		<send_name><![CDATA[send_name]]></send_name>
		<re_openid><![CDATA[onqOjjmM1tad-3ROpncN-yUfa6uI]]></re_openid>
		<total_amount><![CDATA[200]]></total_amount>
		<total_num><![CDATA[1]]></total_num>
		<wishing><![CDATA[恭喜发财]]></wishing>
		<client_ip><![CDATA[127.0.0.1]]></client_ip>
		<act_name><![CDATA[新年红包]]></act_name>
		<remark><![CDATA[新年红包]]></remark>
		<nonce_str><![CDATA[50780e0cca98c8c8e814883e5caa672e]]></nonce_str>
		</xml>
	 * @return
	 */
	public static String buildWXOrderRequestXml(RedPacket redPacket) {
		String xml = null;
		StringBuffer str = new StringBuffer();
		/**
		 * a,b,c,d,e,f,g || h,i,j,k,l,m,n || o,p,q,r,s,t || u,v,w,x,y,z ||
		 * 
		 */
		/* 非空参数值的参数按照参数名ASCII码从小到大排序（字典序）
		 * 	String act_name = "1";// 活动名称
			String client_ip = "172.16.10.37";// Ip地址 调用接口的机器Ip地址
			String mch_billno = "1271026401201511190000000001";// 商户订单号   商户订单号（每个订单号必须唯一）组成：mch_id+yyyymmdd+10位一天内不能重复的数字。
			String mch_id = "1271026401";// 商户号
			String nonce_str = NonceStrUtil.getRandomStringByLength();// 随机字符串
			String remark = "1";// 备注
			String re_openid = "o8dchuAzO_r9Le-lxv1_bjoy3eJA";// 用户openid
			String sign = "";// 签名
			String send_name = "1";// 商户名称
			Integer total_amount = 1000;// 付款金额
			Integer total_num = 1;// 红包发放总人数
			String wxappid = ConfigUtils.getValue("weixin.appId");// 公众账号appid
			String wishing = "1";// 红包祝福语
		 */
		//活动名称
		String act_name=ConfigUtils.getValue("act_name");
		//IP
		String client_ip=ConfigUtils.getValue("spbill_create_ip");
		//商户订单号
		String mch_billno = redPacket.getMch_billno();
		//商户号
		String mch_id =ConfigUtils.getValue("mch_id");
		//随机字符串
	    String nonce_str =NonceStrUtil.getRandomStringByLength();	
	    //备注
	    String remark =ConfigUtils.getValue("remark");
	    //回复的openid
		String re_openid =redPacket.getRe_openid();
		
		/**
		 * 签名算法 参考微信开发文档
			第一步，设所有发送或者接收到的数据为集合M，将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序），使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA。
		 	第二步，在stringA最后拼接上key=商户支付密钥得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，再将得到的字符串所有字符转换为大写，得到sign值signValue。
		 */
		String sign ="";
		String send_name =ConfigUtils.getValue("send_name");
		Integer total_amount =Integer.parseInt(ConfigUtils.getValue("total_amount"));
		Integer total_num=Integer.parseInt(ConfigUtils.getValue("total_num"));
		String wxappid = ConfigUtils.getValue("weixin.appId");
		String wishing=ConfigUtils.getValue("wishing");
		//使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA。	
		String MD5str = "act_name=" + act_name + "&client_ip=" + client_ip
				+ "&mch_billno=" + mch_billno + "&mch_id=" + mch_id
				+ "&nonce_str=" + nonce_str  + "&re_openid=" + re_openid 
				+ "&remark="+ remark+ "&send_name=" + send_name + "&total_amount=" + total_amount
				+ "&total_num=" + total_num + "&wishing=" + wishing+ "&wxappid=" + wxappid;

		String sign_key = ConfigUtils.getValue("sign_key");
		StringBuffer signStr = new StringBuffer();
		signStr.append(MD5str).append("&key=").append(sign_key);
		//signStr:stringSignTemp字符串
		System.out.println("signStr:" + signStr.toString());
		String signStr1 = signStr.toString();
		//得到signValue
		sign = bulidMD5Str(signStr1);
		System.out.println("nonce_str:" + nonce_str);
		System.out.println("sign======:" + sign);
		//参考微信支付 微信商户平台开发文档 
		str.append("<xml>");
		str.append("<act_name>").append("<![CDATA[").append(act_name).append("]]>").append("</act_name>");
		str.append("<client_ip>").append("<![CDATA[").append(client_ip).append("]]>").append("</client_ip>");
		str.append("<mch_billno>").append("<![CDATA[").append(mch_billno).append("]]>").append("</mch_billno>");
		str.append("<mch_id>").append("<![CDATA[").append(mch_id).append("]]>").append("</mch_id>");
		str.append("<nonce_str>").append("<![CDATA[").append(nonce_str).append("]]>").append("</nonce_str>");// 将所有的参数加密MD5
		str.append("<re_openid>").append("<![CDATA[").append(re_openid).append("]]>").append("</re_openid>");
		str.append("<remark>").append("<![CDATA[").append(remark).append("]]>").append("</remark>");
		str.append("<send_name>").append("<![CDATA[").append(send_name).append("]]>").append("</send_name>");
		str.append("<total_amount>").append("<![CDATA[").append(total_amount).append("]]>").append("</total_amount>");
		str.append("<total_num>").append("<![CDATA[").append(total_num).append("]]>").append("</total_num>");
		str.append("<wishing>").append("<![CDATA[").append(wishing).append("]]>").append("</wishing>");
		str.append("<wxappid>").append("<![CDATA[").append(wxappid).append("]]>").append("</wxappid>");// 加密算法生成
		str.append("<sign>").append("<![CDATA[").append(sign).append("]]>").append("</sign>");
		str.append("</xml>");
		xml = str.toString();
		System.out.println("requestXml:" + xml);
		return xml;
	}

	/**
	 * 解析返回报文,从Map中得到对应的内容
	 * 
	 * @param resultXml
	 * @return
	 */
	public static Map<String, String> buildXmlToMap(String resultXml) {
		Map<String, String> map = null;
		if (null == resultXml || "".equals(resultXml)) {
			return null;
		} else {
			try {
				map = new HashMap<String, String>();
				InputStream in = new ByteArrayInputStream(
						resultXml.getBytes("UTF-8"));
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

	private static String bulidMD5Str(String key) {
		String isResult = null;
		isResult = MD5Util.MD5Encode(key, "UTF-8").toUpperCase();
		return isResult;
	}

	public static void main(String[] args) {

		// String
		// requestXml="<xml><act_name>关注送红包</act_name><client_ip>127.0.0.1</client_ip><mch_billno>1271026401201511190000000001</mch_billno><mch_id>1271026401</mch_id><nonce_str>okfkndeiuvs8324080g8i5mr582q4nb</nonce_str><re_openid>o8dchuAzO_r9Le-lxv1_bjoy3eJA</re_openid><remark>后期分享，打赏可以得到金币哦!</remark><send_name>全泰消化专家</send_name><total_amount>1000</total_amount><total_num>1</total_num><wishing>谢谢关注</wishing><wxappid>wxfe3ad8a96fc0bc99</wxappid><sign>1B6B338A1DA99E372A9822E4988DC383</sign></xml>";
		// 发送请求 HttpConntionUtil.buildConn();
		//String requestXml = buildWXOrderRequestXml();
		//HttpConntionUtil.sendHBRequest(requestXml);
		// HttpConntionUtil.getWXResponseXml();
		//String act_name=ConfigUtils.getValue("act_name");
		/*try {
			act_name = new String((act_name).getBytes("ISO8859_1"), "UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/

		//System.out.println(act_name	);
		
		//String requestXml="<xml><act_name><![CDATA[全知消化专家首次关注送红包]]></act_name><client_ip><![CDATA[113.106.90.165]]></client_ip><mch_billno><![CDATA[1271026401201512226669120408]]></mch_billno><mch_id><![CDATA[1271026401]]></mch_id><nonce_str><![CDATA[90cgy9ggz807uly8h3w73ikirvum716]]></nonce_str><re_openid><![CDATA[o8dchuAzO_r9Le-lxv1_bjoy3eJA]]></re_openid><remark><![CDATA[微信扫一扫，关注即可领红包和金币。关注“全知消化专家”，乐享健康资讯，微信红包大礼等你拿！您还有一个微信红包和金币未领取！]]></remark><send_name><![CDATA[深圳市全泰兴投资发展有限公司]]></send_name><total_amount><![CDATA[100]]></total_amount><total_num><![CDATA[1]]></total_num><wishing><![CDATA[全知消化专家,祝您身体健康,生活愉快,工作顺利!]]></wishing><wxappid><![CDATA[wxfe3ad8a96fc0bc99]]></wxappid><sign><![CDATA[7592A94BC01178FF21F3C14B8194F80E]]></sign></xml>";
		
//		RedPacket entity=new RedPacket();
//		entity.setMch_billno("1271026401201512226669120408");
//		entity.setRe_openid("o8dchuAzO_r9Le-lxv1_bjoy3eJA");
//		String requestXml = buildWXOrderRequestXml(entity);
//		HttpConntionUtil.sendHBRequest(requestXml);
		System.out.println("huatiwenhuayishi20151009szhuati2".length());

	}

}
