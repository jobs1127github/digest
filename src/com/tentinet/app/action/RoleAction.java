package com.tentinet.app.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tentinet.app.bean.MenuVo;
import com.tentinet.app.bean.RoleMenuVo;
import com.tentinet.app.bean.RoleVo;
import com.tentinet.app.bean.UserVo;
import com.tentinet.app.bean.dto.MenuTreeBean;
import com.tentinet.app.service.MenuService;
import com.tentinet.app.service.RoleService;
import com.tentinet.app.util.Page;
import com.tentinet.app.util.ResonseUtils;

/**
 * 角色管理
 * 
 * @author Jobs1127 2015－08－07
 */
@Controller
public class RoleAction extends BaseAction {

	@Autowired
	private RoleService roleService;

	@Autowired
	private MenuService menuService;

	/**
	 * 角色列表
	 * @param request
	 * @param response
	 * @param role_id
	 * @param role_name
	 * @param role_status
	 * @param page
	 */
	@RequestMapping(value = "/role/queryRolsInfos.do")
	public void queryRolsInfos(HttpServletRequest request,
			HttpServletResponse response, String role_id, String role_name,
			String role_status, Page page) {
		page.setAutoCount(true);// 直动设置分页
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(role_id)) {
			params.put("role_id", "%" + role_id + "%");
		}
		if (StringUtils.isNotEmpty(role_name)) {
			params.put("role_name", "%" + role_name + "%");
		}
		if (StringUtils.isNotEmpty(role_status)) {
			if (!StringUtils.equals("0", role_status)) {
				params.put("role_status", role_status);
			}
		}
		Integer count = roleService.queryRoleCounts(params);
		List<RoleVo> roles = roleService.queryRoleInfos(params);
		List<RoleVo> newList = getPageList(roles, page);
		page.setTotalCount(count);
		JSONObject oj = new JSONObject();
		oj.put("data", newList);
		oj.put("total", count);
		writeResponseByJson(request, response, oj);
	}

	/**
	 * 查询所有的菜单，用于新建角色时，初始化菜单树
	 */
	@RequestMapping(value = "/role/queryAllMenus.do")
	public void queryAllMenus(HttpServletRequest request,
			HttpServletResponse response) {
		List<MenuVo> menus = menuService.queryMenusInfos();
		JSONObject oj = new JSONObject();
		oj.put("data", menus);
		writeResponseByJson(request, response, oj);
	}

	/**
	 * getMenuOfRoleByRoleId
	 */

	@RequestMapping(value = "/role/getMenuOfRoleByRoleId.do")
	public void getMenuOfRoleByRoleId(HttpServletRequest request,
			HttpServletResponse response, String roleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		// params.put("parmenuId", "-1");
		List<MenuTreeBean> list = menuService.getMenuOfRoleByRoleId(params);
		JSONObject oj = new JSONObject();
		oj.put("data", list);
		writeResponseByJson(request, response, oj);
	}

	/**
	 * 保存角色并重新设定角色的权限 /role/updateRoleAndRoleMenu.do
	 */

	@RequestMapping(value = "/role/updateRoleAndRoleMenu.do")
	public void updateRoleAndRoleMenu(HttpServletRequest request,
			HttpServletResponse response, String roleId, String menuIdStr,
			RoleVo role) {
		// System.out.println(role.getRole_name());
		System.out.println(menuIdStr);
		// 得到当前登 录的用户
		UserVo loginUser = (UserVo) request.getSession().getAttribute(
				"userInfo");
		RoleVo oldRole = roleService.getRoleInfoById(roleId);
		// role.setRole_id(roleId);
		oldRole.setUpdated_by(String.valueOf(loginUser.getUser_id()));
		oldRole.setUpdated_time(dateToStr(new Date()));
		oldRole.setRole_name(role.getRole_name());
		oldRole.setRole_status(role.getRole_status());
		oldRole.setMemo(role.getMemo());
		// 先保存角色信息
		boolean isResult = false;
		isResult = roleService.updateRoleInfos(oldRole);

		// 删除当前角色所有的权限 2,21,4,41,42,1,11,12,13,3,31,32,33,
		List<String> menuIdList = buildStrToList(menuIdStr);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		isResult = roleService.deleteRoleMenuByRoleId(params);

		// 加入日最新角色权限
		if (CollectionUtils.isNotEmpty(menuIdList)) {
			for (int i = 0; i < menuIdList.size(); i++) {
				RoleMenuVo roleMenu = new RoleMenuVo();
				roleMenu.setCraeted_by(String.valueOf(loginUser.getUser_id()));
				roleMenu.setUpdated_by(String.valueOf(loginUser.getUser_id()));
				roleMenu.setCreated_time(dateToStr(new Date()));
				roleMenu.setUpdated_time(dateToStr(new Date()));
				roleMenu.setRole_id(roleId);
				roleMenu.setMenu_id(menuIdList.get(i));
				roleMenu.setEnable("Y");
				roleService.saveRoleMenuInfos(roleMenu);
			}
		}
		JSONObject oj = new JSONObject();
		if (isResult) {
			oj.put("data", 1);
		} else {
			oj.put("data", 2);
		}
		ResonseUtils.writeResponseByJson(response, oj);
	}

	/**
	 * /role/saveRoleAndRoleMenu.do 保存角色与角色对应的菜单权限
	 */
	@RequestMapping(value = "/role/saveRoleAndRoleMenu.do")
	public void saveRoleAndRoleMenu(HttpServletRequest request,
			HttpServletResponse response, String menuIdStr, RoleVo role) {
		// 先查询当前用户是否已存在
		RoleVo dbRole = roleService.getRoleInfoById(role.getRole_id());
		JSONObject oj = new JSONObject();
		if (dbRole != null) {// 说明DB中已存在此角色，不用在进行保存操作
			oj.put("data", 1);
		} else {// 说明DB中不存在此角色可以进行保存操作
			UserVo loginUser = (UserVo) request.getSession().getAttribute("userInfo");
			role.setCreated_by(String.valueOf(loginUser.getUser_id()));
			role.setCreated_time(dateToStr(new Date()));
			role.setUpdated_by(String.valueOf(loginUser.getUser_id()));
			role.setUpdated_time(dateToStr(new Date()));
			boolean isresult = roleService.saveRoleInfos(role);
			// 保存角色的权限 保存角色菜单 1个角色对应N个菜单
			List<String> menuIdList = buildStrToList(menuIdStr);
			if (CollectionUtils.isNotEmpty(menuIdList)) {
				for (int i = 0; i < menuIdList.size(); i++) {
					RoleMenuVo roleMenu = new RoleMenuVo();
					roleMenu.setCraeted_by(String.valueOf(loginUser.getUser_id()));
					roleMenu.setUpdated_by(String.valueOf(loginUser.getUser_id()));
					roleMenu.setCreated_time(dateToStr(new Date()));
					roleMenu.setUpdated_time(dateToStr(new Date()));
					roleMenu.setRole_id(role.getRole_id());
					roleMenu.setMenu_id(menuIdList.get(i));
					roleMenu.setEnable("Y");
					roleService.saveRoleMenuInfos(roleMenu);
				}
			}
			if (isresult) {
				oj.put("data", 3);
			} else {
				oj.put("data", 2);
			}
		}
		ResonseUtils.writeResponseByJson(response, oj);
	}

	/**
	 * 将String转换成list
	 * 
	 * @param str
	 * @return
	 */
	private static List<String> buildStrToList(String str) {
		List<String> list = new ArrayList<String>();
		String[] bytes = str.split(",");
		for (int i = 0; i < bytes.length; i++) {
			list.add(bytes[i]);
		}
		return list;
	}

	/**
	 * /role/delRole,do 角色的删除只作逻辑删除
	 */
	@RequestMapping(value = "/role/delRole.do")
	public void delRole(HttpServletRequest request,
			HttpServletResponse response, String roleId, String delete_type) {

		Map<String, Object> params = new HashMap<String, Object>();
		boolean isResult = false;
		if (StringUtils.isNotEmpty(delete_type)) {// 批量删除
			List<String> roldIds = buildStrToList(roleId);
			for (int i = 0; i < roldIds.size(); i++) {
				RoleVo Role = roleService.getRoleInfoById(roldIds.get(i));
				isResult = roleService.deleteRoleById(Role);
			}
			isResult = roleService.deleteBatchRoleInfos(params);
		} else {// 删除单个
			RoleVo Role = roleService.getRoleInfoById(roleId);
			isResult = roleService.deleteRoleById(Role);
		}
		JSONObject oj = new JSONObject();
		if (isResult) {
			oj.put("data", 1);
		} else {
			oj.put("data", 1);
		}
		ResonseUtils.writeResponseByJson(response, oj);
	}

	/**
	 * 加载角色的下拉列表 /role/loadRolesinitDatas.do
	 * */
	@RequestMapping(value = "/role/loadRolesinitDatas.do")
	public void loadRolesinitDatas(HttpServletRequest request,
			HttpServletResponse response) {
		List<RoleVo> list = null;
		list = roleService.loadRolesinitDatas();
		JSONObject oj = new JSONObject();
		oj.put("data", list);
		writeResponseByJson(request, response, oj);
	}
}