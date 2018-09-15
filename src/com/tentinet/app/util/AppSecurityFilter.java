package com.tentinet.app.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tentinet.app.bean.UserVo;
/**
 * 权限过滤器,filter在web.xml里进行配置，web容器启动后，会执行init()
 * @author jobs1127
 *
 */
public class AppSecurityFilter implements Filter {
	private final static String LOGIN_URL = "/login.do";

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		//获取请求的URl
		String url = httpRequest.getRequestURL().toString();
		//获取servlet上下文
		ServletContext servletContext = httpRequest.getSession().getServletContext();
		String loginUrl = getBasePath(httpRequest) + LOGIN_URL;
		HttpSession session = httpRequest.getSession(true);
		UserVo user = (UserVo) session.getAttribute("userInfo");
		//System.out.println("url="+url+"LOGIN_URL="+LOGIN_URL);
		if (loginUrl.equals(url)) {
			chain.doFilter(httpRequest, httpResponse);
			return;
		} else {
			if (url.contains("login.html")) {
				chain.doFilter(httpRequest, httpResponse);
				return;
			} else {
				if (user != null) {
					chain.doFilter(httpRequest, httpResponse);
					return;
				} else {
					//通过Servlet上下文拿到请求分发器
					RequestDispatcher rd = servletContext.getRequestDispatcher(LOGIN_URL);
					//请求分发器跳转
					rd.forward(httpRequest, httpResponse);
				}
			}
		}
		return;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// init(filterConfig);
	}

	private static String getBasePath(HttpServletRequest request) {
		StringBuffer basePath = new StringBuffer();
		basePath.append(request.getScheme());
		basePath.append("://");
		basePath.append(request.getServerName());
		basePath.append(":");
		basePath.append(request.getServerPort());
		basePath.append(request.getContextPath());
		// basePath.append("/");
		return basePath.toString();
	}
}