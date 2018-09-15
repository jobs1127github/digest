package com.tentinet.app.bean.dto;

import java.io.Serializable;
import java.util.Date;

public class ProductStatisticsHistoryDto implements Serializable{
   private static final long serialVersionUID = 1L;
   private String product_id;
   private int sales_volume;
   private int click_volume;
   private int week;
   private int year;
   private int month;
   private String created_by;
   private String updated_by;
   private Date created_time;
   private Date updated_time;
   
   public ProductStatisticsHistoryDto() {
	
    }
	public String getProduct_id() {
		return product_id;
	}
	public int getSales_volume() {
		return sales_volume;
	}
	public int getClick_volume() {
		return click_volume;
	}
	public int getWeek() {
		return week;
	}
	public int getYear() {
		return year;
	}
	public int getMonth() {
		return month;
	}
	public String getCreated_by() {
		return created_by;
	}
	public String getUpdated_by() {
		return updated_by;
	}
	public Date getCreated_time() {
		return created_time;
	}
	public Date getUpdated_time() {
		return updated_time;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public void setSales_volume(int sales_volume) {
		this.sales_volume = sales_volume;
	}
	public void setClick_volume(int click_volume) {
		this.click_volume = click_volume;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}
	public void setCreated_time(Date created_time) {
		this.created_time = created_time;
	}
	public void setUpdated_time(Date updated_time) {
		this.updated_time = updated_time;
	}
	@Override
	public String toString() {
		return "ProductStatisticsHistoryDto [product_id=" + product_id
				+ ", sales_volume=" + sales_volume + ", click_volume="
				+ click_volume + ", week=" + week + ", year=" + year
				+ ", month=" + month + ", created_by=" + created_by
				+ ", updated_by=" + updated_by + ", created_time="
				+ created_time + ", updated_time=" + updated_time + "]";
	}
	
}
