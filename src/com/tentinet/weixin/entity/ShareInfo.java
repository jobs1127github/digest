package com.tentinet.weixin.entity;

import java.io.Serializable;

/**
 * 分享内容配置类
 * @author Administrator
 *
 */
public class ShareInfo implements Serializable{

	private static final long serialVersionUID = 4174859896227339378L;
	
	private String title;//标题
	private String link;//链接
	private String content;//内容
	private String imgUrl;//图片
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	
	
   
}
