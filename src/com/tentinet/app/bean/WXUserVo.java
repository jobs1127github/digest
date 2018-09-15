package com.tentinet.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 微信用户实体
 */
@Entity
@Table(name = "wx_openid_wxno")
public class WXUserVo implements Serializable {
	private static final long serialVersionUID = 6720813963819678507L;
	private String openid;
	private String status;
	private String publicno;
	private String username;
	private String telphone;
	private String role;// 用户角色　１：普通用户　２：专家用户 0:专家用户待审批
	private String head_portrait;// 头像
	private String expert;// 专家名称
	private String created_by;
	private String updated_by;
	private String created_time;
	private String updated_time;
	private String sex;
	private Integer age;
	private Float gold;
	private String province;
	private String country;
	private String city;
	private String check_time;
	private String group_id;
	private String frist_show;
	private String nickname;//昵称
	

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Id
	@Column(name = "openid", unique = true, nullable = false, length = 100, scale = 0)
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	@Column(name = "status", length = 2)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "publicno", length = 100)
	public String getPublicno() {
		return publicno;
	}

	public void setPublicno(String publicno) {
		this.publicno = publicno;
	}

	@Column(name = "username", length = 100)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "telphone", length = 100)
	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	@Column(name = "role", length = 100)
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	@Column(name = "created_time", length = 100)
	public String getCreated_time() {
		return created_time;
	}

	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}

	@Column(name = "updated_time", length = 100)
	public String getUpdated_time() {
		return updated_time;
	}

	public void setUpdated_time(String updated_time) {
		this.updated_time = updated_time;
	}

	@Column(name = "head_portrait", length = 100)
	public String getHead_portrait() {
		return head_portrait;
	}

	public void setHead_portrait(String head_portrait) {
		this.head_portrait = head_portrait;
	}

	@Column(name = "expert", length = 100)
	public String getExpert() {
		return expert;
	}

	public void setExpert(String expert) {
		this.expert = expert;
	}

	@Column(name = "sex", length = 10)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "age", length = 11)
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Transient
	public Float getGold() {
		return gold;
	}

	public void setGold(Float gold) {
		this.gold = gold;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "check_time", length = 32)
	public String getCheck_time() {
		return check_time;
	}

	public void setCheck_time(String check_time) {
		this.check_time = check_time;
	}

	@Column(name = "group_id", length = 100)
	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	@Column(name = "frist_show", length = 32)
	public String getFrist_show() {
		return frist_show;
	}

	public void setFrist_show(String frist_show) {
		this.frist_show = frist_show;
	}
}
