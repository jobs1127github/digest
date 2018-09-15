package com.tentinet.app.service;

import java.util.List;
import java.util.Map;

import com.tentinet.app.bean.UserVo;

public interface UserService {
	/**
	 * 根据邮箱人账号进行登录
	 * 
	 * @param userName
	 * @return
	 */
	public UserVo loginUser(String userName, String userPass);
	/**
	 * 保存用户
	 * 
	 * @param user
	 * @return
	 */
	public boolean saveUser(UserVo user);
	/**
	 * 修改用户
	 * 
	 * @param user
	 * @return
	 */
	public boolean updateUser(UserVo user);
	/**
	 * 查询单个用户
	 * @param userId
	 * @return
	 */
	public UserVo queryUserById(long userId);
	/**
	 * 查询用户列表信息
	 * 
	 * @param params
	 * @return
	 */
	public List<UserVo> queryUserInfos(Map<String, Object> params);

	/**
	 * 删除用户
	 * 
	 * @param user
	 * @return
	 */
	public boolean deleteUser(UserVo user);

	/**
	 * 查询当前用户的数量
	 * 
	 * @param params
	 * @return
	 */
	public Integer queryUserCount(Map<String, Object> params);

	/**
	 * 查询当前会员的数量
	 * 
	 * @param params
	 * @return
	 */
	public Integer queryOnlineUserCount(Map<String, Object> params);

	public UserVo resaveUser(String user_name);
}
