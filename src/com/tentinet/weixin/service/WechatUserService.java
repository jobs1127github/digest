package com.tentinet.weixin.service;

import com.tentinet.app.bean.WXUserVo;

public interface WechatUserService {
	
	/**
	 * 查找微信用户
	 * @param openId
	 * @return
	 */
    public WXUserVo findUserById(String openId);
    
    /**
     * 修改用户信息
     * @param wxUserVo
     * @return
     */
    public boolean updateUser(WXUserVo wxUserVo);
}
