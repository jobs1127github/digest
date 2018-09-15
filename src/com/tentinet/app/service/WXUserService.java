package com.tentinet.app.service;

import java.util.List;
import java.util.Map;

import com.tentinet.app.bean.WXUserVo;
import com.tentinet.app.bean.dto.WXUserDto;

public interface WXUserService{
	
 /**
    *  查询单个用户
    * @param userId
    * @return
    */
   public WXUserVo queryWXUserById(String openid);
    /**
     * 查询用户列表信息
     * @param params
     * @return
     */
   public List<WXUserDto> queryWXUserInfos(Map <String,Object> params);

   /**
    *   查询当前用户的数量
    * @param params
    * @return
    */
   public Integer queryWXUserCount(Map<String,Object>params);
   
   public Boolean updaueWXUser(WXUserVo vo);
   /*
    * 修改用户群组
    */
   public Boolean updaueWXUserGroup(WXUserVo vo);

}
