package com.tentinet.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 通讯录实体
 * 
 */
@Entity
@Table(name = "wx_mail_list")
@SuppressWarnings("all")
public class WxMailList implements Serializable {
	@Id
	@GeneratedValue
    private Integer id;

	/**
	 * 姓名
	 */
	@Column(name = "name", length = 100)
	private String name;
    /**
     * 性别
     */
	@Column(name = "sex", length = 10)
    private String sex="男";

    /**
     * 手机号
     */
	@Column(name = "mobile_phone", length = 100)
    private String mobilePhone;

    /**
     * 职位
     */
	@Column(name = "position", length = 100)
    private String position;

    /**
     * 座机号码
     */
	@Column(name = "machine_number", length = 100)
    private String machineNumber;

    /**
     * 传真号码
     */
	@Column(name = "fax_number", length = 100)
    private String faxNumber;
    
    /**
     * 通讯地址
     */
	@Column(name = "address", length = 100)
    private String address;
    
    /**
     * 邮箱
     */
	@Column(name = "email", length = 100)
    private String email;

    /**
     * 地区
     */
	@Column(name = "region", length = 100)
    private String region;
    
	@Column(name = "mtime")
    private Date mtime=new Date();//更新时间
	
	@Column(name = "ctime")
    private Date ctime=new Date();//创建时间

	
	/***
	 * region做Hash相等判断，如果region相等认为相等，用于过滤地区
	 */
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WxMailList other = (WxMailList) obj;
		if (region == null) {
			if (other.region != null)
				return false;
		} else if (!region.equals(other.region))
			return false;
		return true;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(String machineNumber) {
        this.machineNumber = machineNumber == null ? null : machineNumber.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public WxMailList(Integer id, String name, String mobilePhone, String position, String machineNumber, String email,
			String region) {
		super();
		this.id = id;
		this.name = name;
		this.mobilePhone = mobilePhone;
		this.position = position;
		this.machineNumber = machineNumber;
		this.email = email;
		this.region = region;
	}
	
	public Date getMtime() {
		return mtime;
	}

	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public WxMailList() {
		super();
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
}