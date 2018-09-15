package com.tentinet.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 角色Vo
 * 
 */
@Entity
@Table(name = "t_role")
public class RoleVo implements Serializable {
	private static final long serialVersionUID = -8098280789381352842L;
	private String role_id;//角色id
	private String role_status;//角色状态
	private String memo;//角色描述
	private String role_name;//角色名称
	
	private String created_by;
	private String updated_by;
	private String created_time;
	private String updated_time;

	@Id
	@Column(name = "role_id", unique = true, nullable = false, length = 32, scale = 0)
	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	@Column(name = "role_status", length = 2)
	public String getRole_status() {
		return role_status;
	}

	public void setRole_status(String role_status) {
		this.role_status = role_status;
	}

	@Column(name = "memo", length = 1000)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "role_name", length = 100)
	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	@Column(name = "created_by", length = 32)
	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	@Column(name = "updated_by", length = 32)
	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	@Column(name = "created_time", length = 32)
	public String getCreated_time() {
		return created_time;
	}

	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}

	@Column(name = "updated_time", length = 32)
	public String getUpdated_time() {
		return updated_time;
	}

	public void setUpdated_time(String updated_time) {
		this.updated_time = updated_time;
	}
}
