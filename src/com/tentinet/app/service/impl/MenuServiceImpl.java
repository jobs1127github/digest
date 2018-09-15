package com.tentinet.app.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tentinet.app.bean.MenuVo;
import com.tentinet.app.bean.dto.MenuTreeBean;
import com.tentinet.app.service.ICommonIbatiesBaseDao;
import com.tentinet.app.service.MenuService;

@Service(value = "menuService")
public class MenuServiceImpl implements MenuService {

	@Autowired
	private ICommonIbatiesBaseDao commonBatiesBaseDao;

	@SuppressWarnings("unchecked")
	@Override
	public Collection<MenuTreeBean> getMenusById(Map<String, Object> params) {
		Collection<MenuTreeBean> list = null;
		try {
			
			list = (Collection<MenuTreeBean>) commonBatiesBaseDao.queryForList(
					"system.userRoleAndMenuByUserId", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuVo> queryMenusInfos() {
		List<MenuVo> menus = null;
		try {
			menus = (List<MenuVo>) commonBatiesBaseDao.queryForList(
					"system.queryMenusInfos", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menus;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuTreeBean> getMenuOfRoleByRoleId(Map<String, Object> params) {
		List<MenuTreeBean> list = null;
		try {
			list = (List<MenuTreeBean>) commonBatiesBaseDao.queryForList(
					"system.getMenuOfRoleByRoleId", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
