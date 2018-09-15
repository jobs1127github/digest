package com.tentinet.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 记录那些已经领取过关注红包的用户。
 */

@Entity
@Table(name="wx_digest_redpacket")
public class DigestRedPacketVo implements Serializable{
	private static final long serialVersionUID = -3821595312285850466L;
	
	private String openid;
	private Float money_count;
	private String send_time;
	private String created_by;
	private String created_time;
	private String updated_by;
	private String updated_time;
	
	
	@Id
	@Column(name="openid",unique=true,nullable=false,length=32,scale=0)
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	@Column(name="money_count")
	public Float getMoney_count() {
		return money_count;
	}
	public void setMoney_count(Float money_count) {
		this.money_count = money_count;
	}
	@Column(name="send_time",length=32)
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
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
	@Column(name="updated_by",length=2)
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
