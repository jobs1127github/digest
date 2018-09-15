package com.tentinet.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wx_information_browse_info")
public class InformationBrowseInfoVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tid;
	private String information_id;
	private String browser_openid;
	private String browse_time;
	private String created_by;
	private String created_time;
	private String updated_by;
	private String updated_time;
	
	@Id
	@Column(name="tid",unique=true,nullable=false,length=100,scale=0)
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	
	@Column(name="information_id",length=100)	
	public String getInformation_id() {
		return information_id;
	}
	public void setInformation_id(String information_id) {
		this.information_id = information_id;
	}
	
	
	@Column(name="browse_time",length=32)	
	public String getBrowse_time() {
		return browse_time;
	}
	public void setBrowse_time(String browse_time) {
		this.browse_time = browse_time;
	}
	
	@Column(name="browser_openid",length=100)
	public String getBrowser_openid() {
		return browser_openid;
	}
	public void setBrowser_openid(String browser_openid) {
		this.browser_openid = browser_openid;
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
