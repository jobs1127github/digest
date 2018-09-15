package com.tentinet.weixin.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tentinet.app.bean.DigestGoldVo;
import com.tentinet.app.bean.DigestRedPacketVo;
import com.tentinet.app.bean.WXUserVo;
import com.tentinet.weixin.entity.RedPacket;
import com.tentinet.weixin.entity.TemplateData;
import com.tentinet.weixin.entity.TemplateMsgContent;
import com.tentinet.weixin.entity.WXUrl;
import com.tentinet.weixin.entity.WechatUser;
import com.tentinet.weixin.service.WXCMSClientService;
import com.tentinet.weixin.service.WechatJssdkService;
import com.tentinet.weixin.util.ConfigUtils;
import com.tentinet.weixin.util.DateUtil;
import com.tentinet.weixin.util.HttpClientUtil;
import com.tentinet.weixin.util.HttpConntionUtil;
import com.tentinet.weixin.util.HttpRequestUtil;
import com.tentinet.weixin.util.TokenUtil;
import com.tentinet.weixin.util.WxPayUtil;
import com.tentinet.weixin.util.XmlUtils;
import com.tentinet.weixin.util.wechat.InMessage;
import com.tentinet.weixin.util.wechat.Tools;

@Service(value = "wechatJssdkService")
public class WechatJssdkServiceImpl implements WechatJssdkService {

	@Autowired
	private WXCMSClientService wXCMSClientService;

	private static Logger log = LoggerFactory.getLogger(WechatJssdkServiceImpl.class);

	/** 
	 * 得到Token
	 */
	@Override
	public String getToken() {
		return TokenUtil.getToken();
	}

	/**
	 * 得到用户的信息
	 */
	@Override
	public WechatUser getWechatUserInfo(String openid) {
		/***
		 * 获取token，必须要把当前自己的服务器的ip地址添加到微信基础配置ip白名单里。
		 */
		String token = getToken();
		System.out.println("得到用户的信息token="+token);
		String url = MessageFormat.format(WXUrl.WECHATUSER_URL, token, openid);
		String result = HttpClientUtil.sendPostSSLRequest(url, null,CharEncoding.UTF_8);
		//log.info("WechatUser:" + result);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = null;
		try {
			jsonNode = mapper.readTree(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		WechatUser wachatUser = null;
		System.out.println("jsonNode="+jsonNode);
		if (jsonNode.get("errcode") == null) {// 说明从微信得到用户信息成功
			wachatUser = new WechatUser();
			wachatUser.setOpenId(jsonNode.path("openid").getTextValue());
			//微信昵称=用户名,先把一些特殊字符过滤掉,真正保存到数据库时需要BASE64Utils.getBASE64()编码后存入
			wachatUser.setNickname(Tools.StringFilter(jsonNode.path("nickname").getTextValue()));
			wachatUser.setSex(String.valueOf(jsonNode.path("sex").getIntValue()));
			wachatUser.setProvince(jsonNode.path("province").getTextValue());
			wachatUser.setCity(jsonNode.path("city").getTextValue());
			wachatUser.setCountry(jsonNode.path("country").getTextValue());
			wachatUser.setHeadimgurl(jsonNode.path("headimgurl").getTextValue());
			
			// 如果数据库中存在与否做对应的操作
			WXUserVo DBwechatUser = wXCMSClientService.getWechatUserInfo(openid);
			System.out.println("DBwechatUser="+DBwechatUser);
			if (DBwechatUser == null) {
				wXCMSClientService.saveWechatUser(wachatUser);
			} else {// 对微信用户的信息进行更新显示 // 说明当前微用户信息,之前在DB中已经存在,说明其关注过我们的系统
				if (StringUtils.equals("2", DBwechatUser.getRole())) { // 专家用户----启用原来的信息
					WXUserVo wxuser = new WXUserVo();
					wxuser.setOpenid(wachatUser.getOpenId());
					wxuser.setStatus("Y");
					wXCMSClientService.updateWechatUser(wxuser);
				} else {// 普通用户
					//System.out.println("-----------普通用户关注---------------");
					wXCMSClientService.updateWechatUser(wachatUser);
					System.out.println("getWechatUserInfo已关注的用户，再次扫二维码过来啦……");
				}
			}
		} else {
			//log.error("TTY Say: WechatJssdkServiceImpl.getWechatUserInfo has error.");
		}
		return wachatUser;
	}

	/**
	 * 得到网页验证的消息，根据code获得access_token
	 */
	@Override
	public Map<String, Object> getPageAccessToken(String code) {
		/**
		 * 获取code后，请求以下链接获取access_token： 
		 * https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
		 */
		String OAUTH2_URL = MessageFormat.format(WXUrl.OAUTH2_URL,
				ConfigUtils.getValue("weixin.appId"),
				ConfigUtils.getValue("weixin.secret"));
		
		String result = HttpClientUtil.sendPostSSLRequest(OAUTH2_URL+code,null,CharEncoding.UTF_8);
		//result == responseContent，result是一个json数据结构
		//log.info("PageAccessToken:" + result);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode;//json结点对象
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			/** 通过ObjectMapper readTree来获得json结点对象 */
			jsonNode = mapper.readTree(result);
			String openId = jsonNode.path("openid").getTextValue();
			String accessToken = jsonNode.path("access_token").getTextValue();
			String refreshToken = jsonNode.path("refresh_token").getTextValue();
			
			log.info("openid:" + openId + ",accessToken:" + accessToken+ ",refreshToken:" + refreshToken);
			
			map.put("openid", openId);
			map.put("access_token", accessToken);
			map.put("refresh_token", refreshToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 得到页面用户的信息
	 */
	@Override
	public WechatUser getPageWechatUser(String access_token, String openid) {
		/**
		 * 如果网页授权作用域为snsapi_userinfo，则此时开发者可以通过access_token和openid拉取用户信息了。
		 * 请求下面链接拉取用户信息
		 * https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
		 */
		String PAGE_WECHATUSER_URL=MessageFormat.format(WXUrl.PAGE_WECHATUSER_URL,access_token,openid);
		
		String result = HttpClientUtil.sendPostSSLRequest(PAGE_WECHATUSER_URL,null, CharEncoding.UTF_8);
		
		// log.info("WechatUser:" + result);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = null;
		try {
			jsonNode = mapper.readTree(result);
		} catch (IOException e) {
			log.info("readTree has exception");
		}
		WechatUser wachatUser = new WechatUser();
		if (jsonNode.get("errcode") == null) {// 说明从微信得到用户信息成功
			wachatUser.setOpenId(jsonNode.path("openid").getTextValue());
			wachatUser.setNickname(jsonNode.path("nickname").getTextValue());
			wachatUser.setSex(jsonNode.path("sex").getTextValue() + "");
			wachatUser.setProvince(jsonNode.path("province").getTextValue());
			wachatUser.setCity(jsonNode.path("city").getTextValue());
			wachatUser.setCountry(jsonNode.path("country").getTextValue());
			wachatUser.setHeadimgurl(jsonNode.path("headimgurl").getTextValue());
			// 根据数据库中存在与否做对应的操作
			WXUserVo DBwechatUser = wXCMSClientService.getWechatUserInfo(openid);
			if (DBwechatUser == null) {//如果在数据库中不存在，则保存到数据库，说明该用户是一个新关注的用户
				wXCMSClientService.saveWechatUser(wachatUser);
			} else {// 对微信用户的信息进行更新显示，说明当前微用户信息,以间在DB中已经存在,说明其关注过我们的系统
				wXCMSClientService.updateWechatUser(wachatUser);
			}
		} else {
			log.error("WechatJssdkServiceImpl.getWechatUserInfo getPageWechatUser has error.");
		}
		return wachatUser;
	}

	/**
	 * 发送模板消息
	 */
	@Override
	public boolean sendTemplateMsg(TemplateMsgContent msg) {
		//获得token
		String token = getToken();
		//发送模板消息的URL
		String url = MessageFormat.format(WXUrl.TEMPLATE_MSG_URL, token);
		//把msg普通对象，转换成json对象
		String jsonString = JSONObject.fromObject(msg).toString();
		System.out.println("jsonString="+jsonString);
		//发起http请求，传递URL，请求type，json数据
		JSONObject jsonObject = HttpRequestUtil.httpRequest(url, "POST",jsonString);
		System.out.println("jsonObject="+jsonObject);
		int result = 0;
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				System.out.println("错误 errcode:{} errmsg:{}"+
						jsonObject.getInt("errcode")+
						jsonObject.getString("errmsg"));
			}
		}
		System.out.println("模板消息发送结果：" + result);
		return false;
	}
	public static void main(String[] args) {
		System.out.println(Long.toString(System.currentTimeMillis() / 1000));
	}
	public static void main3(String[] args) {
		//构建一个InMessage
		InMessage msg = new InMessage();
		msg.setFromUserName("oP_-8wqVgDv-1mcMiamUZf2PolCg");
		
		//构建模板消息
		TemplateMsgContent tMsg = new TemplateMsgContent();
		tMsg.setTemplate_id(ConfigUtils.getValue("template_id"));
		tMsg.setTopcolor(ConfigUtils.getValue("topcolor"));
		tMsg.setTouser(msg.getFromUserName());
		String hb_template_url = ConfigUtils.getValue("hb_template_url");
		/***
		 * 字符串占位符的使用：
		 * hb_template_url=http://www.51pharm.cn/digest/page/label.jsp?re_openid=%s
		 * %s为字符串占位符，表示那个位置将来会传递一个参数过去。
		 * 可以通过String.format()方法把%s初始化，并结合成新的字符串。
		 */
		String new_link_url = String.format(hb_template_url,msg.getFromUserName());
		tMsg.setUrl(new_link_url);
		//模板消息里的内容
		Map<String, TemplateData> data = new HashMap<String, TemplateData>();
		TemplateData first = new TemplateData();
		first.setColor("#333366");
		first.setValue("全知消化专家--现金红包");
		data.put("first", first);

		TemplateData keyword1 = new TemplateData();
		keyword1.setColor("#333366");
		keyword1.setValue("现金红包");
		data.put("keyword1", keyword1);

		TemplateData keyword2 = new TemplateData();
		keyword2.setColor("#333366");
		keyword2.setValue("活动-打赏消化");
		data.put("keyword2", keyword2);

		TemplateData keyword3 = new TemplateData();
		keyword3.setColor("#333366");
		keyword3.setValue("永久");
		data.put("keyword3", keyword3);

		TemplateData remark = new TemplateData();
		remark.setColor("#333366");
		remark.setValue("微信扫一扫，关注即可领红包和金币。关注“全泰消化专家”，乐享健康资讯，微信红包大礼等你拿！金币在我＠全泰中可以查询."
				+ "您还有一个微信红包未领取！");
		data.put("remark", remark);
		tMsg.setData(data);
		
		//获得token
		String token = TokenUtil.getToken();
		//发送模板消息的URL
		String url = MessageFormat.format(WXUrl.TEMPLATE_MSG_URL, token);
		//把msg普通对象，转换成json对象
		String jsonString = JSONObject.fromObject(msg).toString();
		System.out.println("jsonString="+jsonString);
		//发起http请求，传递URL，请求type，json数据
		JSONObject jsonObject = HttpRequestUtil.httpRequest(url, "POST",jsonString);
		System.out.println("jsonObject="+jsonObject);
		int result = 0;
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				
				System.out.println("错误 errcode:{} errmsg:{}"+
						jsonObject.getInt("errcode")+
						jsonObject.getString("errmsg"));
			}
		}
		System.out.println("模板消息发送结果：" + result);
	}
	
	/**
	 * 发送红包,发送金币
	 */
	@Override
	public boolean sendRedPacket(RedPacket redPacket) {
		boolean isResult = false;
		System.out.println("------------redPacket.getRe_openid()==="+ redPacket.getRe_openid());
		/**
		 * 发送金币
		 */
		boolean isgold = wXCMSClientService.isAttentionGold(redPacket.getRe_openid());// 对当前用户系数统是否已发送过关金币
		if (isgold) {// 说明系统已发过金币和红包不作任何操作
		} else {// 要对该用户发金币
			DigestGoldVo entity = new DigestGoldVo();
			UUID uuid = UUID.randomUUID();
			entity.setTid(uuid.toString());
			entity.setGold(Float.valueOf(ConfigUtils.getValue("gold_num")));
			entity.setOpenid(redPacket.getRe_openid());
			entity.setSend_time(DateUtil.dateToStr(new Date()));
			entity.setSend_type("1");
			// entity.setTid(UUIDUtils.getUUID());
			entity.setCreated_by("admin");
			entity.setCreated_time(DateUtil.dateToStr(new Date()));
			entity.setUpdated_by("admin");
			entity.setUpdated_time(DateUtil.dateToStr(new Date()));
			isResult = wXCMSClientService.saveAttentionGold(entity);
			
			isResult = wXCMSClientService.saveUsergoldCount(
					redPacket.getRe_openid(),
					Float.valueOf(ConfigUtils.getValue("gold_num")));
		}
		/***
		 * 发送红包
		 */
		boolean isHB = wXCMSClientService.isAttentionRedPacket(redPacket.getRe_openid());// 对当前用户系统 是否已经发送过红包
		if (isHB) {// 说明之前系统已经发送过红包
		} else {// 说明系数统之前没有发送过红包,此次要发送红包
			String requestXml = XmlUtils.buildWXOrderRequestXml(redPacket);// 得到发送红包的报文
			String resultXml = HttpConntionUtil.sendHBRequest(requestXml);// 发送红包,调用了微信发红包的接口，享用了微信的原生代码
			Map<String, String> WXReturnMap = WxPayUtil.buildXmlToMap(resultXml);
			// 发送成功
			if ("SUCCESS".equals(WXReturnMap.get("return_code"))
					&& "SUCCESS".equals(WXReturnMap.get("result_code"))) {
				// 还对应的增加用户首次关注的金币
				DigestRedPacketVo entity = new DigestRedPacketVo();
				entity.setMoney_count(Float.valueOf(Integer.parseInt(ConfigUtils.getValue("total_amount")) / 100));// 这里以元为单位
				entity.setOpenid(redPacket.getRe_openid());
				entity.setSend_time(DateUtil.dateToStr(new Date()));
				entity.setCreated_by("admin");
				entity.setCreated_time(DateUtil.dateToStr(new Date()));
				entity.setUpdated_by("admin");
				entity.setUpdated_time(DateUtil.dateToStr(new Date()));
				isResult = wXCMSClientService.saveAttentionRedPacket(entity);
			} else {
				isResult = false;
			}
		}
		return isResult;
	}
	/**
	 * 签名生成规则如下：参与签名的字段包括noncestr（随机字符串）, 
	 * 有效的jsapi_ticket, timestamp（时间戳）, url（当前网页的URL，不包含#及其后面部分） 。
	 * 对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）后，
	 * 使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串string1。
	 * 这里需要注意的是所有参数名均为小写字符。对string1作sha1加密，字段名和字段值都采用原始值，不进行URL 转义。
	 */
	public Map<String, String> sign(String jsapi_ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";
		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str
				+ "&timestamp=" + timestamp + "&url=" + url;
		System.out.println(string1);
		//以下的SHA-1加密可以参考 package com.tentinet.weixin.util.wechat; public class SHA1 
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(signature);
		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);
		ret.put("appId", ConfigUtils.getValue("weixin.appId"));
		return ret;
	}

	/**
	 * 将byte字节数组转换成16进制字符串
	 * @param hash
	 * @return
	 */
	private String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
	/***
	 * 这里没有缓存ticket，如果有必要可以模仿getToken方法来缓存ticket
	 */
	public String getTicket() throws IOException {
		String accessToken = getToken();
		//用第一步拿到的access_token 采用http GET方式请求获得jsapi_ticket（有效期7200秒，开发者必须在自己的服务全局缓存jsapi_ticket）：https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
		String TICKET_URL = MessageFormat.format(WXUrl.TICKET_URL, accessToken);
		String result = HttpClientUtil.sendPostSSLRequest(TICKET_URL, null,CharEncoding.UTF_8);
		System.out.println("getTicket()的result="+result);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(result);
		return jsonNode.path("ticket").getTextValue();
	}

	/**
	 * 发送生成二维码的请求
	 */
	public static void main2(String[] args) {
		/*
		 * http请求方式: POST URL:
		 * https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN
		 * POST数据格式：json POST数据例子：{"action_name": "QR_LIMIT_SCENE",
		 * "action_info": {"scene": {"scene_id": 123}}}
		 * 或者也可以使用以下POST数据创建字符串形式的二维码参数： {"action_name": "QR_LIMIT_STR_SCENE",
		 * "action_info": {"scene": {"scene_str": "123"}}}
		 */
//		TokenUtil.getInstance();
//		String token = TokenUtil.getToken();
//		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="
//				+ token;
//		StringBuffer str = new StringBuffer();
//		for (int i = 1; i <= 10; i++) {
//			String requestXml = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": "
//					+ "1" + "}}}";
//			// System.out.println(requestXml);
//			JSONObject jsonObject = HttpRequestUtil.httpRequest(url, "POST",
//					requestXml);
//			System.out.println(i + ":" + jsonObject);
//		}
	}

}
