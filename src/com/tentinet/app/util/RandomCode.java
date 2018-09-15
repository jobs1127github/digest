package com.tentinet.app.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 验证码 通过web.xml配置，访问servlet，处理验证码相关事宜
 * @author jobs1127 2015－07－27
 */
public class RandomCode extends HttpServlet {
	private static final long serialVersionUID = 6559023741220317887L;
	private static final int WIDTH = 65;// 设置图片的宽
	private static final int HEIGHT = 22;// 设置图片的高
	/***
	 * RandomCode类继续HTTPServlet后，自己就成了一个Servlet小应用程序了。
	 * web请求基于HTTP协议，请求封装到HttpServletRequest对象里，响应封装到HTTPServletResponse对象里
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Object o = request.getParameter("imgCount");
		//System.out.println(o);
		
		
		HttpSession session = request.getSession();
		// 判断是否是新初始化的session
		if (!session.isNew()) {
			//System.out.println("!session.isNew()");
			session.invalidate();
			session = request.getSession();
		} 
		/**
		 * 页面是通过<image 标签来请求的，所以response响应浏览器的类型是image/jpeg，而不是html.
		 * 输出的图片格式，控制输出网页、或文本、或图片
		 */
		response.setContentType("image/jpeg");
		ServletOutputStream sos = response.getOutputStream();
		//返回的数据不要被浏览器缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		// 初始化图像
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);
		// 初始化画笔
		Graphics g = image.getGraphics();
		char[] rands = generateCheckCode();
		// 写入背景
		drawBackground(g);
		// 写入随机数
		drawRands(g, rands);
		//dispose()  释放此图形的上下文以及它使用的所有系统资源。
		g.dispose();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(image, "JPEG", bos);
		byte[] buf = bos.toByteArray();
		// 控制输出内容的长度
		response.setContentLength(buf.length);
		sos.write(buf);
		bos.close();
		sos.close();
		
		session.setAttribute("randomCode", new String(rands));
	}

	// 画出背景图片
	private void drawBackground(Graphics g) {
		g.setColor(new Color(0xDCDCDC));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		for (int i = 0; i < 120; i++) {
			int x = (int) (Math.random() * WIDTH);
			int y = (int) (Math.random() * HEIGHT);
			int red = (int) (Math.random() * 255);
			int green = (int) (Math.random() * 255);
			int blue = (int) (Math.random() * 255);
			// 图片颜色组成
			g.setColor(new Color(red, green, blue));
			g.drawOval(x, y, 1, 0);
		}
	}

	// 随机生成字符或数字
	private void drawRands(Graphics g, char[] rands) {
		// g.setColor(Color.BLUE);
		Random random = new Random();
		int red = random.nextInt(110);
		int green = random.nextInt(50);
		int blue = random.nextInt(50);
		g.setColor(new Color(red, green, blue));
		g.setFont(new Font(null, Font.ITALIC | Font.BOLD, 18));
		g.drawString("" + rands[0], 1, 17);
		g.drawString("" + rands[1], 16, 15);
		g.drawString("" + rands[2], 31, 18);
		g.drawString("" + rands[3], 46, 16);
	}
	/**
	 * @return 四位随机字母或数字
	 */
	private char[] generateCheckCode() {
		String chars = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
		char[] rands = new char[4];
		for (int i = 0; i < 4; i++) {
			int rand = (int) (Math.random() * 32);
			rands[i] = chars.charAt(rand);
		}
		return rands;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
}
