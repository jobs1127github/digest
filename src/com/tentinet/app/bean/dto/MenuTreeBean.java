package com.tentinet.app.bean.dto;

import java.io.Serializable;
import java.util.Collection;
/**
 * 菜单对象
 * @author jobs1127
 * DTO对象，通过该对象封装前台需要的数据
 */
public class MenuTreeBean implements Serializable {
	private static final long serialVersionUID = 8072477614709789237L;
	private String text;
	private String id;
	private String state;
	private String menuRUL;
	private String roleId;
	private String roleName;
	private String parMenuId;
	private String memo;
	private String icon;
	//菜单对象的孩子们
	private Collection<MenuTreeBean> children;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getParMenuId() {
		return parMenuId;
	}

	public void setParMenuId(String parMenuId) {
		this.parMenuId = parMenuId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Collection<MenuTreeBean> getChildren() {
		return children;
	}

	public void setChildren(Collection<MenuTreeBean> children) {
		this.children = children;
	}

	public String getMenuRUL() {
		return menuRUL;
	}

	public void setMenuRUL(String menuRUL) {
		this.menuRUL = menuRUL;
	}
}
