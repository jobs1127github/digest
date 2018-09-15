package com.tentinet.app.bean.dto;

import java.io.Serializable;
@SuppressWarnings("all")
public class PlanDTO implements Serializable{
	private String status;
	private Float money;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Float getMoney() {
		return money;
	}
	public void setMoney(Float money) {
		this.money = money;
	}
	public PlanDTO() {
	}
}
