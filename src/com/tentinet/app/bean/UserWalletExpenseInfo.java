package com.tentinet.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wx_user_wallet_expense_info")
public class UserWalletExpenseInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String openid;
	private Float digest_gold;
	private Float money;
	private String expense_openid;
	private String expense_type;
	private String created_by;
	private String created_time;
	private String updated_by;
	private String updated_time;
	
	
	public UserWalletExpenseInfo() {
		
	}


	public UserWalletExpenseInfo(String openid, Float digest_gold,
			Float money, String expense_openid, String expense_type,
			String created_by, String created_time, String updated_by,
			String updated_time) {
		super();
		this.openid = openid;
		this.digest_gold = digest_gold;
		this.money = money;
		this.expense_openid = expense_openid;
		this.expense_type = expense_type;
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

	@Column(name="expense_openid")    
	public String getExpense_openid() {
		return expense_openid;
	}

	@Column(name="expense_type")  
	public String getExpense_type() {
		return expense_type;
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


	public void setExpense_openid(String expense_openid) {
		this.expense_openid = expense_openid;
	}


	public void setExpense_type(String expense_type) {
		this.expense_type = expense_type;
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
				+ expense_openid + ", expense_type=" + expense_type
				+ ", created_by=" + created_by + ", created_time="
				+ created_time + ", updated_by=" + updated_by
				+ ", updated_time=" + updated_time + "]";
	}
	
}
