package com.tentinet.weixin.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tentinet.app.bean.WXUserVo;
import com.tentinet.app.service.ICommonIbatiesBaseDao;
import com.tentinet.weixin.service.WechatUserService;
/**
 * @Service服务层
 * @author jobs1127
 * 接入ibaties Dao与DB交流
 */
@Service(value="wechatUserService")
public class WechatUserServiceImpl implements WechatUserService{
	
	@Autowired
	private ICommonIbatiesBaseDao commonBatiesBaseDao;

	@Override
	public WXUserVo findUserById(String openId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("openid", openId);
		return (WXUserVo) this.commonBatiesBaseDao.queryForObject("WXUser.queryWXUserInfos",params);
	}

	@Override
	public boolean updateUser(WXUserVo wxUserVo) {
		
		//return this.commonBatiesBaseDao.updateObject("WXUser.updateWXUserInfos", params);
		return false;
	}

}
