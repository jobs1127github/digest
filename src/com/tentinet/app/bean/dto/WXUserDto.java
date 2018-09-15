package com.tentinet.app.bean.dto;

public class WXUserDto {

	private String openid;
	private String wxno;
	private String status;
	private String publicno;
	private String username;
	private String head_portrait;//头像
	private String telphone;
	private String role;//用户角色　１：普通用户　２：专家用户   0:专家用户待审批
	private String expert;//专家名称
	private String created_by;
	private String updated_by;
	private String created_time;
	private String updated_time;
	private String sex;
	private Integer age;
	private String province;
	private String city;
	private String country;
	
	private Float gold;
	private String check_time;
	private String group_id;
	private String group_name;
	private Float got_money;//已提取金额
	private Float can_get_money;//可提取金额
	private Float award_count;//被打赏次数
	private Integer information_count;//发布资讯数
	private String text;//专家验证码
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getWxno() {
		return wxno;
	}
	public void setWxno(String wxno) {
		this.wxno = wxno;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPublicno() {
		return publicno;
	}
	public void setPublicno(String publicno) {
		this.publicno = publicno;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getHead_portrait() {
		return head_portrait;
	}
	public void setHead_portrait(String head_portrait) {
		this.head_portrait = head_portrait;
	}
	public String getExpert() {
		return expert;
	}
	public void setExpert(String expert) {
		this.expert = expert;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public String getUpdated_by() {
		return updated_by;
	}
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}
	public String getCreated_time() {
		return created_time;
	}
	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}
	public String getUpdated_time() {
		return updated_time;
	}
	public void setUpdated_time(String updated_time) {
		this.updated_time = updated_time;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Float getGold() {
		return gold;
	}
	public void setGold(Float gold) {
		this.gold = gold;
	}
	public String getCheck_time() {
		return check_time;
	}
	public void setCheck_time(String check_time) {
		this.check_time = check_time;
	}
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public Float getGot_money() {
		return got_money;
	}
	public void setGot_money(Float got_money) {
		this.got_money = got_money;
	}
	public Float getCan_get_money() {
		return can_get_money;
	}
	public void setCan_get_money(Float can_get_money) {
		this.can_get_money = can_get_money;
	}
	public Float getAward_count() {
		return award_count;
	}
	public void setAward_count(Float award_count) {
		this.award_count = award_count;
	}
	public Integer getInformation_count() {
		return information_count;
	}
	public void setInformation_count(Integer information_count) {
		this.information_count = information_count;
	}
	public String getProvince() {
		return province;
	}
	public String getCity() {
		return city;
	}
	public String getCountry() {
		return country;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}
