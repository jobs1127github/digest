package com.tentinet.app.bean.dto;

import java.io.Serializable;
/***
 * 用于接收前台参数
 * 
 * @author jobs1127
 *
 */
@SuppressWarnings("all")
public class QueryVO implements Serializable{
	/**
	 * 开始时间
	 */
	private String stime;
	/**
	 * 结束时间
	 */
	private String etime;
	/**
	 * 入货乙方
	 */
	private String terminal;
	/**
	 * 品种s
	 */
	private String[] products;
	/**
	 * 年
	 */
	private String year;
	
	/**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String telephone;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public QueryVO() {
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public String getTerminal() {
		return terminal;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	public String[] getProducts() {
		return products;
	}
	public void setProducts(String[] products) {
		this.products = products;
	}
}
