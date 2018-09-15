package com.tentinet.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 专家的钱包
 * @author jobs1127
 *
 */
@Entity
@Table(name="wx_expert_money")
public class ExpertMoneyVo implements Serializable{

	private static final long serialVersionUID = 1L;
	private String openid;
    private double money_count;
    private double money_usable;
    
    
    @Id
	@Column(name="openid",unique=true,nullable=false,length=100,scale=0)
	public String getOpenid() {
		return openid;
	}
    
    @Column(name="money_count")
	public double getMoney_count() {
		return money_count;
	}
    @Column(name="money_usable")
	public double getMoney_usable() {
		return money_usable;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public void setMoney_count(double money_count) {
		this.money_count = money_count;
	}
	public void setMoney_usable(double money_usable) {
		this.money_usable = money_usable;
	}
    
    
}
