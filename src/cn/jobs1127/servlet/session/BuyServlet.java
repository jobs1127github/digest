/**
 * @author jobs1127
 *
 */
package cn.jobs1127.servlet.session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BuyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		Book book = DB.getAll().get(id); // 得到用户想买的书
		HttpSession session = request.getSession();
		List<Book> list = (List) session.getAttribute("list"); // 得到用户用于保存所有书的容器
		if (list == null) {
			list = new ArrayList<Book>();
			session.setAttribute("list", list);
		}
		//用户买了书，就把书添加到list
		list.add(book);
		// response.encodeRedirectURL(java.lang.String url)用于对sendRedirect方法后的url地址进行重写
		String url = response.encodeRedirectURL(request.getContextPath()
				+ "/servlet/session/ListCartServlet.go");
		System.out.println(url);
		//从定向到购物车 展示用户购买的书
		response.sendRedirect(url);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}