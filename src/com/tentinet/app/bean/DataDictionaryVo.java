package com.tentinet.app.bean;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

/**
 * 字典类
 */
@Entity
@Table(name = "t_data_dictionary")
public class DataDictionaryVo implements Serializable {

	private static final long serialVersionUID = -2264387993989011176L;

	private String datakey;
	private String dataItem;
	private String dataValue;
	private String dataType;
	private String status;

	@Id
	@Column(name = "t_data_key", unique = true, nullable = false, length = 225, scale = 0)
	public String getDatakey() {
		return datakey;
	}

	public void setDatakey(String datakey) {
		this.datakey = datakey;
	}

	@Column(name = "t_data_item", unique = true, nullable = false, length = 32)
	public String getDataItem() {
		return dataItem;
	}

	public void setDataItem(String dataItem) {
		this.dataItem = dataItem;
	}

	@Column(name = "t_data_value", length = 32)
	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	@Column(name = "t_data_type", length = 32)
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Column(name = "status", length = 2)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
