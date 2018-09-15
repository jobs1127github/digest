package com.tentinet.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

/**
 * 基础的数据 VO
 *
 */
public class BaseVo implements Serializable{
   

	private static final long serialVersionUID = 8731757713003395151L;
	private String createdBy;//创建人
	private String updatedBy;//维护人
	private Date createdTime;//创建 时间
	private Date upadtedTime;//维护时间
	
	@Column(name="created_by",length=32)
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Column(name="updated_By",length=32)
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	@Column(name="created_Time",length=100)
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	@Column(name="upadted_Time",length=100)
	public Date getUpadtedTime() {
		return upadtedTime;
	}
	public void setUpadtedTime(Date upadtedTime) {
		this.upadtedTime = upadtedTime;
	}
	
	
	
	
}
