package com.tentinet.app.service;

import java.util.List;
import java.util.Map;

import com.tentinet.app.bean.RoleMenuVo;
import com.tentinet.app.bean.RoleVo;

public interface RoleService {

	/**
	 * 查询所有角色信息
	 * 
	 * @param params
	 * @return
	 */
	public List<RoleVo> queryRoleInfos(Map<String, Object> params);

	/**
	 * 查询角色数量
	 * 
	 * @param params
	 * @return
	 */
	public Integer queryRoleCounts(Map<String, Object> params);

	/**
	 * 保存角色信息
	 * 
	 * @param role
	 * @return
	 */
	public boolean updateRoleInfos(RoleVo role);

	/**
	 * 删除现有的角色权限并更改成最新的角色权限
	 * 
	 * @param params
	 * @return
	 */
	public boolean deleteRoleMenuByRoleId(Map<String, Object> params);

	/**
	 * 保存角色权限
	 * 
	 * @return
	 */
	public boolean saveRoleMenuInfos(RoleMenuVo roleMenu);

	/**
	 * 得到单个用户角色的信息
	 */
	public RoleVo getRoleInfoById(String roldId);

	/**
	 * 新建角色
	 */
	public boolean saveRoleInfos(RoleVo role);

	/**
	 * 逻辑删除角色的信息
	 */
	public boolean deleteRoleById(RoleVo Role);
	
	/**
	 * 批量删除角色的信息
	 */
	public boolean deleteBatchRoleInfos(Map<String,Object>params);
	
	/**
	 * 加载所有有效的角色下拉
	 */
    public List<RoleVo> loadRolesinitDatas();
}
