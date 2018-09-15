package com.tentinet.app.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.tentinet.app.bean.UserVo;

/**
 * @Component标识一个bean，交由Spring控管,在web.xml中直接写个这个类名，web容器就能知道
 * 操作人员权限过滤 判断是否登陆 filter
 */
@Component
public class OMSSecurityFilter implements Filter {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(OMSSecurityFilter.class);
	/**
	 * ThreadLocal能把对象存放到线程范围内共享，同一个线程共享其自己的数据。
	 */
	//public static final ThreadLocal threadLocal = new ThreadLocal();
	/**
	 * 排除不校验权限的URL,ConcurrentHashMap是线程安全的，能支持并发访问的HashMap,HashMap线程不安全
	 */
	private Map<String, String> excludeUrls = new ConcurrentHashMap<String, String>();
	
	/**
	 * 测试map,set
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, String> map = new ConcurrentHashMap<String, String>();
		Set<String> set = new HashSet<String>();
		map.put("hah", "");
		map.put("hah", "cc");
		map.put("hah", "ccd");
		System.out.println(map.size());
		System.out.println(map.get("hah"));
		set.add("hh");
		set.add("hh2");
		set.add("hh");
		System.out.println(set.size());
		
	}
	private void writeSecurityFilter(HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = null;
		try {
			/**
			 * 把errorCode=-2000写入response，前台在请求时都会调用httpService.js的sendRequest()方法
			 * 如果为errorCode=-2000，则：top.location.href=pageContextPath+"/login.html";
			 */
			ObjectMapper mapper = new ObjectMapper();
			JsonResult jsonResult = JsonResult.failure(JsonResult.ERROR_CODE_NO_OPERATOR_SECURITY, "对不起，请您先登录!");
			/***
			 * 把jsonResult对象，转换成json格式的字符串，并写入到response对象里
			 */
			String jsonString = mapper.writeValueAsString(jsonResult);
			writer = response.getWriter();
			writer.print(jsonString);
			return;
		} catch (IOException e) {
			//logger.error("response write error:", e);
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}
	/**
	 * web.xml容器关闭后，执行
	 */
	@Override
	public void destroy() {
		logger.info("=================EcourseSecurityFilter destroy==================");
	}
	int i= 0;
	/**
	 * 发起请求时，执行
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		//System.out.println("OMSSecurityFilter doFilter-----------"+i++);
		HttpSession session = req.getSession(true);
		String requestUri = req.getRequestURI();
		//System.out.println("requestUri="+requestUri);
		
		String userId = null;
		
		/**
		 * 从session里取的用户名信息
		 */
		UserVo user = (UserVo) session.getAttribute("userInfo");
		if(user != null) {
			userId = user.getUser_id()+"";
		}
		
		/**
		 * 如果都需要修改成从cookie里获取数据，则在其他使用到session的地方也有同步修改。
		 *  从cookie里获取用户的信息
		 
		userId = CookieUtil.getCookie(req, "userId");
		
		System.out.println("OM-userId="+userId);
		System.out.println("excludeUrls.size()="+excludeUrls.size());
		*/
		/**
		 * 排除不需要过滤器处理的URL,
		 * excludeUrls中key=/Digest_wx/toLogin.do或者uri中包含/wechat或者包含/sysGold的
		 * 请求都直接放行。
		 */
		if (excludeUrls.containsKey(requestUri)||requestUri.contains("/wechat")
				||requestUri.contains("/sysGold")) {
			//System.out.println("uri="+requestUri);
			/***
			 * filter拦截器，在浏览器请求*.do时，就会被拦截，chain.doFilter()放行后，才去调用
			 * com.tentinet.app.action.LoginAction
			 * @RequestMapping(value = "/toLogin.do")对应的toLogin()方法。
			 */
			chain.doFilter(request, response);
			return;
		}
		
		/***
		 * 当访问其他的.do的请求时，会被拦截判断是否存在user，比如session过期了user就不存在了。
		 */
		// 判断如果没有取到用户信息,就跳转到登陆页面
		if (userId == null) {
			//System.out.println("haha user=null");
			// 跳转到登陆页面 jsonData.errorCode == -2000
			writeSecurityFilter(res);
			return;
		} else {
			// 已经登陆,继续此次请求
			chain.doFilter(request, response);
		}
	}
	@Override
	/**
	 * web.xml容器启动后，执行
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		//logger.debug("=================OMSSecurityFilter init==================");
		/**
		 * <param-name>ExcludedUrl</param-name>
			<param-value>/WEB-INF/excludeUrl.properties</param-value>
		 */
		String excludedUrls = filterConfig.getInitParameter("ExcludedUrl");
		//System.out.println("excludedUrls="+excludedUrls);
		InputStream in = null;
		try {
			/**
			 * 把excludeUrl.properties文件，加载到内存，并存放到线程安全的HashMap中
			 */
			File file = new File(filterConfig.getServletContext().getRealPath("/")+ excludedUrls);
			System.out.println("file.getPath()="+file.getPath());
			if (file.exists() && file.canRead()) {
				in = new FileInputStream(file);
				Properties prop = new Properties();
				prop.load(in);
				if (prop != null) {
					for (Object excludeUrl : prop.keySet()) {
						String sUrl = excludeUrl.toString().trim();
						//System.out.println("excludeUrl:" + sUrl);
						excludeUrls.put(sUrl, "");
					}
				}
			}
			/**
			 * 硬盘上的文件，加载到程序内存中的方式2，
			 * 保证在OMSSecurityFilter类同目录下存在文件：login.properties即可。
			 */
			in = OMSSecurityFilter.class.getResourceAsStream("login.properties");
			Properties prop = new Properties();
			prop.load(in);
			if (prop != null) {
				for (Object excludeUrl : prop.keySet()) {
					String sUrl = excludeUrl.toString().trim();
					//System.out.println("第二种方法，excludeUrl:" + prop.get(sUrl));
					//excludeUrls.put(sUrl, "");//sUrl的值为key:url
				}
			}
		} catch (IOException e) {
			//logger.error("load " + excludedUrls + " exception", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
	}
}