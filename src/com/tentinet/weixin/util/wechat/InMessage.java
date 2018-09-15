package com.tentinet.weixin.util.wechat;

import java.io.Serializable;

/***
 * 接收消息实体类，接收openid发来的消息。
 * 用户往微信服务器请求的数据封装在该对象里。
 * 
 * 用户（FromUserName）发消息给微信服务器（ToUserName）
 * @author jobs1127
 *
 */
public class InMessage implements Serializable {
	private static final long serialVersionUID = -2572635563423558942L;

	/** 开发者微信号 处理消息的人*/
	private String ToUserName;

	// private String Encrypt;

	/** 用户发送方消息的OpenID 消息是从哪里来的，谁发出的*/
	private String FromUserName;

	/** 消息创建时间 */
	private Long CreateTime;

	/** text,image,location,link,event,voice,video 消息类型，event */
	private String MsgType = "text";

	/** 消息ID */
	private Long MsgId;

	private Long MsgID;

	/** 文本消息内容 */
	private String Content;

	/** 图片链接 */
	private String PicUrl;

	/** 语音格式：amr */
	private String Format;

	/** 语音消息媒体id，可以调用多媒体文件下载接口拉取该媒体 */
	private String MediaId;

	private String MenuId;

	private String ThumbMediaId;

	/** 地理位置纬度 */
	private String Location_X;

	/** 地理位置经度 */
	private String Location_Y;

	/** 地图缩放大小 */
	private Long Scale;

	/** 地理位置信息 */
	private String Label;

	/** 消息标题 */
	private String Title;

	/** 消息描述 */
	private String Description;

	/** 消息链接 */
	private String Url;

	/**
	 * subscribe关注,unsubscribe取消关注,scan,location地理位置,click点击事件
	 * 事件类型，subscribe(订阅)、unsubscribe(取消订阅)
	 */
	private String Event;

	/** 事件KEY值，与自定义菜单接口中KEY值对应 */
	private String EventKey;

	/** 二维码的ticket，可用来换取二维码图片 */
	private String Ticket;
	private String Latitude;
	private String Longitude;
	private String Precision;

	/** 语音识别结果，UTF8编码 */
	private String Recognition;
	/**
	 * 消信状态
	 */
	private String Status;

	public String getMenuId() {
		return MenuId;
	}

	public void setMenuId(String menuId) {
		MenuId = menuId;
	}

	public Long getMsgID() {
		return MsgID;
	}

	public void setMsgID(Long msgID) {
		MsgID = msgID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		this.ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.FromUserName = fromUserName;
	}

	public Long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Long createTime) {
		this.CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		this.MsgType = msgType;
	}

	public Long getMsgId() {
		return MsgId;
	}

	public void setMsgId(Long msgId) {
		this.MsgId = msgId;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		this.Content = content;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		this.PicUrl = picUrl;
	}

	public Long getScale() {
		return Scale;
	}

	public void setScale(Long scale) {
		this.Scale = scale;
	}

	public String getLabel() {
		return Label;
	}

	public void setLabel(String label) {
		this.Label = label;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		this.Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		this.Description = description;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		this.Url = url;
	}

	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		this.Event = event;
	}

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		this.EventKey = eventKey;
	}

	public String getTicket() {
		return Ticket;
	}

	public void setTicket(String ticket) {
		Ticket = ticket;
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}

	public String getRecognition() {
		return Recognition;
	}

	public void setRecognition(String recognition) {
		Recognition = recognition;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}

	public String getLocation_X() {
		return Location_X;
	}

	public void setLocation_X(String location_X) {
		Location_X = location_X;
	}

	public String getLocation_Y() {
		return Location_Y;
	}

	public void setLocation_Y(String location_Y) {
		Location_Y = location_Y;
	}

	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitude) {
		Latitude = latitude;
	}

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		Longitude = longitude;
	}

	public String getPrecision() {
		return Precision;
	}

	public void setPrecision(String precision) {
		Precision = precision;
	}

	public InMessage() {
	}
}
