package com.tentinet.app.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/***
 * 签到实体
 * @author jobs1127
 *
 */
@Entity
@Table(name = "wx_qiandao")
public class QianDao {
	@Id
	@Column(name = "openid", unique = true, nullable = false, length = 100, scale = 0)
	private String openid;
	private String username;
	private String nickname;
	private String date;
	private String time;
	private Integer count;
	private String province;
	private String country;
	private String city;
	private String publicno;
	private Integer ljcount;
	
	public Integer getLjcount() {
		return ljcount;
	}
	public void setLjcount(Integer ljcount) {
		this.ljcount = ljcount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
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
		this.username = username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
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
	public String getPublicno() {
		return publicno;
	}
	public void setPublicno(String publicno) {
		this.publicno = publicno;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}
