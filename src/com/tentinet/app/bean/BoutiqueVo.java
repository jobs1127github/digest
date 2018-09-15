package com.tentinet.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="wx_information_boutique")
public class BoutiqueVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String information_id;
	private String title;
	private String title_img_url;
	private String content_url;
	private String status;
	private String created_by;
	private String created_time;
	private String updated_by;
	private String updated_time;
	private String headline;
	
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
	@Column(name="title_img_url",length=1000)
	public String getTitle_img_url() {
		return title_img_url;
	}
	public void setTitle_img_url(String title_img_url) {
		this.title_img_url = title_img_url;
	}
	@Column(name="content_url",length=1000)
	public String getContent_url() {
		return content_url;
	}
	public void setContent_url(String content_url) {
		this.content_url = content_url;
	}
	@Column(name="status",length=2)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	@Column(name="headline",length=1)
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}


}
