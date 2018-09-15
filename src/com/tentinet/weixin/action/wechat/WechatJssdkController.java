package com.tentinet.weixin.action.wechat;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tentinet.weixin.service.WechatJssdkService;
import com.tentinet.weixin.util.JsonResult;

@Controller
@RequestMapping("/wechat/jssdk")
public class WechatJssdkController extends WechatBaseAction{
	@Autowired
	private WechatJssdkService wechatJssdkService;
	/**
	 * 生成signature签名之前必须先了解一下jsapi_ticket，
	 * jsapi_ticket是公众号用于调用微信JS接口的临时票据。
	 * 正常情况下，jsapi_ticket的有效期为7200秒，通过access_token来获取。
	 * 由于获取jsapi_ticket的api调用次数非常有限，
	 * 频繁刷新jsapi_ticket会导致api调用受限，影响自身业务，
	 * 开发者必须在自己的服务全局缓存jsapi_ticket 。
	 * @param url
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping("/sign.do")
	public void sign(String url, HttpServletResponse response,HttpServletRequest request) throws IOException {
		/**
		 * HTTP referer是header的一部分，当浏览器向web服务器发送请求的时候，
		 * 一般会带上referer，告诉服务器我是从哪个页面链接过来的，服务器借此可以获得一些信息用于处理。
		 * 比如从我主页上链接到一个朋友那里，
		 * 他的服务器就能够从HTTP referer中统计出每天有多少用户点击我主页上的链接访问他的网站。
		 * 获取REFERER的方式是：request.getHeader("REFERER");
		 */
		String referer = request.getHeader("referer");
		System.out.println("sign.do:"+referer);
		
		//获取jsapiTicket 为签名做准备
		String jsapiTicket = wechatJssdkService.getTicket();
		System.out.println("jsapiTicket="+jsapiTicket);
		/**
		 * JS-SDK使用权限签名算法.
		 * 1、生成签名之前必须先了解一下jsapi_ticket，
		 * jsapi_ticket是公众号用于调用微信JS接口的临时票据。
		 * 正常情况下，jsapi_ticket的有效期为7200秒，通过access_token来获取。
		 * 由于获取jsapi_ticket的api调用次数非常有限，频繁刷新jsapi_ticket会导致api调用受限，
		 * 影响自身业务，开发者必须在自己的服务全局缓存jsapi_ticket 。
		 * 2、1.参考以下文档获取access_token（有效期7200秒，开发者必须在自己的服务全局缓存access_token）。
		 * 3、2.用第一步拿到的access_token 
		 * 采用http GET方式请求获得jsapi_ticket（有效期7200秒，
		 * 开发者必须在自己的服务全局缓存jsapi_ticket）：https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
		 * 
		 * url（当前网页的URL，不包含#及其后面部分） :referer。
		 * 
		 * 签名生成规则如下：
		 * 参与签名的字段包括noncestr（随机字符串）, 
		 * 有效的jsapi_ticket, timestamp（时间戳）, 
		 * url（当前网页的URL，不包含#及其后面部分） 。
		 * 对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）后，
		 * 使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串string1。
		 * 这里需要注意的是所有参数名均为小写字符。
		 * 对string1作sha1加密，字段名和字段值都采用原始值，不进行URL 转义。
		 */
		Map<String, String> ret = wechatJssdkService.sign(jsapiTicket, referer);
		
		writeResponseByJson(request, response, JsonResult.success(ret));
	}
	
}
