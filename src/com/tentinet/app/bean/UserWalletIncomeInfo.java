package com.tentinet.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wx_user_wallet_income_info")
public class UserWalletIncomeInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String openid;
	private Float digest_gold;
	private Float money;
	private String from_openid;
	private String from_type;
	private String created_by;
	private String created_time;
	private String updated_by;
	private String updated_time;
	
	
	public UserWalletIncomeInfo() {
		
	}


	public UserWalletIncomeInfo(String openid, Float digest_gold,
			Float money, String expense_openid, String expense_type,
			String created_by, String created_time, String updated_by,
			String updated_time) {
		super();
		this.openid = openid;
		this.digest_gold = digest_gold;
		this.money = money;
		this.from_openid = from_openid;
		this.from_type = from_type;
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

	@Column(name="digest_gold")
	public double getDigest_gold() {
		return digest_gold;
	}

	@Column(name="money")    
	public double getMoney() {
		return money;
	}

	@Column(name="from_openid")    
	public String getFrom_openid() {
		return from_openid;
	}

	@Column(name="from_type")  
	public String getFrom_type() {
		return from_type;
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


	public void setDigest_gold(Float digest_gold) {
		this.digest_gold = digest_gold;
	}


	public void setMoney(Float money) {
		this.money = money;
	}


	public void setFrom_openid(String expense_openid) {
		this.from_openid = expense_openid;
	}


	public void setFrom_type(String expense_type) {
		this.from_type = expense_type;
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
		return "userWalletExpenseInfo [openid=" + openid + ", digest_gold="
				+ digest_gold + ", money=" + money + ", expense_openid="
				+ from_openid + ", expense_type=" + from_type
				+ ", created_by=" + created_by + ", created_time="
				+ created_time + ", updated_by=" + updated_by
				+ ", updated_time=" + updated_time + "]";
	}
	
}
