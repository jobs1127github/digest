package com.tentinet.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tentinet.app.bean.UserVo;
import com.tentinet.app.service.ICommonHibernateBaseDao;
import com.tentinet.app.service.ICommonIbatiesBaseDao;
import com.tentinet.app.service.UserService;

/**
 * 服务层,value标识该Bean，默认用该服务层的类名，首字母小写
 * 
 * @author jobs1127
 * 
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

	/**
	 * 服务层自动转载DAO层对象，这里定义接口，多态，接口的编程模式 ibaties的DAO
	 */
	@Autowired(required=true)
	@Qualifier(value="commonBatiesBaseDao")
	private ICommonIbatiesBaseDao commonBatiesBaseDao;
	/**
	 * 服务层自动转载DAO层对象，这里定义接口，多态，接口的编程模式 hibernate的DAO
	 */
	@Autowired(required=true)
	@Qualifier(value="commonHibernateBaseDao")
	private ICommonHibernateBaseDao commonHibernateBaseDao;

	/**
	 * 登录
	 */
	@Override
	public UserVo loginUser(String userName, String userPass) {
		UserVo user = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);
		params.put("userPass", userPass);
		System.out.println("loginUser_commonBatiesBaseDao="+commonBatiesBaseDao);
		try {
			/**
			 * ibaties的xml配置文件<sqlMap namespace="system">，
			 * commonBatiesBaseDao底层操作数据的增删改查是通过sqlMapClient对象完成的，sqlMap用id为key,
			 * select为value，形成key-value键值对的sqlMap。
			 */
			user = (UserVo) commonBatiesBaseDao.queryForObject("system.loginUser", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public boolean saveUser(UserVo user) {
		boolean result = false;
		try {
			commonHibernateBaseDao.save(user);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean updateUser(UserVo user) {
		boolean result = false;
		try {
			commonHibernateBaseDao.update(user);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public UserVo queryUserById(long userId) {
		UserVo user = null;
		try {
			user = commonHibernateBaseDao.getEntityByLongId(UserVo.class,
					userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserVo> queryUserInfos(Map<String, Object> params) {
		List<UserVo> listUser = null;
		try {
			listUser = (List<UserVo>) commonBatiesBaseDao.queryForList(
					"system.queryUserInfos", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listUser;
	}

	@Override
	public boolean deleteUser(UserVo user) {
		boolean result = false;
		try {
			commonHibernateBaseDao.delete(user);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Integer queryUserCount(Map<String, Object> params) {
		Integer count = 0;
		try {
			count = commonBatiesBaseDao.queryForCount("system.queryUserCount",params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Integer queryOnlineUserCount(Map<String, Object> params) {
		Integer count = 0;
		try {
			count = commonBatiesBaseDao.queryForCount(
					"system.queryOnlineUserCount", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public UserVo resaveUser(String user_name) {
		UserVo user = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", user_name);
		try {
			user = (UserVo) commonBatiesBaseDao.queryForObject(
					"system.loginUser", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

}
