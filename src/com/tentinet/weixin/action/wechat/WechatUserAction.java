package com.tentinet.weixin.action.wechat;

import java.text.MessageFormat;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.tentinet.weixin.entity.WXUrl;
import com.tentinet.weixin.entity.WechatUser;
import com.tentinet.weixin.service.WechatJssdkService;
import com.tentinet.weixin.util.ConfigUtils;
import com.tentinet.weixin.util.UrlUtil;

@Controller
@RequestMapping("/wechat")
public class WechatUserAction {
	private static final Logger log = LoggerFactory.getLogger(WechatUserAction.class);
	@Autowired
	private WechatJssdkService wechatJssdkService;

	/***
	 * 授权成功后，得到code，根据code获取用户信息，用户信息获取成功后，显示目标页面给用户
	 */
	@RequestMapping(value = "/auth.do")
	public String auth(HttpServletRequest request, String code, String type)throws Exception {
		//log.info("jobs1127:"+"code===========" + code + "==============type==========="+ type);
		//用户同意授权后，会得到code，把openid,access_token,refresh_token存放到map,根据code获得code作为换取access_token的票据
		Map<String, Object> map = wechatJssdkService.getPageAccessToken(code);
		if(map != null) {
			if(map.get("access_token")!=null && map.get("openid")!=null) {
				// 拉取用户信息，保存到数据库/更新数据库
				//WechatUser pageWechatUser 
				//= wechatJssdkService.getPageWechatUser(map.get("access_token").toString(), map.get("openid").toString());
				
				//自己服务器端，把openID保存到session里
				request.getSession().setAttribute("openId", map.get("openid").toString());
			}
		}
		//log.info("jobs1127:"+"=======wechatId:===========" + pageWechatUser.getOpenId());
		
		//String create_menu_URL=MessageFormat.format(WXUrl.Create_Menu_URL,map.get("access_token").toString());
		//String result = HttpClientUtil.sendPostSSLRequest(create_menu_URL,null, CharEncoding.UTF_8);
		
		//log.info("jobs1127_Create_Menu_URL:"+create_menu_URL);
		//log.info("jobs1127_create_menu:" + result);
		//根据type，重定向到显示页面
		if (type.equals("5")) {
			return "redirect:/wechat/qiandaoChouJiang.do";//签到抽奖授权链接
		} else if (type.equals("2")) {
			return "redirect:/wechat/planQuery.do";//流向查询
		} else if(type.equals("1")){
			return "redirect:/wechat/contact_center_UI.do";//营销中心通讯录
		} else if(type.equals("11")){
			return "redirect:/wechat/contact_factory_UI.do";//新疆厂部通讯录
		} else {
			return null;
		}
	}
	/***
	 * 微信公众平台，如果请求的URL是非微信平台的，而是第三方的URL页面，则相当于请求的是自己的服务器，如何获取
	 * 用户相关的信息了，微信平台通过网页授权的机制，获取用户相关的信息，包括openId.
	 * 如果用户在微信客户端中访问第三方网页，公众号可以通过微信网页授权机制，来获取用户基本信息，进而实现业务逻辑。	
	 * 1、引导用户进入授权页面同意授权，获取code 
	 * 2、通过code换取网页授权access_token（与基础支持中的access_token不同）
	 * 3、如果需要，开发者可以刷新网页授权access_token，避免过期
	 * 4、通过网页授权access_token和openid获取用户基本信息（支持UnionID机制）
	 */
	@RequestMapping(value = "/youhui.do", method = RequestMethod.GET)
	public ModelAndView youhui(HttpServletRequest request,HttpServletResponse response,String type) throws Exception {
		/**重定向的uri 授权后重定向的回调链接地址，请使用urlEncode对链接进行处理*/
		String wechatRedirece = UrlUtil.encode(ConfigUtils.getValue("weixin.backHost")+"wechat/auth.do?format=json&type="+type);
		
		// http://www.51pharm.cn/digest/wechat/auth.do?format=json&type=1
		log.info("url:"+ UrlUtil.decode(wechatRedirece));
		
		String AUTHORIZE_URL = MessageFormat.format(WXUrl.AUTHORIZE_URL,ConfigUtils.getValue("weixin.appId"));
		
		// https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxfe3ad8a96fc0bc99&redirect_uri=
		log.info("AUTHORIZE_URL:" + AUTHORIZE_URL);

		// 引导关注者打开如下页面，用户同意授权，获取code
		String url = AUTHORIZE_URL
				+ wechatRedirece
				+ "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
		
		log.info("url===========" + UrlUtil.decode(url));
		/***
		 * scope:应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），
		 * snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息） 
		 */
		/* url */
		// https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxfe3ad8a96fc0bc99&redirect_uri=http://www.51pharm.cn/digest/wechat/auth.do?format=json&type=2&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect

		// redirect_uri：授权后重定向的回调链接地址，请使用urlencode对链接进行处理  REDIRECT_URI == wechatRedirece
		
		/** 网页授权 如果用户同意授权，页面将跳转至 redirect_uri/?code=CODE&state=STATE。 */
		// https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
		return new ModelAndView(new RedirectView(url));
		/***
		 * 1、Servlet重定向forward与redirect：
		 * 使用servlet跳转有两种方式，一种是forward，另一种就是redirect。
		 * forward是服务器内部重定向，客户端并不知道服务器把你当前请求重定向到哪里去了，
		 * 地址栏的url与你之前访问的url保持不变。
		 * redirect则是客户端重定向，是服务器将你当前请求返回，然后给个状态标示给你，
		 * 告诉你应该去重新请求另外一个url，具体表现就是地址栏的url变成了新的url。
		 * 
		 * 2、ModelAndView重定向：
		 * 使用Spring MVC通常是使用ModelAndView用来返回视图。
		 * ModelAndView其实也是支持Servlet中的两种跳转方式。
		 * 比如404页面我们一般采用redirect重定向方式，像下面的代码就是redirect重定向：
		 * public ModelAndView getPage404MVC() {        
			   ModelAndView mv = new ModelAndView("redirect:/404.html");
			   return mv;
		   }
		 * 要使用forward跳转就只需把redirect换成forward即可，
		 * 特别的ModelAndView默认使用forward方式跳转页面。
		 */
	}
}
