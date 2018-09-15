package com.tentinet.app.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tentinet.app.bean.LoadSelectObject;
import com.tentinet.app.bean.UserVo;
import com.tentinet.app.service.UserService;
import com.tentinet.app.util.MyMD5;
import com.tentinet.app.util.Page;
import com.tentinet.app.util.ResonseUtils;

/**
 * 用户管理
 */
@Controller
public class OperatorAction extends BaseAction {
	private Logger logger = Logger.getLogger(OperatorAction.class);

	@Autowired
	private UserService userService;

	/**
	 * 查询用户列表信息
	 * 前台页面pageSize=15&pageNo=，后台通过Page对象(属性名和前台的name一致)接收
	 */
	@RequestMapping(value = "/user/getEmplInfo.do")
	public void getEmplInfo(HttpServletRequest request,
			HttpServletResponse response, String user_id, String user_name,
			String status, String user_sex, Page page) {
		page.setAutoCount(true);// 自动设置分页
		HttpSession session = request.getSession();
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(user_id)) {
			//数据库模糊查询用like %%
			params.put("user_id", "%" + user_id + "%");
		}
		if (StringUtils.isNotEmpty(user_name)) {
			params.put("user_name", "%" + user_name + "%");
		}
		if (StringUtils.isNotEmpty(status) && !StringUtils.equals("0", status)) {
			params.put("status", status);
		}
		if (StringUtils.isNotEmpty(user_sex) && !user_sex.equals("0")) {
			params.put("user_sex", user_sex);
		}
		Integer userCount = userService.queryUserCount(params);
		page.setTotalCount(userCount);
		List<UserVo> listUser = userService.queryUserInfos(params);
		List<UserVo> newList = getPageList(listUser, page);
		JSONObject oj = new JSONObject();
		oj.put("data", newList);
		oj.put("total", listUser.size());
		ResonseUtils.writeResponseByJson(response, oj);
	}
	/**
	 * 加载<option>
	 */
	@RequestMapping(value = "/user/loadSelectData.do")
	public void loadSelectData(HttpServletRequest request,
			HttpServletResponse response, String user_id, String user_name,
			String status, String user_sex, Page page) {
		page.setAutoCount(true);// 自动设置分页
		HttpSession session = request.getSession();
		JSONObject oj = new JSONObject();
		List<LoadSelectObject> newList = new ArrayList<LoadSelectObject>();
		for(int i=0; i<9; i++) {
			LoadSelectObject lso = new LoadSelectObject(i+"",i+"");
			newList.add(lso);
		} 
		oj.put("data", newList);
		writeResponseByJson(request, response, oj);
	}

	/**
	 * 查询单个用户的信息
	 * 
	 * @param request
	 * @param response
	 * @param user_id
	 */

	@RequestMapping(value = "/user/showEmplInfo.do")
	public void showEmplInfo(HttpServletRequest request,
			HttpServletResponse response, String user_id) {
		logger.debug("查询单个员工的对象 user_id:" + user_id);
		long userId = Long.valueOf(user_id);
		UserVo user = userService.queryUserById(userId);
		JSONObject oj = new JSONObject();
		oj.put("data", user);
		ResonseUtils.writeResponseByJson(response, oj);
	}

	/**
	 * 更新用户信息
	 */
	@RequestMapping(value = "/user/updateEmpltInfo.do")
	public void updateEmpltInfo(HttpServletRequest request,
			HttpServletResponse response, String user_id, String user_name,
			String user_sex, String user_idcard, String user_birthday,
			String user_mail, String user_tentinet_mail, String user_weixin_no,
			String status, String user_pass, String user_role) {
		UserVo user = userService.queryUserById(Long.valueOf(user_id));
		user.setUser_name(user_name);
		user.setStatus(status);
		UserVo loginUser = (UserVo) request.getSession().getAttribute("userInfo");
		user.setUpdatedBy(String.valueOf(loginUser.getUser_id()));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		user.setUpadtedTime(format.format(new Date()));
		user.setUser_birthday(user_birthday);
		user.setUser_idcard(user_idcard);
		user.setUser_mail(user_mail);
		user.setUser_tentinet_mail(user_tentinet_mail);
		user.setUser_weixin_no(user_weixin_no);
		user.setUser_pass(user_pass);
		user.setUser_sex(user_sex);
		user.setUser_role(user_role);
		JSONObject oj = new JSONObject();
		boolean result = userService.updateUser(user);
		if (result) {
			oj.put("data", 1);
		} else {
			oj.put("data", 2);
		}
		ResonseUtils.writeResponseByJson(response, oj);
	}
	/**
	 * 删除一个或多个操作员/user,如果是批量删除，通过user_id字符串，用“,”号隔开，过来后split()成数组
	 * @param request
	 * @param response
	 * @param user_id
	 */
	@RequestMapping(value = "/user/delEmplInfo.do")
	public void delEmplInfo(HttpServletRequest request,
			HttpServletResponse response, String user_id) {
		JSONObject oj = new JSONObject();
		boolean result = true;
		UserVo userNow = (UserVo) request.getSession().getAttribute("userInfo");
		String[] userIds = user_id.split(",");
		//String[] userIds2 = StringUtils.split(user_id, ',');
		for (int i = 0; i < userIds.length; i++) {
			if (userNow.getUser_id() == Long.valueOf(userIds[i])) {
				oj.put("data", "不能删除当前使用的用户本身！");
				result = false;
			}
		}
		if (result) {
			for (int i = 0; i < userIds.length; i++) {
				UserVo user = userService.queryUserById(Long.valueOf(userIds[i]));
				result = userService.deleteUser(user);
			}
			if (result) {
				oj.put("data", 1);
			} else {
				oj.put("data", 2);
			}
		}
		ResonseUtils.writeResponseByJson(response, oj);
	}

	@RequestMapping(value = "/user/saveEmpltInfo.do")
	public void saveEmpltInfo(HttpServletRequest request,
			HttpServletResponse response, String user_name, String user_sex,
			String user_idcard, String user_birthday, String user_mail,
			String user_tentinet_mail, String user_weixin_no, String status,
			String user_pass, String user_role) {
		UserVo hasnameuser = userService.resaveUser(user_name);
		JSONObject oj = new JSONObject();
		if (hasnameuser != null) {
			oj.put("data", 3);
		} else {
			UserVo user = new UserVo();
			user.setUser_name(user_name);
			user.setStatus(status);
			UserVo loginUser = (UserVo) request.getSession().getAttribute("userInfo");
			user.setUpdatedBy(String.valueOf(loginUser.getUser_id()));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			user.setCreatedBy(String.valueOf(loginUser.getUser_id()));
			user.setCreatedTime(format.format(new Date()));
			user.setUpadtedTime(format.format(new Date()));
			user.setUser_birthday(user_birthday);
			user.setUser_idcard(user_idcard);
			user.setUser_mail(user_mail);
			user.setUser_tentinet_mail(user_tentinet_mail);
			user.setUser_weixin_no(user_weixin_no);
			user.setUser_sex(user_sex);
			user.setUser_pass(MyMD5.toMd5(user_pass));
			user.setUser_role(user_role);
			boolean result = userService.saveUser(user);
			if (result) {
				oj.put("data", 1);
			} else {
				oj.put("data", 2);
			}
		}
		ResonseUtils.writeResponseByJson(response, oj);
	}
}