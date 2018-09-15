package com.tentinet.app.util;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
/**
 * 图片上传，音频上传
 * @author jobs1127
 *
 */
@SuppressWarnings("serial")
public class UploadServlet extends HttpServlet {
	@SuppressWarnings("unchecked")
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * 前台页面：
		 * 'scriptData' : {
				'type' : '2' //type为用户自定义的参数，使用了这个自定义的参数，则需要method为get
			},
		 */
		String u_type = request.getParameter("type");
		String fileName = request.getParameter("fileName");
		/**
		 * u_type=2
		   fileName=null，前台没有fileName参数传来，故为null
		 */
		System.out.println("u_type="+u_type);
		System.out.println("fileName="+fileName);
		
		//保存文件的路径
		String savePath = "";
		//显示文件的路径
		String showPath = "";
		if ("1".equals(u_type)) {
			savePath = ConfigUtil.getValue("baseDir")+ ConfigUtil.getValue("audioDir");// 音频存储路劲
			showPath = ConfigUtil.getValue("fileDir")+ ConfigUtil.getValue("audioDir");// 音频显示路劲
		}
		if ("2".equals(u_type)) {
			savePath = ConfigUtil.getValue("baseDir")+ ConfigUtil.getValue("imageDir");// 图片存储路径
			showPath = ConfigUtil.getValue("fileDir")+ ConfigUtil.getValue("imageDir");// 图片显示路径
		}
		/**
		 * savePath=D:/apache-tomcat-6.0.29/webapps/Digest_wx_files/attachment/digest_wx/image/
		 */
		System.out.println("savePath="+savePath);
		/**
		 * showPath=http://localhost:8080/Digest_wx_files/attachment/digest_wx/image/
		 */
		System.out.println("showPath="+showPath);
		/**
		 * 文件路径如果不存在，则创建该路径文件夹
		 */
		File f1 = new File(savePath);
		if (!f1.exists()) {
			f1.mkdirs();
		}
		/**
		 * 创建硬盘文件项目工厂
		 */
		DiskFileItemFactory fac = new DiskFileItemFactory();
		/**
		 * 根据创建的硬盘文件项目工厂，new出ServletFileUpload实例
		 */
		ServletFileUpload upload = new ServletFileUpload(fac);
		/***
		 * 设置上传时的编码
		 */
		upload.setHeaderEncoding("utf-8");
		/**
		 * 该list容器存放上传来的文件项
		 */
		List<FileItem> fileList = null;
		try {
			/**
			 * ServletFileUpload解析request请求对象，获取一系列的上传的item项。
			 */
			fileList = upload.parseRequest(request);
		} catch (FileUploadException ex) {
			return;
		}
		/**
		 * fileList.size()=3
		 */
		System.out.println("fileList.size()="+fileList.size());
		//for循环迭代list容器
		for(FileItem fi:fileList) {
			System.out.println("getFieldName="+fi.getFieldName());
			System.out.println("getName="+fi.getName());
			System.out.println("getSize="+fi.getSize());
			System.out.println("getContentType="+fi.getContentType());
			System.out.println("isFormField="+fi.isFormField());
			System.out.println("isInMemory="+fi.isInMemory());
			System.out.println("---------------------------------");
		}
		//用迭代器来迭代list容器
		Iterator<FileItem> it = fileList.iterator();
		String name = "";
		String extName = "";
		while (it.hasNext()) {
			FileItem item = it.next();
			/**
			 * item.isFormField()判断该表单项是否是普通类型，否则该表单项是file类型的
			 * Filename,isFormField为true，所以Filename属性字段为form表单的普通类型
			 * Filedata,isFormField为false，所以Filedata属性字段为file文件类型
			 */
			if (!item.isFormField()) {
				name = item.getName();//文件上传时的原文件名，可能为中文名字
				System.out.println("name="+name);
				double size = item.getSize();// 单位为字节
				System.out.println("size="+size/1024/1024);
				/**可以对上传上来的文件，进行大小限制
				 * 
				 */
//				if ("1".equals(u_type) && size/1024/1024>100){//附件不能大于100M
//					 return; 
//				} 
//				if ("2".equals(u_type) && size/1024>500){//图片不能大于500KB
//					return; 
//				}
						 
				String type = item.getContentType();
				System.out.println("type="+type);
				if (name == null || name.trim().equals("")) {
					continue;
				}
				// 扩展名格式：
				if (name.lastIndexOf(".") >= 0) {
					extName = name.substring(name.lastIndexOf("."));
				}
				//若上传的格式不对，则直接返回
				if ("1".equals(u_type) && !extName.equalsIgnoreCase(".mp3")) {
					return;
				}
				//若上传的格式不对，则直接返回
				if ("2".equals(u_type) && !extName.equalsIgnoreCase(".jpg")) {
					return;
				}
				File file = null;
				// 生成文件名：把文件名字重新命名，防止文件名重复，防止用户上传中文名的文件
				name = UUID.randomUUID().toString();
				// name=fileName;
				file = new File(savePath + name + extName);
				//若该文件存在则删除
				if (file.exists()) {
					file.delete();
				}
				//保存该文件
				File saveFile = new File(savePath + name + extName);
				try {
					//把该fileItem文件写入到saveFile文件路径下
					item.write(saveFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		//把文件（图片、视频）显示的路径，写入到response对象里
		response.getWriter().print(showPath + "/" + name + extName);
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet first");
		this.doPost(request, response);
	}
}