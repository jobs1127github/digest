package com.tentinet.app.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tentinet.app.bean.WXUserVo;
import com.tentinet.app.bean.dto.WXUserDto;
import com.tentinet.app.service.ICommonHibernateBaseDao;
import com.tentinet.app.service.ICommonIbatiesBaseDao;
import com.tentinet.app.service.WXUserService;

@Service(value="WXuserService")
public class WXUserServiceImpl implements WXUserService {
	
	@Autowired
	private ICommonIbatiesBaseDao commonBatiesBaseDao;
	
	@Autowired
	private ICommonHibernateBaseDao commonHibernateBaseDao;
	


	@Override
	public WXUserVo queryWXUserById(String openid) {
		WXUserVo user=null;
		try {
			user=commonHibernateBaseDao.getEntityByStringId(WXUserVo.class, openid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public List<WXUserDto> queryWXUserInfos(Map<String, Object> params) {
		List<WXUserDto> listUser=null;
		try {
			listUser=(List<WXUserDto>) commonBatiesBaseDao.queryForList("WXUser.queryWXUserInfos", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listUser;
	}

	@Override
	public Integer queryWXUserCount(Map<String, Object> params) {
		Integer count=0;
		try {
			count=commonBatiesBaseDao.queryForCount("WXUser.queryWXUserCount", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public Boolean updaueWXUserGroup(WXUserVo vo){
		System.out.println("update updaueWXUserGroup!");
		return commonHibernateBaseDao.update(vo);	
	}

	@Override
	public Boolean updaueWXUser(WXUserVo vo) {
		return commonHibernateBaseDao.update(vo);
	}

}
