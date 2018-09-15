package com.tentinet.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="wx_user_mark")
public class UserMarkVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String openid;
	private String str_mark;
	private String created_by;
	private String created_time;
	private String updated_by;
	private String updated_time;
	@Id
	@Column(name="openid",unique=true,nullable=false,length=100,scale=0)
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	@Column(name="str_mark",length=1000)
	public String getStr_mark() {
		return str_mark;
	}
	public void setStr_mark(String str_mark) {
		this.str_mark = str_mark;
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
