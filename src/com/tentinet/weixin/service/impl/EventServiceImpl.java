package com.tentinet.weixin.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.tentinet.weixin.service.EventService;
import com.tentinet.weixin.service.PrecessService;
import com.tentinet.weixin.util.SpringContextUtil;
import com.tentinet.weixin.util.wechat.InMessage;
import com.tentinet.weixin.util.wechat.MsgType;
import com.tentinet.weixin.util.wechat.OutMessage;

@Service(value = "eventService")
public class EventServiceImpl implements EventService {
	
	/***
	 * 通过Spring的DI、IOC注入相关对象。
	 */
	@Resource(name="textJob")
	private PrecessService textJob;
	@Resource(name="eventJob")
	private PrecessService eventJob;
	
	/***
	 * 处理消息
	 */
	@Override
	public OutMessage doPrecess(InMessage msg) {
		//System.out.println("openid="+msg.getFromUserName());
		OutMessage oms = null;
		PrecessService jobService = null;
		
		SpringContextUtil.getApplicationContext();// 初始化配置文件
		String msgType = msg.getMsgType().toLowerCase();// 消息类型
		if (StringUtils.equals(MsgType.TEXT, msgType)) {// 文本消息
			/**
			 * SpringContextUtil.getBean("textJob")通过Spring容器的上下文获取对象，
			 * 也可以通过注入的方式来获取相关的对象。
			 */
			jobService = (PrecessService) SpringContextUtil.getBean("textJob");
			oms = jobService.doJob(msg);
		} else if (StringUtils.equals(MsgType.EVENT, msgType)) {// 事件消息
			jobService = (PrecessService) SpringContextUtil.getBean("eventJob");
			oms = jobService.doJob(msg);
		} else {
			//还有其他类型的消息，可以根据需要处理。
		}
		return oms;
	}

	public PrecessService getTextJob() {
		return textJob;
	}

	public void setTextJob(PrecessService textJob) {
		this.textJob = textJob;
	}

	public PrecessService getEventJob() {
		return eventJob;
	}

	public void setEventJob(PrecessService eventJob) {
		this.eventJob = eventJob;
	}
}
