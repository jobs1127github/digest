package com.tentinet.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户信息Java bean/POJO
 * 
 * @author jobs1127
 * @Entity 标识成Spring的Bean,由Spring控管
 */
@Entity
@Table(name = "t_user")
public class UserVo implements Serializable {
	private static final long serialVersionUID = 6720813963819678507L;
	private long user_id;//操作员/用户id
	private String user_name;//用户名
	private String user_sex;//性别
	private String user_idcard;//身份证
	private String user_birthday;//生日
	private String user_mail;//邮箱
	private String user_tentinet_mail;//邮箱
	private String user_weixin_no;//用户对应的微信openid 用于知道哪些用户领取了微信的奖励
	private String status;//用户是否有效
	private String user_pass;//登陆密码
	private String user_role;//用户角色
	
	private String createdBy;// 创建人
	private String updatedBy;// 维护人
	private String createdTime;// 创建 时间
	private String upadtedTime;// 维护时间

	@Id
	@Column(name = "user_id", unique = true, nullable = false, length = 32, scale = 0)
	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	@Column(name = "user_name", length = 100)
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	@Column(name = "user_sex", length = 2)
	public String getUser_sex() {
		return user_sex;
	}

	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}

	@Column(name = "user_idcard", length = 100)
	public String getUser_idcard() {
		return user_idcard;
	}

	public void setUser_idcard(String user_idcard) {
		this.user_idcard = user_idcard;
	}

	@Column(name = "user_birthday", length = 100)
	public String getUser_birthday() {
		return user_birthday;
	}

	public void setUser_birthday(String user_birthday) {
		this.user_birthday = user_birthday;
	}

	@Column(name = "user_mail", length = 100)
	public String getUser_mail() {
		return user_mail;
	}

	public void setUser_mail(String user_mail) {
		this.user_mail = user_mail;
	}

	@Column(name = "user_tentinet_mail", length = 100)
	public String getUser_tentinet_mail() {
		return user_tentinet_mail;
	}

	public void setUser_tentinet_mail(String user_tentinet_mail) {
		this.user_tentinet_mail = user_tentinet_mail;
	}

	@Column(name = "user_weixin_no", length = 100)
	public String getUser_weixin_no() {
		return user_weixin_no;
	}

	public void setUser_weixin_no(String user_weixin_no) {
		this.user_weixin_no = user_weixin_no;
	}

	@Column(name = "status", length = 2)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "user_pass", length = 32)
	public String getUser_pass() {
		return user_pass;
	}

	public void setUser_pass(String user_pass) {
		this.user_pass = user_pass;
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
	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	@Column(name = "updated_time", length = 100)
	public String getUpadtedTime() {
		return upadtedTime;
	}

	public void setUpadtedTime(String upadtedTime) {
		this.upadtedTime = upadtedTime;
	}

	@Column(name = "user_role", length = 20)
	public String getUser_role() {
		return user_role;
	}

	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}
}