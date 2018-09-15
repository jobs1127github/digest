package com.tentinet.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tentinet.app.bean.UserGroupVo;
import com.tentinet.app.bean.UserVo;
import com.tentinet.app.service.ICommonHibernateBaseDao;
import com.tentinet.app.service.ICommonIbatiesBaseDao;
import com.tentinet.app.service.UserGroupService;

@Service(value="userGroupService")
public class UserGroupServiceImpl implements UserGroupService {

	@Autowired
	private ICommonIbatiesBaseDao commonBatiesBaseDao;
	
	@Autowired
	private ICommonHibernateBaseDao commonHibernateBaseDao;
	
	
	@Override
	public boolean saveGroup(UserGroupVo group) {
		boolean result=false;
		try {
			commonHibernateBaseDao.save(group);
			result=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean updateGroup(UserGroupVo group) {
		boolean result=false;
		try {
			commonHibernateBaseDao.update(group);
			result=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	@Override
	public UserGroupVo queryGroupById(String groupId) {
		UserGroupVo group=null;
		try {
			group=commonHibernateBaseDao.getEntityByStringId(UserGroupVo.class, groupId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return group;
	}

	@Override
	public List<UserGroupVo> queryGroupInfos(Map<String, Object> params) {
		List<UserGroupVo> list=null;
		list = (List<UserGroupVo>) commonBatiesBaseDao.queryForList("group.queryGroupList", params);
		return list;
	}

	@Override
	public boolean deleteGroup(String groupId) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("group_id", groupId);
		boolean result=false;
		try {
			commonBatiesBaseDao.updateObject("group.deleteGroup", params);
			result=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Integer queryGroupCount(Map<String, Object> params) {
		Integer count=0;
		try {
			count=commonBatiesBaseDao.queryForCount("group.queryGroupCount", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

}
