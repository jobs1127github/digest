package com.tentinet.app.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tentinet.app.bean.RoleMenuVo;
import com.tentinet.app.bean.RoleVo;
import com.tentinet.app.service.ICommonHibernateBaseDao;
import com.tentinet.app.service.ICommonIbatiesBaseDao;
import com.tentinet.app.service.RoleService;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private ICommonIbatiesBaseDao commonBatiesBaseDao;

	@Autowired
	private ICommonHibernateBaseDao commonHibernateBaseDao;

	@Override
	public List<RoleVo> queryRoleInfos(Map<String, Object> params) {
		List<RoleVo> roles = null;
		try {
			roles = (List<RoleVo>) commonBatiesBaseDao.queryForList(
					"role.queryRoleInfos", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roles;
	}

	@Override
	public Integer queryRoleCounts(Map<String, Object> params) {
		Integer count = 0;
		try {
			count = commonBatiesBaseDao.queryForCount("role.queryRoleCounts",
					params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public boolean updateRoleInfos(RoleVo role) {
		boolean isResult = false;
		try {
			commonHibernateBaseDao.update(role);
			isResult = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isResult;
	}

	@Override
	public boolean deleteRoleMenuByRoleId(Map<String, Object> params) {

		boolean isResult = false;
		try {
			commonBatiesBaseDao.batchDeleteObjects(
					"role.deleteRoleMenuByRoleId", params);
			isResult = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isResult;
	}

	@Override
	public boolean saveRoleMenuInfos(RoleMenuVo roleMenu) {
		boolean isResult = false;
		try {
			commonHibernateBaseDao.save(roleMenu);
			isResult = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isResult;
	}

	@Override
	public RoleVo getRoleInfoById(String roldId) {
		RoleVo role = null;
		try {
			role = commonHibernateBaseDao.getEntityByStringId(RoleVo.class,
					roldId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return role;
	}

	@Override
	public boolean saveRoleInfos(RoleVo role) {
		boolean isResult = false;
		try {
			commonHibernateBaseDao.save(role);
			isResult = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isResult;
	}

	@Override
	public boolean deleteRoleById(RoleVo Role) {
		boolean isResult = false;
		try {
			commonHibernateBaseDao.delete(Role);
			isResult = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isResult;
	}

	@Override
	public boolean deleteBatchRoleInfos(Map<String, Object> params) {
		boolean isResult = false;
		try {
			commonBatiesBaseDao.updateObject("role.deleteBatchRoleInfos",
					params);
			isResult = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleVo> loadRolesinitDatas() {
		List<RoleVo> list = null;
		try {
			list = (List<RoleVo>) commonBatiesBaseDao.queryForList(
					"role.loadRolesinitDatas", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
