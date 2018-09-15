package com.tentinet.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 地区表
 */
@Entity
@Table(name = "t_area")
public class AreaVo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;//id
	private int parents_id;//父id 构成树
	private String city;

	public AreaVo() {

	}

	public AreaVo(String id, int parents_id, String city) {
		super();
		this.id = id;
		this.parents_id = parents_id;
		this.city = city;
	}

	@Id
	@Column(name = "id")
	public String getId() {
		return id;
	}

	@Column(name = "parents_id")
	public int getParents_id() {
		return parents_id;
	}

	@Column(name = "city")
	public String getCity() {
		return city;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setParents_id(int parents_id) {
		this.parents_id = parents_id;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
