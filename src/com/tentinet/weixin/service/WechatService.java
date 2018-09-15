package com.tentinet.weixin.service;

import java.io.IOException;
import java.util.Map;

import com.tentinet.weixin.entity.WechatUser;
import com.tentinet.weixin.util.wechat.InMessage;
import com.tentinet.weixin.util.wechat.OutMessage;

/**
 * 微信公众平台管理
 */
public interface WechatService {
	/**
	 * 文字内容的消息处理
	 * @param msg 接收信息
	 * @param wechatUser 用户信息
	 * @return 回复信息O
	 */
	public OutMessage textTypeMsg(InMessage msg, WechatUser wechatUser);
	
	/**
	 * 地理位置类型的消息处理
	 * @param msg 接收信息
	 * @param wechatUser 用户信息
	 * @return 回复信息
	 */
	public OutMessage locationTypeMsg(InMessage msg, WechatUser wechatUser);
	
	/**
	 * 图片类型的消息处理
	 * @param msg 接收信息
	 * @param wechatUser 用户信息
	 * @return 回复信息
	 */
	public OutMessage imageTypeMsg(InMessage msg, WechatUser wechatUser);
	/**
	 * 链接类型的消息处理
	 * @param msg 接收信息
	 * @param wechatUser 用户信息
	 * @return 回复信息
	 */
	public OutMessage linkTypeMsg(InMessage msg, WechatUser wechatUser);
	
	/**
	 * 事件类型的消息处理
	 * @param msg 接收信息
	 * @param wechatUser 用户信息
	 * @return 回复信息
	 * @throws IOException 
	 */
	public OutMessage eventTypeMsg(InMessage msg, WechatUser wechatUser) throws IOException;

	public Map<String, Object> getPageAccessToken(String code) throws IOException;
	
	public WechatUser getPageWechatUser(String accessToken, String openId, Integer source) throws IOException;
	
	public WechatUser getWechatUser(String accessToken, String openId, Integer source) throws IOException;
	/**
	 * 得到Token
	 * @return
	 * @throws IOException
	 */
	 public String getAccessToken() throws IOException;
	
	 /**
	  * 当前用户是否关注了该公众号
	  * @param openid
	  * @return
	  */
	 public boolean isSubscribeWX(String openid);
	
	 

}
