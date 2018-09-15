package com.tentinet.app.excel.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Person implements Serializable {
	
    private int id;
    private String name;
    private char sex;
    private int phone;
    
	public Person() {
	}

	public Person(int id, String name, char sex, int phone) {
		super();
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.phone = phone;
	}

	public int getId() {
		return id;
	}


	public String getName() {
		return name;
	}

	public char getSex() {
		return sex;
	}

	public int getPhone() {
		return phone;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}


	public void setSex(char sex) {
		this.sex = sex;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", sex=" + sex
				+ ", phone=" + phone + "]";
	}
    
}
