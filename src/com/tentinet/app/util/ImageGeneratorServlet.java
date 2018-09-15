package com.tentinet.app.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 图片上传显示
 */
public class ImageGeneratorServlet extends HttpServlet{
    
	private static final long serialVersionUID = -2877911269946264649L;
	private Logger logger=Logger.getLogger(ImageGeneratorServlet.class);
	public void destroy() {
    }

    public void init() throws ServletException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        //String basePath = ConfigUtil.getValue("baseDir");
        String filePath = request.getParameter("filePath");
        OutputStream out = null;
        if (StringUtils.isEmpty(filePath)) {
            return;
        }
        ServletOutputStream servletOut = null;
        
        if (logger.isInfoEnabled()) {
            logger.info("filePath=" + filePath);
        }
       /* try {
            filePath = filePath.substring(filePath.indexOf("/u/"));
        } catch (Exception e) {
            logger.error(e.getMessage());
            filePath = filePath.substring(filePath.indexOf("\\u\\"));
        }*/
        File file = new File(filePath);
        try {
            long totalsize = 0;
            // File file = new File(basePath + filePath);
            if (file != null && file.exists()) {
                long filelength = file.length();
                byte[] b = new byte[1024];
                DataInputStream dis = new DataInputStream(new FileInputStream(file));
                String filesize = Long.toString(filelength);
                response.setHeader("Content-Length", filesize);
                servletOut = response.getOutputStream();
                while (totalsize < filelength) {
                    totalsize += 1024;
                    if (totalsize > filelength) {
                        byte[] leftpart = new byte[1024 - (int) (totalsize - filelength)];
                        dis.readFully(leftpart);
                        servletOut.write(leftpart);
                    } else {
                        dis.readFully(b);
                        servletOut.write(b);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            logger.error("ImageGeneratorServlet file not found:", e);
        } catch (IOException e) {
            logger.error("ImageGeneratorServlet io exception:", e);
        } catch (Exception e) {
            logger.error("ImageGeneratorServlet exception:", e);
        } finally {
            if (file == null || !file.exists()) {
                logger.error(filePath + "下文件不存在!");
            } else {
                try {
                    servletOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
