package com.tentinet.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "t_menu")
public class MenuVo implements Serializable {
	private static final long serialVersionUID = 6356964340105506286L;
	private String menuid;//菜单id
	private String enable;//是否有效菜单
	private String icon;//菜单对应的图标
	private String ordernum;//菜单显示排序
	private String parmenuid;//菜单父id
	private String title;//菜单标题
	private String menuurl;//点击菜单对应的跳转链接
	
	private String createdBy;// 创建人
	private String updatedBy;// 维护人
	private Date createdTime;// 创建 时间
	private Date upadtedTime;// 维护时间

	@SequenceGenerator(name = "sequenceGenerator", sequenceName = "ROLE_MENU_SEQ")
	@Id
	@Column(name = "menuid", unique = true, nullable = false, length = 32)
	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	@Column(name = "enable", length = 2)
	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	@Column(name = "icon", length = 100)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "ordernum", length = 100)
	public String getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}

	@Column(name = "parmenuid", length = 32)
	public String getParmenuid() {
		return parmenuid;
	}

	public void setParmenuid(String parmenuid) {
		this.parmenuid = parmenuid;
	}

	@Column(name = "title", length = 32)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "menuurl", length = 32)
	public String getMenuurl() {
		return menuurl;
	}

	public void setMenuurl(String menuurl) {
		this.menuurl = menuurl;
	}

	@Column(name = "created_by", length = 32)
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "updated_By", length = 32)
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Column(name = "created_Time", length = 100)
	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Column(name = "upadted_Time", length = 100)
	public Date getUpadtedTime() {
		return upadtedTime;
	}

	public void setUpadtedTime(Date upadtedTime) {
		this.upadtedTime = upadtedTime;
	}
}
