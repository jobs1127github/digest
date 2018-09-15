package com.tentinet.app.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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

import com.tentinet.app.bean.WXUserVo;
import com.tentinet.app.bean.dto.WXUserDto;
import com.tentinet.app.excel.model.DownLoadWXUsers;
import com.tentinet.app.excel.utils.ExcelUtils;
import com.tentinet.app.service.WXUserService;
import com.tentinet.app.util.Page;
import com.tentinet.weixin.util.BASE64Utils;

/**
 * 用户管理
 * 
 * 
 */
@Controller
public class WXUserAction extends BaseAction {
	private Logger logger = Logger.getLogger(WXUserAction.class);

	@Autowired
	private WXUserService WXUserService;

	/**
	 * 查询用户列表信息
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/WXuser/getUserInfos.do")
	public void getEmplInfo(HttpServletRequest request,
			HttpServletResponse response, String wxno, String username,
			String status, String role, Page page)
					throws UnsupportedEncodingException {
		page.setAutoCount(true);// 直动设置分页
		HttpSession session = request.getSession();
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(wxno)) {
			params.put("wxno", "%" + wxno + "%");
		}
		if (StringUtils.isNotEmpty(username)) {
			params.put("username", "%" + username + "%");
		}
		if (StringUtils.isNotEmpty(status) && !StringUtils.equals("0", status)) {
			params.put("status", status);
		}
		
		if (StringUtils.isNotEmpty(role) && StringUtils.equals("A", role)) {
			params.put("role_02", role);
		}
		if (StringUtils.isNotEmpty(role) && !role.equals("A")) {
			params.put("role", role);
		}
		Integer userCount = WXUserService.queryWXUserCount(params);
		page.setTotalCount(userCount);
		List<WXUserDto> listUser = WXUserService.queryWXUserInfos(params);
		List<WXUserDto> newList = getPageList(listUser, page);
		if (newList != null) {
			for (int i = 0; i < newList.size(); i++) {
				WXUserDto dto = newList.get(i);
				String userName = getUserName(dto.getUsername());
				// userName=new String((userName).getBytes("ISO8859_1"),
				// "utf-8");
				dto.setUsername(userName);
			}
		}
		JSONObject oj = new JSONObject();
		oj.put("data", newList);
		oj.put("total", userCount);
		writeResponseByJson(request, response, oj);
		
	}
	/**
	 * 下载用户列表信息
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/WXuser/downLoadUserInfos.do")
	public void downLoadUserInfos(HttpServletRequest request,
			HttpServletResponse response, String wxno, String username,
			String status, String role, Page page)
			throws UnsupportedEncodingException {
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(wxno)) {
			params.put("wxno", "%" + wxno + "%");
		}
		if (StringUtils.isNotEmpty(username)) {
			params.put("username", "%" + username + "%");
		}
		if (StringUtils.isNotEmpty(status) && !StringUtils.equals("0", status)) {
			params.put("status", status);
		}

		if (StringUtils.isNotEmpty(role) && StringUtils.equals("A", role)) {
			params.put("role_02", role);
		}
		if (StringUtils.isNotEmpty(role) && !role.equals("A")) {
			params.put("role", role);
		}
		List<WXUserDto> listUser = WXUserService.queryWXUserInfos(params);
		List<Object> list = new ArrayList<Object>();
		for(WXUserDto d:listUser) {
			DownLoadWXUsers g = new DownLoadWXUsers();
			g.setAward_count(d.getAward_count());
			g.setCan_get_money(d.getCan_get_money());
			g.setCity(d.getCity());
			g.setCountry(d.getCountry());
			g.setGot_money(d.getGot_money());
			g.setOpenid(d.getOpenid());
			g.setProvince(d.getProvince());
			g.setRole(d.getRole());
			g.setSex(d.getSex());
			g.setUsername(d.getUsername());
			g.setPublicno(d.getPublicno());
			g.setStatus(d.getStatus());
			g.setCreated_time(d.getCreated_time());
			list.add(g);
		}
		String filePath = "D:\\wx_user_"+UUID.randomUUID().toString()+".xls";
		String[] title = new String[] { "openId", "用户", "省份", "地市","国家",
				"角色","性别","已提取金额","可提取金额","被打赏次数","状态","渠道","创建时间"};
		ExcelUtils.dataToExcel(filePath,"用户信息表", title, list);
		
		JSONObject oj = new JSONObject();
		oj.put("data", "/servlet/FileDownServlet?url="+filePath);
		writeResponseByJson(request, response, oj);
	}

	/**
	 * @author bond 查询用户列表信息
	 */
	@RequestMapping(value = "/WXuser/getUserInfoById.do")
	public void getUserInfoById(HttpServletRequest request,
			HttpServletResponse response, String openid) {
		HttpSession session = request.getSession();
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(openid)) {
			params.put("openid", openid);
		}
		List<WXUserDto> listUser = WXUserService.queryWXUserInfos(params);
		WXUserDto user = null;
		if (listUser != null) {

			user = listUser.get(0);
			user.setUsername(getUserName(user.getUsername()));

		}
		JSONObject oj = new JSONObject();
		oj.put("user", user);
		writeResponseByJson(request, response, oj);

	}

	/**
	 * 查询单个用户的信息
	 * 
	 * @author bond
	 * @param request
	 * @param response
	 * @param user_id
	 */

	@RequestMapping(value = "/WXuser/showEmplInfo.do")
	public void showEmplInfo(HttpServletRequest request,
			HttpServletResponse response, String openid) {
		WXUserVo user = WXUserService.queryWXUserById(openid);
		user.setUsername(getUserName(user.getUsername()));
		JSONObject oj = new JSONObject();
		oj.put("data", user);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");

		try {
			response.getWriter().write(oj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 专家审批
	 */
	@RequestMapping(value = "/WXuser/updateWXuser.do")
	public void updateWXuser(HttpServletRequest request,
			HttpServletResponse response, String openid, String role,
			String expert) {
		WXUserVo user = WXUserService.queryWXUserById(openid);
		user.setRole(role);
		user.setExpert(expert);
		boolean data = WXUserService.updaueWXUser(user);
		JSONObject oj = new JSONObject();
		oj.put("data", data);
		writeResponseByJson(request, response, oj);

	}

	@RequestMapping(value = "/WXuser/updateEmplGroup.do")
	public void updateEmplGroup(HttpServletRequest request,
			HttpServletResponse response, String openid, String group_id) {
		WXUserVo user = WXUserService.queryWXUserById(openid);
		user.setGroup_id(group_id);
		boolean data = WXUserService.updaueWXUserGroup(user);
		JSONObject oj = new JSONObject();
		oj.put("data", data);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");

		try {
			response.getWriter().write(oj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getUserName(String str) {
		return BASE64Utils.getFromBASE64(str);
	}

	public static void main(String[] args) {
		System.out.println(BASE64Utils.getFromBASE64("Qm9uZA=="));

		// System.out.println(BASE64Utils.getFromBASE64("5YWw6aG65bmz") );
	}

}
