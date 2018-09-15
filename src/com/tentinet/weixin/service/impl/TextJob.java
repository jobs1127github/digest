package com.tentinet.weixin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tentinet.app.bean.UserCharLogVo;
import com.tentinet.weixin.service.PrecessService;
import com.tentinet.weixin.service.WXCMSClientService;
import com.tentinet.weixin.util.DateUtil;
import com.tentinet.weixin.util.wechat.Articles;
import com.tentinet.weixin.util.wechat.InMessage;
import com.tentinet.weixin.util.wechat.OutMessage;

/**
 * 文本消息处理类
 * @author jobs1127
 */
@Service(value = "textJob")
public class TextJob implements PrecessService {
	
	@Autowired
	private WXCMSClientService wXCMSClientService;

	@Override
	public OutMessage doJob(InMessage msg) {
		OutMessage oms=null;
		String content=msg.getContent();//得到用户输入的内容
		System.out.println("文本消息用户输入的内容:"+content);
		if(StringUtils.isNotEmpty(content) && StringUtils.startsWith(content,"ZJ") && content.length()==10){//专家认证的内容
			oms=buildTextContent(msg);
		} else {
			oms=buildClientServiceContent(msg);
		}
		return oms;
	}
	/**
	 * 构建文本返回的内容，构建客服消息
	 * @param content 告知客服系统，可以对接客服系统。
	 */
	private OutMessage buildClientServiceContent(InMessage msg){
		OutMessage oms=new OutMessage();
		oms.setMsgType("transfer_customer_service");
		return oms;
	}
	/**
	 * 构建文本返回的内容
	 * @param content 用户传过来的内容
	 */
	private OutMessage buildTextContent(InMessage msg){
		OutMessage oms=new OutMessage();
		UserCharLogVo userChatLogVo=new UserCharLogVo();
		userChatLogVo.setOpenid(msg.getFromUserName());
		userChatLogVo.setText(msg.getContent());
		userChatLogVo.setCreated_by("admin");
		userChatLogVo.setCreated_time(DateUtil.dateToStr(new Date()));
		userChatLogVo.setUpdated_by("admin");
		userChatLogVo.setUpdated_time(DateUtil.dateToStr(new Date()));
		wXCMSClientService.saveUserChatLog(userChatLogVo);
		//将用户的状态更改为0待认证
		//wXCMSClientService.updateWXUserRole(msg.getFromUserName());
		oms.setContent("您好,您的专家资格待后台工作人员审批认证");
		return oms;
	}
	/**
	 * 构建图文返回信息
	 * @param content 用户过来的内容
	 */
	private OutMessage buildArticlesConent(String content){
		OutMessage oms=new OutMessage();
		oms.setArticleCount(2);
		oms.setMsgType("news");
		List<Articles> list=new ArrayList<Articles>();
		Articles entity=new Articles("测试图文消息主题", "测试图文消息主题", "http://sukerwei.imwork.net/wx/images/integral_2.jpg"," http://app.ihotd.com/baonengggwwz721/");
		Articles entity1=new Articles("测试图文消息副标题", "测试图文消息副标题", "http://sukerwei.imwork.net/wx/images/pro.jpg"," http://app.ihotd.com/baonengggwwz721/");
		list.add(entity);
		list.add(entity1);
		oms.setArticles(list);
		return oms;
	}
}
