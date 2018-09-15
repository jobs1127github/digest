package com.tentinet.app.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tentinet.app.bean.InformationVo;
import com.tentinet.app.bean.UserVo;
import com.tentinet.app.bean.dto.InformationDto;
import com.tentinet.app.service.InformationService;
import com.tentinet.app.util.ConfigUtil;
import com.tentinet.app.util.DateFill;
import com.tentinet.app.util.Page;

/**
 * 资讯管理
 * 
 */
@Controller
public class InformationAction extends BaseAction {
	private static Logger logger = Logger.getLogger(InformationAction.class);

	@Autowired
	private InformationService informationService;

	/**
	 * 资讯列表查询
	 * 
	 * @param request
	 * @param response
	 * @param title
	 * @param keywords
	 * @param status
	 * @param author
	 * @param mark
	 * @param page
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/information/queryInformation.do")
	public void queryInformation(HttpServletRequest request,
			HttpServletResponse response, String title, String keywords,
			String status, String openid, String best_flag, Page page) {
		/***
		 * page对象中有pageSize、pageNo，故可以通过page对象来接收前台页面传递过来的参数。
		 * page接收前台页面的pageSize、pageNo参数
		 */
		HttpSession session = request.getSession();
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(title)) {
			params.put("title", "%" + title + "%");
		}
		if (StringUtils.isNotEmpty(keywords)) {
			params.put("keywords", "%" + keywords + "%");
		}
		if (StringUtils.isNotEmpty(status) && !StringUtils.equals("0", status)) {
			params.put("status", status);
		}
		if (StringUtils.isNotEmpty(status) && StringUtils.equals("0", status)) {
			params.put("status_34", "status_34");
		}
		if (StringUtils.isNotEmpty(openid)) {
			params.put("openid", openid);
		}
		if (StringUtils.isNotEmpty(best_flag) && !"0".equals(best_flag)) {
			params.put("best_flag", best_flag);
		}
		
		/***
		 * 通过session保存第一次请求的参数字符串，然后其他的请求，获得的参数字符串都会与该session里的字符串比较，
		 * 如果相同则表示查询条件没变化，则无需去数据库拿取数据，而是直接拿取第一次请求获得的结果（从session里拿取）。
		 * 这个其实就是查询缓存。
		 */
		/**
		 * StringBuffer线程安全的
		 */
		StringBuffer sb = new StringBuffer();
		
		for(String key:params.keySet()) {
			sb.append(key).append(params.get(key));
		}
		System.out.println("sb="+sb);
		List<InformationDto> list = null;
		List<InformationDto> newList = null;
		Integer count = 0;
		if(session.getAttribute("sb")!=null&&session.getAttribute("sb").toString().equals(sb.toString())) {
			
		} else {
			session.setAttribute("sb",sb.toString());
			/**
			 * 分页相关设置：
			 * 一次性查询出所有的对象，存放到collection容器中，然后在从容器中去拿取分页相关的数据。
			 * 前台通过js组件封装分页，每次点击分页页码时，仍然要调用该queryInformation.do方法，在访问数据库，
			 * 把所有的数据查询出来，并存放到list容器中，再通过分页相关属性，得到分页相关的数据视图。
			 */
			/**查询出一共有多少条记录*/
			count = informationService.queryInformationCount(params);
			session.setAttribute("information-count", count);
			/**查询出符合条件的所有对象，并存放到List容器里*/
			list = informationService.queryInformationInfos(params);
			session.setAttribute("information-list", list);
		}
		
		page.setTotalCount((Integer)(session.getAttribute("information-count")));
		/**从存放所有数据的List容器中，分页显示*/
		newList = getPageList((List<InformationDto>)(session.getAttribute("information-list")), page);
		
		System.out.println("分页："+page.getPageNo());
		
		
		JSONObject oj = new JSONObject();
		oj.put("data", newList);
		oj.put("total", page.getTotalCount());
		writeResponseByJson(request, response, oj);
	}

	@RequestMapping(value = "/information/queryInformationById.do")
	public void queryInformationById(HttpServletRequest request,
			HttpServletResponse response) {
		String informationId = request.getParameter("information_id");
		InformationVo Vo = informationService
				.queryInformationById(informationId);
		JSONObject oj = new JSONObject();
		oj.put("data", Vo);
		writeResponseByJson(request, response, oj);
	}

	/**
	 * 点击修改时 修改资讯状态
	 * 
	 * @param information
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/information/updateInformation.do")
	public void updateInformation(HttpServletRequest request,
			HttpServletResponse response) {
		String information_id = request.getParameter("information_id");
		String status = request.getParameter("status");
//		System.out.println("information_id="+information_id);
//		System.out.println("status="+status);
		InformationVo Vo = informationService.queryInformationById(information_id);
		Vo.setStatus(status);
		Boolean rel = informationService.updateInformation(Vo);
		JSONObject oj = new JSONObject();
		oj.put("data", rel);
		writeResponseByJson(request, response, oj);
	}

	@RequestMapping(value = "/information/allUpdateInformation.do")
	public void allUpdateInformation(HttpServletRequest request,
			HttpServletResponse response) {
		String information_id = request.getParameter("information_id");
		String status = request.getParameter("status");
		String[] information_Ids = information_id.split(",");
		Boolean rel = false;
		for (int i = 0; i < information_Ids.length; i++) {
			InformationVo Vo = informationService.queryInformationById(information_Ids[i]);
			Vo.setStatus(status);
			rel = informationService.updateInformation(Vo);
		}
		JSONObject oj = new JSONObject();
		oj.put("data", rel);
		writeResponseByJson(request, response, oj);
	}

	/**
	 * 修改资讯
	 * 
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/information/updateInformations.do")
	public void updateInformations(InformationVo information,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		InformationVo Vo = informationService.queryInformationById(information
				.getInformation_id());
		String text = information.getContent();
		UserVo loginUser = (UserVo) request.getSession().getAttribute(
				"userInfo");
		information.setUpdated_by(loginUser.getUser_name());
		information.setUpdated_time(DateFill.getSysDate("yyyy-MM-dd HH:mm:ss"));
		// information.setStatus("2");
		information.setContent(text);
		information.setCreated_by(Vo.getCreated_by());
		information.setCreated_time(Vo.getCreated_time());
		String mark = information.getMark();
		if (mark == null || "".equals(mark)) {
			information.setMark(Vo.getMark());
		}
		information.setBest_flag(information.getBest_flag());
		Boolean rel = informationService.updateInformation(information);
		JSONObject oj = new JSONObject();
		oj.put("data", rel);
		writeResponseByJson(request, response, oj);
	}

	/**
	 * 添加资讯
	 * 
	 * @param information
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/information/saveInformation.do")
	public void saveInformation(InformationVo information,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		UUID uuid = UUID.randomUUID();
		information.setInformation_id(uuid.toString());
		String text = information.getContent();
		UserVo loginUser = (UserVo) request.getSession().getAttribute("userInfo");
		information.setCreated_by(loginUser.getUser_name());
		information.setUpdated_by(loginUser.getUser_name());
		information.setCreated_time(DateFill.getSysDate("yyyy-MM-dd HH:mm:ss"));
		information.setUpdated_time(DateFill.getSysDate("yyyy-MM-dd HH:mm:ss"));
		information.setStatus("1");
		information.setContent(text);
		Boolean rel = informationService.saveInformation(information);
		JSONObject oj = new JSONObject();
		oj.put("data", rel);
		writeResponseByJson(request, response, oj);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/informatiom/resourcesManage.do")
	public void resourcesManage(HttpServletRequest request,HttpServletResponse response) {
		String rootPath = 
				ConfigUtil.getValue("baseDir").replaceAll("//",File.separator)
				+ ConfigUtil.getValue("imageDir");
		System.out.println("rootPath="+rootPath);
		// 根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
		String rootUrl = ConfigUtil.getValue("fileDir")+ConfigUtil.getValue("imageDir");
		System.out.println("rootUrl="+rootUrl);
		// 图片扩展名
		String[] fileTypes = new String[] { "gif", "jpg", "jpeg", "png", "bmp" };

		String dirName = request.getParameter("dir");
		System.out.println("dirName"+dirName);
		if (dirName != null) {
			if (!Arrays.<String> asList(
					new String[] { "image", "flash", "media", "file" })
					.contains(dirName)) {
				// out.println("Invalid Directory name.");
				return;
			}
			File saveDirFile = new File(rootPath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
		}
		// 根据path参数，设置各路径和URL
		String path = request.getParameter("path") != null ? request.getParameter("path") : "";
		System.out.println("path="+path);
		String currentPath = rootPath + path;
		String currentUrl = rootUrl + path;
		String currentDirPath = path;
		System.out.println("currentPath="+currentPath);
		System.out.println("currentUrl="+currentUrl);
		String moveupDirPath = "";
		if (!"".equals(path)) {
			String str = currentDirPath.substring(0,currentDirPath.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0,str.lastIndexOf("/") + 1) : "";
		}
		System.out.println("moveupDirPath="+moveupDirPath);
		// 排序形式，name or size or type
		String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";

		// 不允许使用..移动到上一级目录
		if (path.indexOf("..") >= 0) {
			// out.println("Access is not allowed.");
			return;
		}
		// 最后一个字符不是/
		if (!"".equals(path) && !path.endsWith("/")) {
			// out.println("Parameter is not valid.");
			return;
		}
		// 目录不存在或不是目录
		File currentPathFile = new File(currentPath);
		if (!currentPathFile.isDirectory()) {
			// out.println("Directory does not exist.");
			return;
		}

		// 遍历目录取的文件信息
		List<Hashtable> fileList = new ArrayList<Hashtable>();
		if (currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = file.getName();
				if (file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if (file.isFile()) {
					String fileExt = fileName.substring(
							fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", Arrays.<String> asList(fileTypes)
							.contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime",
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file
								.lastModified()));
				fileList.add(hash);
			}
		}
		JSONObject result = new JSONObject();
		result.put("moveup_dir_path", moveupDirPath);
		result.put("current_dir_path", currentDirPath);
		result.put("current_url", currentUrl);
		result.put("total_count", fileList.size());
		result.put("file_list", fileList);
		writeResponseByJson(request, response, result);
	}

	@RequestMapping(value = "/information/upload.do")
	public void upload(HttpServletRequest request, HttpServletResponse response) throws FileUploadException {
		/**
		 * 获取项目的真实路径
		 */
		String realPath = request.getSession().getServletContext().getRealPath("/");
		System.out.println("realPath="+realPath);
	
		/**
		 * url=http://localhost:8080/Digest_wx
		 */
		String url = request.getScheme()+"://"
		+request.getServerName()+":"+request.getServerPort()
		+request.getContextPath();
		System.out.println("url="+url);
		
		System.out.println("获取uri="+request.getRequestURI());
		System.out.println("获取参数="+request.getQueryString());
		System.out.println("获取全路径="+request.getRequestURL());
		/**
		 * multipartRequest对象能获取到文件等内容
		 */
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String dirName = request.getParameter("dir");
		/**
		 * 如果上传的是音视频则为media，如果是上传图片则为image
		 */
		System.out.println("dirName="+dirName);
		/**
		 * 获得用户从Editor上传的文件：
		 * imgFile参考如下js里的配置，
		 * image.js/media.js:
		 * filePostName = K.undef(self.filePostName, 'imgFile'),
		 */
		MultipartFile file = multipartRequest.getFile("imgFile");
		System.out.println("file="+file);
		// 获得文件名：
		String fileName = file.getOriginalFilename();
		System.out.println("fileName="+fileName);
		//获得文件的后缀名
		String img_name = fileName.substring(fileName.lastIndexOf("."));
		System.out.println("后缀："+img_name);
		//后缀保持不变重命名，生成随机id的文件名，避免用户上传的是中文的文件名文件
		fileName = UUID.randomUUID().toString()+img_name;
		
		// 第一种方式，把文件保存到指定的绝对路径
		try {
			InputStream input = file.getInputStream();
			request.getServletPath();
			String imagePath = ConfigUtil.getValue("baseDir")+ConfigUtil.getValue("imageDir");// 图片路径
			System.out.println("imagePath="+imagePath+fileName);
			//把上传的文件写入到指定路径的硬盘位置
			FileOutputStream fos = new FileOutputStream(imagePath + fileName);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = input.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		// 第二种方式，把文件保存到和项目路径相同的路径，在WebContent下新建attachment等文件夹
		/**
		 * 用项目的当前路径来保存上传的图片
		 * 
		try {
			InputStream input = file.getInputStream();
			// 图片存放的路径
			String imagePath = ConfigUtil.getValue("savePath")+ConfigUtil.getValue("imageDir");
			System.out.println("imagePath="+imagePath+fileName);
			//把上传的文件写入到指定路径的硬盘位置
			FileOutputStream fos = new FileOutputStream(realPath+imagePath + fileName);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = input.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		 * 
		 */
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		//第一种方式
		obj.put("url",ConfigUtil.getValue("fileDir")+ConfigUtil.getValue("imageDir")+fileName);
		//第二种方式
		//obj.put("url",url+ConfigUtil.getValue("savePath")+ConfigUtil.getValue("imageDir")+fileName);
		System.out.println(obj.get("url"));
		writeResponseByJson(request, response, obj);
	}
	
	public static void main(String[] args) {
		InformationVo information = new InformationVo();
	}
}