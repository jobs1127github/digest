package com.tentinet.app.excel.model;

import com.tentinet.weixin.util.BASE64Utils;

/**
 * @author jobs1127
 *
 */
public class DownLoadWXUsers {
	private String openid;
	private String username;
	private String province;
	private String city;
	private String country;
	private String role;//用户角色　１：普通用户　２：专家用户   0:专家用户待审批
	private String sex;
	private Float got_money;//已提取金额
	private Float can_get_money;//可提取金额
	private Float award_count;//被打赏次数
	
	private String status;
	private String publicno;
	private String created_time;
	
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

	public String getCreated_time() {
		return created_time;
	}

	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}

	public DownLoadWXUsers() {}
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = BASE64Utils.getFromBASE64(username);
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		//用户角色　１：普通用户　２：专家用户   0:专家用户待审批
		if(role != null) {
			if(role.equals("1")) {
				this.role = "普通用户";
			} else if(role.equals("2")){
				this.role = "专家用户";
			} else {
				this.role = "专家用户待审批";
			}
		} else {
			this.role = "普通用户";
		}
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		if(sex != null) {
			if(sex.equals("1")) {
				this.sex = "男";
			} else if(sex.equals("2")){
				this.sex = "女";
			} else {
				this.sex = "非男非女";
			}
		} else {
			this.sex = "非男非女";
		}
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
}
