package com.tentinet.weixin.util.wechat;

/**
 * 事件消息类型
 */
public class EventType {
	
	/** 订阅事件类型. */
    public final static String SUBSCRIBE_EVENT_TYPE = "subscribe";

    /** 取消订阅事件类型. */
    public final static String UNSUBSCRIBE_EVENT_TYPE = "unsubscribe";

    /** 自定义菜单点击事件. */
    public final static String CLICK_EVENT_TYPE = "click";
    
    /** 自定义菜单网页跳转事件. */
    public final static String VIEW_EVENT_TYPE = "view";
    
}
