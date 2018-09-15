package com.tentinet.weixin.util.wechat;

/**
 * 
 * 微信事件
 */
public class WechatEvent 
{
	private Integer eventId;
	
	private String eventType;
	
	private String eventTypeKey;
	
	private String eventParam;
	
	private String executeParam;
	
	private String executeFunction;
	
	private String eventNode;

	public Integer getEventId() 
	{
		return eventId;
	}

	public void setEventId(Integer eventId) 
	{
		this.eventId = eventId;
	}

	public String getEventType() 
	{
		return eventType;
	}

	public void setEventType(String eventType) 
	{
		this.eventType = eventType;
	}

	public String getEventTypeKey() 
	{
		return eventTypeKey;
	}

	public void setEventTypeKey(String eventTypeKey) 
	{
		this.eventTypeKey = eventTypeKey;
	}

	public String getEventParam() 
	{
		return eventParam;
	}

	public void setEventParam(String eventParam) 
	{
		this.eventParam = eventParam;
	}

	public String getExecuteParam() 
	{
		return executeParam;
	}

	public void setExecuteParam(String executeParam) 
	{
		this.executeParam = executeParam;
	}

	public String getExecuteFunction() 
	{
		return executeFunction;
	}

	public void setExecuteFunction(String executeFunction) 
	{
		this.executeFunction = executeFunction;
	}

	public String getEventNode() 
	{
		return eventNode;
	}

	public void setEventNode(String eventNode) 
	{
		this.eventNode = eventNode;
	}
}
