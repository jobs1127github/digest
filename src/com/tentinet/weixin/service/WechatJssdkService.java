package com.tentinet.weixin.service;

import java.io.IOException;
import java.util.Map;

import com.tentinet.weixin.entity.RedPacket;
import com.tentinet.weixin.entity.TemplateMsgContent;
import com.tentinet.weixin.entity.WechatUser;

/**
 * 与微信服务平台对接的接口,从此接口与微信进行对接
 * 
 */
public interface WechatJssdkService {

	/**
	 * 从微信得到请求的评证token
	 * 
	 * @return
	 */
	public String getToken();

	/**
	 * 从微信平台得到用户的信息
	 * 
	 * @return
	 */
	public WechatUser getWechatUserInfo(String openid);

	public Map<String, Object> getPageAccessToken(String code);

	public WechatUser getPageWechatUser(String token_url, String openid);

	/**
	 * 发送模板消息
	 * 
	 * @param msg
	 * @return
	 */
	public boolean sendTemplateMsg(TemplateMsgContent msg);

	/**
	 * 发送红包
	 * 
	 * @param redPacket
	 * @return
	 */
	public boolean sendRedPacket(RedPacket redPacket);

	public Map<String, String> sign(String jsapi_ticket, String url);

	public String getTicket() throws IOException;
}
