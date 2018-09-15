package cn.jobs1127.utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 文件下载Servlet
 * 
 */
public class FileDownServlet extends HttpServlet {

	private static final long serialVersionUID = 6471692487967490797L;
	private Logger logger = Logger.getLogger(FileDownServlet.class);

	public void destroy() {
	}

	public void init() throws ServletException {
		System.out.println("文件下载filter");
	}

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String zipFilePath = request.getParameter("url");
		response.setContentType("application/x-msdownload");
		response.setCharacterEncoding("utf-8");
		// 为了让https能够下载
		response.setHeader("Expires", "0");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Cache-Control", "public");
		ServletOutputStream servletOut = null;
		File file = new File(zipFilePath);
		//System.out.println(file.getName());
		// 如果此处不是UTF-8会出现乱码
		try {
			response.addHeader("Content-Disposition", "attachment;filename="+ java.net.URLEncoder.encode(file.getName(), "UTF-8"));
			long totalsize = 0;
			if (file != null && file.exists()) {
				long filelength = file.length();
				DataInputStream dis = new DataInputStream(new FileInputStream(file));
				String filesize = Long.toString(filelength);
				response.setHeader("Content-Length", filesize);
				servletOut = response.getOutputStream();
				byte[] b = new byte[1024];
				while (totalsize < filelength) {
					totalsize += 1024;
					if (totalsize > filelength) {
						byte[] leftpart = new byte[1024 - (int) (totalsize - filelength)];
						dis.readFully(leftpart);
						servletOut.write(leftpart);
						servletOut.flush();
					} else {
						dis.readFully(b);
						servletOut.write(b);
						servletOut.flush();
					}
				}
				dis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (file == null || !file.exists()) {
				logger.error(zipFilePath + "文件不存在!");
			} else {
				try {
					servletOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("meihao");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
}