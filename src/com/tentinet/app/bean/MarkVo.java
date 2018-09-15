package com.tentinet.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.compass.annotations.Index;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;
import org.compass.annotations.Store;

@Searchable(root=true)
@Entity
@Table(name="wx_information_mark")
public class MarkVo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String mark_code;
	private String mark_name;
	private String created_by;
	private String created_time;
	private String updated_by;
	private String updated_time;
	
	@SearchableId
	@Id
	@Column(name="mark_code",unique=true,nullable=false,length=225,scale=0)
	public String getMark_code() {
		return mark_code;
	}
	public void setMark_code(String mark_code) {
		this.mark_code = mark_code;
	}
	
	@SearchableProperty(name="markName",index=Index.ANALYZED,store=Store.YES)
	@Column(name="mark_name",length=32)
	public String getMark_name() {
		return mark_name;
	}
	public void setMark_name(String mark_name) {
		this.mark_name = mark_name;
	}
	
	@Column(name="created_by",length=32)
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	
	@Column(name="created_time",length=32)
	public String getCreated_time() {
		return created_time;
	}
	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}
	
	@Column(name="updated_by",length=32)
	public String getUpdated_by() {
		return updated_by;
	}
	public void setUpdated_by(String update_by) {
		this.updated_by = update_by;
	}
	
	@Column(name="updated_time",length=32)
	public String getUpdated_time() {
		return updated_time;
	}
	public void setUpdated_time(String update_time) {
		this.updated_time = update_time;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
