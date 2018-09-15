package com.tentinet.app.bean;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wx_information")
public class InformationVo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String information_id;
	private String title;
	private String tile_img_url;
	private String introduce;//前台精选
	private String keywords;
	private String content;
	private String audio_url;
	private String openid;
	private String status;
	private String information_type;
	private String mark;
	private String best_flag;//前台精选,前6个图片切换、和类别跳转
	private String created_by;
	private String created_time;
	private String updated_by;
	private String updated_time;
	
	@Id
	@Column(name="information_id",unique=true,nullable=false,length=100,scale=0)
	public String getInformation_id() {
		return information_id;
	}
	public void setInformation_id(String information_id) {
		this.information_id = information_id;
	}
	
	@Column(name="title",length=1000)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name="tile_img_url",length=1000)	
	public String getTile_img_url() {
		return tile_img_url;
	}
	public void setTile_img_url(String tile_img_url) {
		this.tile_img_url = tile_img_url;
	}
	@Column(name="introduce",length=1000)	
	public String getIntroduce() {
		return introduce;
	}	
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	@Column(name="keywords",length=400)		
	public String getKeywords() {
		return keywords;
	}	
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	@Column(name="content",length=15000)	
	public String getContent() {
		return content;
	}	
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name="audio_url",length=1000)	
	public String getAudio_url() {
		return audio_url;
	}	
	public void setAudio_url(String audio_url) {
		this.audio_url = audio_url;
	}
	@Column(name="openid",length=100)	
	public String getOpenid() {
		return openid;
	}	
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	@Column(name="status",length=2)	
	public String getStatus() {
		return status;
	}	
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name="information_type",length=2)	
	public String getInformation_type() {
		return information_type;
	}	
	public void setInformation_type(String information_type) {
		this.information_type = information_type;
	}
	@Column(name="mark",length=10)	
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	
	
	@Column(name="best_flag",length=2)	
	public String getBest_flag() {
		return best_flag;
	}
	public void setBest_flag(String best_flag) {
		this.best_flag = best_flag;
	}
	@Column(name="created_by",length=32)	
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	@Column(name="created_time",length=32)	
	public String getCreated_time() {
		return created_time;
	}
	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}
	@Column(name="updated_by",length=32)	
	public String getUpdated_by() {
		return updated_by;
	}
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}
	@Column(name="updated_time",length=32)	
	public String getUpdated_time() {
		return updated_time;
	}
	public void setUpdated_time(String updated_time) {
		this.updated_time = updated_time;
	}
}