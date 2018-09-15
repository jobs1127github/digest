package com.tentinet.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wx_information_award_info")
public class InformationAwardInfoVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tid;
	private String information_id;
	private String award_openid;
	private float gold;
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
	
	@Column(name="award_openid",length=100)	
	public String getAward_openid() {
		return award_openid;
	}
	public void setAward_openid(String award_openid) {
		this.award_openid = award_openid;
	}
	@Column(name="gold",length=32)
	public float getGold() {
		return gold;
	}
	public void setGold(float gold) {
		this.gold = gold;
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