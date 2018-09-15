package com.tentinet.app.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tentinet.app.bean.UserVo;
import com.tentinet.app.service.UserService;
import com.tentinet.app.util.CookieUtil;
import com.tentinet.app.util.JsonResult;
import com.tentinet.app.util.MyMD5;
import com.tentinet.app.util.ResonseUtils;
import com.tentinet.weixin.util.UrlUtil;

/**
 * 1、用户登录 2、首页获取用户信息
 * 控制层
 * @author jobs1127 2015/07/27
 */
@Controller
public class LoginAction extends BaseAction {
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(LoginAction.class);
	/**
	 * 控制层，自动装载服务层对象,注入对象
	 */
	@Autowired(required=true)
	@Qualifier(value="userService")
	private UserService userService;
	
	/**
	 * 使用邮箱登录
	 */
	@RequestMapping(value = "/login.do")
	public String loginGet(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		System.out.println("Come here");
		model.put("basePath", getBasePath(request));
		return "login";
	}

	// login.html
	@RequestMapping(value = "/toLogin.do")
	public JsonResult toLogin(HttpServletResponse response,
			HttpServletRequest request,String userPass,String userName, 
			String textRandomCode, ModelMap model) {
		System.out.println(userPass+"--"+userName+"--"+textRandomCode);
		/**
		 * 这里的参数应该与页面的input元素的name一一对应，才能接收得到
		 * var params = $("#login").serialize();表单form序列化，也可以通过对象（属性应该与input的name一一对应）来接收
		 * 
		 * toLogin()方法的参数个数不一定与前台页面的form里的参数name一致，但是名字要一一对应一致才能接收，参数个数很灵活
		 */
		Map<String,Object> data = new HashMap<String,Object>();
		
		/**
		 * session方案
		 * 生成的验证码存放在session里，用于与用户输入的验证码进行对比，是否输入正确
		 */
		HttpSession session = request.getSession();
		
		String randomCode = (String) session.getAttribute("randomCode");
		// com.tentinet.app.util RandomCode
		if (StringUtils.isEmpty(textRandomCode)) {//StringUtils.isEmpty判断某个字符串是否为空，如果有空格字符，认为不空
			data.put("message", "请输入验证码!");
			System.out.println("请输入验证码!");
			ResonseUtils.writeJsonResult(response, data);
			return null;
		}
		
		if(StringUtils.isNotBlank(" dd ")) {
			/***
			 * StringUtils.isNotEmpty 空字符会被单做元素。
			 * StringUtils.isNotBlank 不能为空白，null,空字符等都是空白。
			 */
		}
		// 说明验证码不正确
		if (StringUtils.isNotEmpty(textRandomCode)//StringUtils.isNotEmpty判断某个字符串是否不空
				&& !textRandomCode.equalsIgnoreCase(randomCode)) {
			data.put("message", "验证码输入不正确,请重新输入!");
		} else {// 验证码正确
			UserVo user = userService.loginUser(userName, MyMD5.toMd5(userPass));
			// UserVo user=userService.loginUser(userName, userPass);
			//System.out.println(user.getUser_name());
			if (user != null) {// 说明当前用户存在
				data.put("data", user);
				/***
				 * session里存放一个对象。
				 */
				session.setAttribute("userInfo", user);
				
				String cval = CookieUtil.getCookie(request, "userId");
				if(cval == null) {
					/***
					 * cookie方案
					 * 创建一个cookie对象，指定cookie的名字，和cookie对象存放String类型的值，并进行编码，防止中文乱码。
					 * 通过服务器响应浏览器上，通过response对象把cookie给浏览器。
					 */
					Cookie cookie=new Cookie("userId",UrlUtil.encode(user.getUser_id()+""));
					//设置时间为1年
                    cookie.setMaxAge(365*24*3600);   
                    cookie.setPath("/");
                    //把cookie给浏览器
                    response.addCookie(cookie);
				}
			} else {
				data.put("message", "用户名/密码  输入错误!");
			}
		}
	
		/**
		 * ModelMap model,可以把参数存放到Request里，前台访问  var base = "${base}";
		 */
		model.put("base", getBasePath(request));
		System.out.println("base="+model.get("base"));
		ResonseUtils.writeJsonResult(response, data);
		//System.out.println("toLogin()方法已经执行完。");
		return null;
	}

	// s.html 首页获取用户信息
	@RequestMapping(value = "/getUserInfo.do")
	public void getUserInfo(HttpServletRequest request,
			HttpServletResponse response) {
		UserVo user = (UserVo) request.getSession().getAttribute("userInfo");
		/**
		 * JSONObject oj = new JSONObject();可以把一个对象以Json对象形式体现
		 * ObjectMapper mapper = new ObjectMapper(); jsonString = mapper.writeValueAsString(data);
		 */
		JSONObject oj = new JSONObject();
		oj.put("message", user.getUser_name());
		
		//JSONObject oj2 = new JSONObject();
		//oj2.put("hah", user);
		//System.out.println(oj2.get("hah"));
		//writeResponseByJson(request, response, oj);
		
		// 以Json形式写入response
		ResonseUtils.writeResponseByJson(request,response, oj);
		
		// 下面方法是：通过Map对象存储数据，用ObjectMapper对象把MAP对象作为String写入response
		// Map data = new HashMap();
		// data.put("message", user.getUser_name());
		// ResonseUtil.writeJsonResult(response, data);
	}
}