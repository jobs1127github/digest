package com.tentinet.app.service;

import java.util.List;
import java.util.Map;

import com.tentinet.app.bean.UserGroupVo;

public interface UserGroupService{
	
   
   /**
    *  保存群组
    * @param user
    * @return
    */
   public boolean saveGroup(UserGroupVo group);
   /**
    * 修改群组
    * @param user
    * @return
    */
   public boolean updateGroup(UserGroupVo group);
   /**
    *  查询单个群组
    * @param userId
    * @return
    */
   public UserGroupVo queryGroupById(String groupId);
    /**
     * 查询群组列表信息
     * @param params
     * @return
     */
   public List<UserGroupVo> queryGroupInfos(Map <String,Object> params);
   /**
    * 删除群组
    * @param user
    * @return
    */
   public boolean deleteGroup(String groupId);
   
   /**
    *   查询当前群组的数量
    * @param params
    * @return
    */
   public Integer queryGroupCount(Map<String,Object>params);

}
