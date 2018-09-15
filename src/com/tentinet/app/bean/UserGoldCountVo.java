package com.tentinet.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户钱包
 */
@Entity
@Table(name="wx_user_gold_count")
public class UserGoldCountVo implements Serializable{

   private static final long serialVersionUID = 1L;
   private String openid;
   private float gold_count;
   private String created_by;
   private String created_time;
   private String updated_by;
   private String updated_time;
   
	public UserGoldCountVo() {
	     super();
    }
	
	public UserGoldCountVo(String openid, float gold_count, String created_by,
			String created_time, String updated_by, String updated_time) {
		super();
		this.openid = openid;
		this.gold_count = gold_count;
		this.created_by = created_by;
		this.created_time = created_time;
		this.updated_by = updated_by;
		this.updated_time = updated_time;
	}


	@Id
	@Column(name="openid",unique=true,nullable=false,length=32,scale=0)
	public String getOpenid() {
		return openid;
	}
	
	@Column(name="gold_count")
	public float getGold_count() {
		return gold_count;
	}
	
	@Column(name="created_by")
	public String getCreated_by() {
		return created_by;
	}
	
	@Column(name="created_time")
	public String getCreated_time() {
		return created_time;
	}
	
	@Column(name="updated_by")
	public String getUpdated_by() {
		return updated_by;
	}
	
	@Column(name="updated_time")
	public String getUpdated_time() {
		return updated_time;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public void setGold_count(float gold_count) {
		this.gold_count = gold_count;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}
	public void setUpdated_time(String updated_time) {
		this.updated_time = updated_time;
	}

	@Override
	public String toString() {
		return "UserWalletVo [openid=" + openid + ", gold_count="
				+ gold_count + ", created_by="
				+ created_by + ", created_time=" + created_time
				+ ", updated_by=" + updated_by + ", updated_time="
				+ updated_time + "]";
	}
   
}