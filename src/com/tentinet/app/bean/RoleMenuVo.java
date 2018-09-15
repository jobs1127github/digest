package com.tentinet.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色菜单信息
 */

@Entity
@Table(name="t_role_menu")
public class RoleMenuVo implements Serializable {

	private static final long serialVersionUID = 6292869528583788965L;
	private long key_id;
	private String menu_id;
	private String role_id;
	private String enable;
	private String craeted_by;
	private String updated_by;
	private String created_time;
	private String updated_time;

	@Id
	@Column(name="key_id",unique=true,nullable=false,length=32,scale=0)
	public long getKey_id() {
		return key_id;
	}

	public void setKey_id(long key_id) {
		this.key_id = key_id;
	}

	@Column(name="menu_id",length=32)
	public String getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}

	@Column(name="role_id",length=32)
	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}


	@Column(name="enable",length=2)
	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}


	@Column(name="craeted_by",length=32)
	public String getCraeted_by() {
		return craeted_by;
	}

	public void setCraeted_by(String craeted_by) {
		this.craeted_by = craeted_by;
	}


	@Column(name="updated_by",length=32)
	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}


	@Column(name="created_time",length=32)
	public String getCreated_time() {
		return created_time;
	}

	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}


	@Column(name="updated_time",length=32)
	public String getUpdated_time() {
		return updated_time;
	}

	public void setUpdated_time(String updated_time) {
		this.updated_time = updated_time;
	}
}
