/**
 * 
 */
package cn.jobs1127.servlet.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author jobs1127
 * 
 */
public class SessionTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF=8");
		response.setContentType("text/html;charset=UTF-8");
		// 使用request对象的getSession()获取session，如果session不存在则创建一个
		HttpSession session = request.getSession();
		/**request.getSession()方法内部新创建了Session之后一定是做了如下的处理
		 * //获取session的Id
			String sessionId = session.getId();
			//将session的Id存储到名字为JSESSIONID的cookie中
			Cookie cookie = new Cookie("JSESSIONID", sessionId);
			//设置cookie的有效路径
			cookie.setPath(request.getContextPath());
			response.addCookie(cookie);
		 */
		
		// 将数据存储到session中
		session.setAttribute("data", "Jobs1127");
		// 获取session的Id
		String sessionId = session.getId();
		// 判断session是不是新创建的
		if (session.isNew()) {
			response.getWriter().print("session创建成功，session的id是：" + sessionId);
		} else {
			response.getWriter().print("服务器已经存在该session了，session的id是："+sessionId);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
