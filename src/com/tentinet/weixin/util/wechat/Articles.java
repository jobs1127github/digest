package com.tentinet.weixin.util.wechat;

import java.io.Serializable;

/**
 * 多图文消息实体类
 * 实体类，尽量都实现序列化接口。
 */
public class Articles implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 图文消息标题 */
	private String Title;

	/** 图文消息描述 */
	private String Description;

	/** 图片链接支持JPG、PNG格式较好的效果为大图640*320小图80*80 */
	private String PicUrl;

	/** 点击图文消息跳转链接 */
	private String Url;

	public Articles(String title, String description, String picUrl, String url) {
		this.Title = title;
		this.Description = description;
		this.PicUrl = picUrl;
		this.Url = url;
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

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		this.PicUrl = picUrl;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		this.Url = url;
	}
}
