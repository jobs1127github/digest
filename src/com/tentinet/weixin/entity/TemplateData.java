package com.tentinet.weixin.entity;

import java.io.Serializable;

/**
 * 模板消息和data的子类
 */
public class TemplateData implements Serializable {
	private static final long serialVersionUID = -6521205018926256003L;

	private String value;// 内容
	private String color;// 字体的颜色

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
