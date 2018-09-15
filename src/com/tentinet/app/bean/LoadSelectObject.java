package com.tentinet.app.bean;

public class LoadSelectObject {
	private String dataItem;
	private String dataValue;
	public String getDataItem() {
		return dataItem;
	}
	public void setDataItem(String dataItem) {
		this.dataItem = dataItem;
	}
	public String getDataValue() {
		return dataValue;
	}
	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
	public LoadSelectObject() {}
	public LoadSelectObject(String dataItem,String dataValue) {
		this.dataItem = dataItem;
		this.dataValue = dataValue;
	}
}
