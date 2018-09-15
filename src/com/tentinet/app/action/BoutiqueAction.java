package com.tentinet.app.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tentinet.app.bean.BoutiqueVo;
import com.tentinet.app.bean.UserVo;
import com.tentinet.app.service.BoutiqueService;
import com.tentinet.app.util.DateFill;
import com.tentinet.app.util.Page;

/**
 * 精品资讯管理
 * @author bond
 *
 */
@Controller
public class BoutiqueAction extends BaseAction{
	 private static Logger logger = Logger.getLogger(BoutiqueAction.class);

	@Autowired
	private BoutiqueService boutiqueService;
	
	/**
	 * 精品资讯列表查询
	 * @param request
	 * @param response
	 * @param title
	 * @param status
	 * @param page
	 */
	@RequestMapping(value = "/boutique/queryBoutique.do")
	public void queryBoutique(HttpServletRequest request,
			HttpServletResponse response, String title,
			String status,Page page) {
		/***
		 * page对象中有pageSize、pageNo属性，故可以通过对象来接收前台页面传递过来的参数：
		 * sendRequest(url,"pageSize=15&pageNo=" + pageNo + "&" + params,
		 * 
		 */
		HttpSession session = request.getSession();
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(title)) {
			params.put("title", "%" + title + "%");
		}
		if (StringUtils.isNotEmpty(status) && !StringUtils.equals("0", status)) {
			params.put("status", status);
		}
		if (StringUtils.isNotEmpty(status) && StringUtils.equals("0", status)) {
			params.put("status_34", "status_34");
		}
		
		page.setAutoCount(true);// 直动设置分页
		Integer Count = boutiqueService.queryBoutiqueCount(params);
		page.setTotalCount(Count);
		List<BoutiqueVo> list = boutiqueService.queryBoutique(params);
		List<BoutiqueVo> newList=getPageList(list, page);
		
		
		JSONObject oj = new JSONObject();
		oj.put("data", newList);
		oj.put("total", Count);
		writeResponseByJson(request, response, oj);

	}
	
	@RequestMapping(value = "/boutique/queryBoutiqueById.do")
	public void queryInformationById(HttpServletRequest request, HttpServletResponse response) {
		String informationId=request.getParameter("information_id");
		BoutiqueVo Vo = boutiqueService.queryBoutiqueById(informationId);
		JSONObject oj = new JSONObject();
		oj.put("data", Vo);
		writeResponseByJson(request, response, oj);
	}
	/**
	 * 修改资讯状态
	 * @param information
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/boutique/updateBoutique.do")
	public void updateInformation(HttpServletRequest request, HttpServletResponse response) {
		String information_id=request.getParameter("information_id");
		String status=request.getParameter("status");
		BoutiqueVo Vo=boutiqueService.queryBoutiqueById(information_id);
		Vo.setStatus(status);
		Boolean rel= boutiqueService.updateBoutique(Vo);
		JSONObject oj = new JSONObject();
		oj.put("data", rel);
		writeResponseByJson(request, response, oj);
	}
	
	/**
	 * 修改资讯
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/boutique/updateBoutiques.do")
	public void updateInformations(BoutiqueVo boutique,HttpServletRequest request, HttpServletResponse response) {
		BoutiqueVo Vo=boutiqueService.queryBoutiqueById(boutique.getInformation_id());
		UserVo loginUser=(UserVo) request.getSession().getAttribute("userInfo");
		boutique.setUpdated_by(loginUser.getUser_name());
		boutique.setUpdated_time(DateFill.getSysDate("yyyy-MM-dd HH:mm:ss"));
		//boutique.setStatus("2");
		//System.out.println("img:"+boutique.getTitle_img_url());
		//System.out.println("kais:"+Vo.getTitle_img_url());
		
		//解决修改时没有上传图片，保存后不要丢失图片，而是默认为之前的图片
		if(boutique.getTitle_img_url() == null || "".equals(boutique.getTitle_img_url())) {
			boutique.setTitle_img_url(Vo.getTitle_img_url());
		}
		boutique.setCreated_by(Vo.getCreated_by());
		boutique.setCreated_time(Vo.getCreated_time());
		Boolean rel= boutiqueService.updateBoutique(boutique);
		JSONObject oj = new JSONObject();
		oj.put("data", rel);
		writeResponseByJson(request, response, oj);
	}
	@RequestMapping(value = "/boutique/allUpdateBoutiques.do")
	public void allUpdateBoutiques(HttpServletRequest request, HttpServletResponse response) {
		String information_id=request.getParameter("information_id");
		String status=request.getParameter("status");
		String[] information_Ids = information_id.split(",");
		Boolean rel=false;
		for (int i = 0; i < information_Ids.length; i++) {
			BoutiqueVo Vo=boutiqueService.queryBoutiqueById(information_Ids[i]);
			Vo.setStatus(status);
			rel= boutiqueService.updateBoutique(Vo);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=UTF-8");
		}

		JSONObject oj = new JSONObject();
		oj.put("data", rel);
		writeResponseByJson(request, response, oj);
	}	
	/**
	 * 添加资讯
	 * @param information form对应的name属性，和BoutiqueVo boutique对象里的属性一样的名字，就可以序列化
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/boutique/saveBoutique.do")
	public void saveInformation(BoutiqueVo boutique,HttpServletRequest request, HttpServletResponse response) {
		UUID uuid = UUID.randomUUID();
		boutique.setInformation_id(uuid.toString());
		//System.out.println("img:"+boutique.getTitle_img_url());
		UserVo loginUser=(UserVo) request.getSession().getAttribute("userInfo");
		boutique.setCreated_by(loginUser.getUser_name());
		boutique.setUpdated_by(loginUser.getUser_name());
		boutique.setCreated_time(DateFill.getSysDate("yyyy-MM-dd HH:mm:ss"));
		boutique.setUpdated_time(DateFill.getSysDate("yyyy-MM-dd HH:mm:ss"));
		boutique.setStatus("1");
		Boolean rel= boutiqueService.saveBoutique(boutique);
		JSONObject oj = new JSONObject();
		oj.put("data", rel);
		writeResponseByJson(request, response, oj);
	}
}
