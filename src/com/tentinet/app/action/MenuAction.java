package com.tentinet.app.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tentinet.app.bean.UserVo;
import com.tentinet.app.bean.dto.MenuTreeBean;
import com.tentinet.app.service.MenuService;
import com.tentinet.app.util.ResonseUtils;

/**
 * 菜单管理
 * 每1个用户有N个角色，每1个角色对应N个菜单
 * 数据库表：用户：user_role,角色:role_id,角色菜单：menu_id,parmenu_id,role_id
 * 通过Dto对象来表达前台对应的关系，菜单以及孩子菜单
 */
@Controller
public class MenuAction extends BaseAction {
	@Autowired
	private MenuService menuService;
	
	@RequestMapping(value = "/menu/getRoleAndMenuByUserId.do")
	public void getRoleAndMenuByUserId(HttpServletRequest request,HttpServletResponse response) {
		// 得到当前用户
		UserVo user = (UserVo) request.getSession().getAttribute("userInfo");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", user.getUser_id());
		params.put("parMenuId", "-1");
		System.out.println("userid="+user.getUser_id());
		
		String url = request.getScheme()+"://"
				+request.getServerName()+":"+request.getServerPort()
				+request.getContextPath();
		/***
		 * 这个list是测试zTree插件的。
		 */
		List<String> list = new ArrayList<String>();
		/**
		 * 用一个集合存放所有的根菜单
		 */
		Collection<MenuTreeBean> listMenus = menuService.getMenusById(params);
		/**
		 * 初始化每个菜单下面的孩子菜单
		 */
		if(listMenus != null && !listMenus.isEmpty()) {
			for (MenuTreeBean menu : listMenus) {
				StringBuilder m = new StringBuilder();
				m.append("{");
				m.append("id:").append(menu.getId()).append(",");
				m.append("pId:").append(menu.getParMenuId()).append(",");
				m.append("name:").append("'").append(menu.getText()).append("'");
				m.append("}");
				list.add(m.toString());
				
				params.put("parMenuId", menu.getId());
				Collection<MenuTreeBean> childrenMenus = menuService.getMenusById(params);
				if (childrenMenus != null) {
					menu.setChildren(childrenMenus);
					for(MenuTreeBean child:childrenMenus) {
						StringBuilder s = new StringBuilder();
						s.append("{");
						s.append("id:").append(child.getId()).append(",");
						s.append("pId:").append(child.getParMenuId()).append(",");
						s.append("name:").append("'").append(child.getText()).append("'").append(",");
						s.append("url:").append("'").append(url+child.getMenuRUL()).append("'");
						s.append("}");
						list.add(s.toString());
					}
				}
			}
		}
		
		JSONObject oj = new JSONObject();
		oj.put("data", listMenus);
		oj.put("list", list);
		for(String s:list) {
			System.out.println(s);
		}
		ResonseUtils.writeResponseByJson(request, response, oj);
	}
	
	@RequestMapping(value = "/menu/checkbox.do")
	public void getCheckbox(HttpServletRequest request,HttpServletResponse response,Object[] designIds) {
		System.out.println("haha");
		if(designIds != null && designIds.length >0) {
			for(Object obj:designIds) {
				System.out.println(obj.toString());
			}
		}
		JSONObject oj = new JSONObject();
		oj.put("isOk", "ok");
		ResonseUtils.writeResponseByJson(request, response, oj);
	}
}