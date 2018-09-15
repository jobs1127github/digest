package com.tentinet.app.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.tentinet.app.bean.MenuVo;
import com.tentinet.app.bean.dto.MenuTreeBean;

public interface MenuService {
	
	/**
	 * 根据用户的id提到对应的菜单权限树
	 * @param params
	 * @return
	 */
	public Collection<MenuTreeBean> getMenusById(Map<String,Object>params);
	
    /**
     * 查询所有的菜单构建树
     * @return
     */
	public List<MenuVo> queryMenusInfos();
   
	/**
	 * getMenuOfRoleByRoleId
	 */
	public List<MenuTreeBean> getMenuOfRoleByRoleId(Map<String,Object>params);
}
