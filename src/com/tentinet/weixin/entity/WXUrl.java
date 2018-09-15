package com.tentinet.weixin.entity;
/**
 * 公众平台调用微信接口，调用 url
 * 
 * @author jobs1127
 */
public class WXUrl {
	// 得到token接口，获取access_token需要appid,secret
	public static String ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";
	/**
	 *  得到用户信息，获取用户信息，需要access_token,openid
	 */
	public static String WECHATUSER_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={0}&openid={1}";
	
	
	// 发送模板信息，需要access_token
	public static String TEMPLATE_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={0}";
	
	
	// 发送红包 这个要参考  微信支付 商户平台开发文档
	public static String HB_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
	
	// 页面认证跳转 得到code
	public static String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={0}&redirect_uri=";
	// 网页oauth2认证 通过code换取网页授权access_token
	public static String OAUTH2_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&grant_type=authorization_code&code=";
	// 通过access_token，openID得到页面的用户的信息
	public static String PAGE_WECHATUSER_URL = "https://api.weixin.qq.com/sns/userinfo?access_token={0}&openid={1}&lang=zh_CN";
	
	
	//微信支付 统一下订单的URL
	public static String UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	// 采用http GET方式请求获得jsapi_ticket
	public static String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={0}&type=jsapi";
	
	
	
	// 页面订证的方式得到Token
	public static String ACCESS_TOKEN_URL_BASE = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&grant_type=authorization_code&code=";
	
	//创建菜单的接口
	public static String Create_Menu_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token={0}";
}
