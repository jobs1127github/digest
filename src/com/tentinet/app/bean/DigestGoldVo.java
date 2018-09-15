package com.tentinet.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "wx_digest_gold")
public class DigestGoldVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tid;// 主键
	private String openid;
	private Float gold;// 金币数
	private String send_time;// 发送的时间
	private String send_type;// 发送的方式 1关注,2分享奖励记录，3系统奖励

	private String created_by;
	private String created_time;
	private String updated_by;
	private String updated_time;
	private Integer share_count;// 分享的次数

	@Id
	@Column(name = "tid", unique = true, nullable = false, length = 100, scale = 0)
	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	@Column(name = "openid", length = 100)
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	@Column(name = "gold")
	public Float getGold() {
		return gold;
	}

	public void setGold(Float gold) {
		this.gold = gold;
	}

	@Column(name = "send_time", length = 32)
	public String getSend_time() {
		return send_time;
	}

	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}

	@Column(name = "send_type", length = 2)
	public String getSend_type() {
		return send_type;
	}

	public void setSend_type(String send_type) {
		this.send_type = send_type;
	}

	@Column(name = "created_by", length = 32)
	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	@Column(name = "created_time", length = 32)
	public String getCreated_time() {
		return created_time;
	}

	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}

	@Column(name = "updated_by", length = 32)
	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	@Column(name = "updated_time", length = 32)
	public String getUpdated_time() {
		return updated_time;
	}

	public void setUpdated_time(String updated_time) {
		this.updated_time = updated_time;
	}

	@Column(name = "share_count", length = 2)
	public Integer getShare_count() {
		return share_count;
	}

	public void setShare_count(Integer share_count) {
		this.share_count = share_count;
	}

}
