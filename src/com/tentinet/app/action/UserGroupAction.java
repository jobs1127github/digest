package com.tentinet.app.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.tentinet.app.bean.UserGroupVo;
import com.tentinet.app.service.UserGroupService;
import com.tentinet.app.util.Page;

/**
 * 群组管理
 * 
 * @author bond
 */
@Controller
public class UserGroupAction extends BaseAction {

	@Autowired
	private UserGroupService userGroupService;

	@RequestMapping(value = "/group/queryGroupList.do")
	public void queryRolsInfos(HttpServletRequest request,
			HttpServletResponse response, String group_id, String group_name,
			Page page) {
		page.setAutoCount(true);// 直动设置分页
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(group_id)) {
			params.put("group_id", group_id);
		}
		if (StringUtils.isNotEmpty(group_name)) {
			params.put("group_name", "%" + group_name + "%");
		}
		Integer count = userGroupService.queryGroupCount(params);
		List<UserGroupVo> list = userGroupService.queryGroupInfos(params);
		List<UserGroupVo> newList = getPageList(list, page);
		page.setTotalCount(count);
		JSONObject oj = new JSONObject();
		oj.put("data", newList);
		oj.put("total", count);
		writeResponseByJson(request, response, oj);
	}

	@RequestMapping(value = "/group/queryGroupById.do")
	public void queryGroupById(HttpServletRequest request,
			HttpServletResponse response, String group_id) {
		UserGroupVo vo = userGroupService.queryGroupById(group_id);
		JSONObject oj = new JSONObject();
		oj.put("data", vo);
		writeResponseByJson(request, response, oj);
	}

	/**
	 * 加载群组的下拉列表
	 * 
	 * */
	@RequestMapping(value = "/group/loadGroupsInit.do")
	public void loadGroupsInit(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		List<UserGroupVo> list = userGroupService.queryGroupInfos(params);
		JSONObject oj = new JSONObject();
		oj.put("data", list);
		writeResponseByJson(request, response, oj);
	}

	@RequestMapping(value = "/group/saveGroup.do")
	public void saveGroup(UserGroupVo group, HttpServletRequest request,
			HttpServletResponse response) {
		UUID uuid = UUID.randomUUID();
		group.setGroup_id(uuid.toString());
		Boolean data = userGroupService.saveGroup(group);
		JSONObject oj = new JSONObject();
		oj.put("data", data);
		writeResponseByJson(request, response, oj);
	}

	@RequestMapping(value = "/group/updateGroup.do")
	public void updateGroup(UserGroupVo group, HttpServletRequest request,
			HttpServletResponse response) {
		Boolean data = userGroupService.updateGroup(group);
		JSONObject oj = new JSONObject();
		oj.put("data", data);
		writeResponseByJson(request, response, oj);
	}

	@RequestMapping(value = "/group/deleteGroup.do")
	public void deleteGroup(String groupIds, HttpServletRequest request,
			HttpServletResponse response) {
		String[] groupId = groupIds.split(",");
		Boolean data = false;
		for (int i = 0; i < groupId.length; i++) {
			data = userGroupService.deleteGroup(groupId[i]);
		}
		JSONObject oj = new JSONObject();
		oj.put("data", data);
		writeResponseByJson(request, response, oj);
	}
}
