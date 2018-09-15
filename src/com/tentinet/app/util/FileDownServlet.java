package com.tentinet.app.util;

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
		//System.out.println("zipFilePath="+zipFilePath);
		/**
		 * 要实现客户端有下载的对话框，这里需要设置response的类型为application/x-msdownload
		 */
		response.setContentType("application/x-msdownload");
		response.setCharacterEncoding("utf-8");
		// 为了让https能够下载
		response.setHeader("Expires", "0");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Cache-Control", "public");
		
		ServletOutputStream servletOut = null;
		//把文件路径，包装成File
		File file = new File(zipFilePath);
		System.out.println("fileName="+file.getName());
		// 如果此处不是UTF-8会出现乱码
		try {
			response.addHeader("Content-Disposition", "attachment;filename="+ java.net.URLEncoder.encode(file.getName(), "UTF-8"));
			long totalsize = 0;
			if (file != null && file.exists()) {
				long filelength = file.length();
				System.out.println("filelength="+filelength);
				//把File包装成FileInputStream，再包装成DataInputStream
				DataInputStream dis = new DataInputStream(new FileInputStream(file));
				String filesize = Long.toString(filelength);
				response.setHeader("Content-Length", filesize);
				/**
				 * 拿取到response对象的输出管道，然后把目标文件读入到内存，再把内存里的数据写入到response里。
				 */
				servletOut = response.getOutputStream();
				byte[] b = new byte[1024]; 
				//方式1：
				for(int len=0;(len=dis.read(b))!=-1;) {
					servletOut.write(b, 0, len);
				}
				//方式2：
//				while (totalsize < filelength) {
//					totalsize += 1024;
//					if (totalsize > filelength) {
//						byte[] leftpart = new byte[1024 - (int) (totalsize - filelength)];
//						dis.readFully(leftpart);
//						servletOut.write(leftpart);
//						servletOut.flush();
//					} else {
//						dis.readFully(b);
//						servletOut.write(b);
//						servletOut.flush();
//					}
//				}
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
		this.doGet(request, response);
	}
}