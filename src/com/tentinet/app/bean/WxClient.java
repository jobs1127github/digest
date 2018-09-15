package com.tentinet.app.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 好伙伴实体，（包含内部人员）
 * 全安好伙伴：客户、代表、内部人员
 * @author wangyu
 * 
 * @date 2018-09-03
 */
@Entity
@Table(name = "wx_client")
public class WxClient {
	@Id
	@GeneratedValue
    private Integer id;

    /**
     * 姓名
     */
	@Column(name = "name", length = 100)
    private String name;

    /**
     * 手机号
     */
	@Column(name = "telephone", length = 100)
    private String telephone;

    /**
     * 省份
     */
	@Column(name = "province", length = 100)
    private String province;

    /**
     * 地市
     */
	@Column(name = "city", length = 100)
    private String city;

    /**
     * 区县
     */
	@Column(name = "country", length = 100)
    private String country;

    /**
     * 入货乙方
     */
	@Column(name = "terminal_collection", length = 2000)
    private String terminalCollection;

    /**
     * openid
     */
	@Column(name = "openid", length = 100)
    private String openid;

    /**
     * 用户类型：1客户、2代表、3、内部人员  
     */
	@Column(name = "type", length = 100)
    private String type;
    
	@Column(name = "mtime")
    private Date mtime=new Date();//更新时间
    
    @Column(name = "ctime")
    private Date ctime=new Date();//创建时间

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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getTerminalCollection() {
        return terminalCollection;
    }

    public void setTerminalCollection(String terminalCollection) {
        this.terminalCollection = terminalCollection == null ? null : terminalCollection.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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

	@Override
	public String toString() {
		return "WxClient [id=" + id + ", name=" + name + ", telephone=" + telephone + ", province=" + province
				+ ", city=" + city + ", country=" + country + ", terminalCollection=" + terminalCollection + ", openid="
				+ openid + ", type=" + type + "]";
	}

	public WxClient(Integer id, String name, String telephone, String province, String city, String country,
			String terminalCollection, String openid, String type) {
		super();
		this.id = id;
		this.name = name;
		this.telephone = telephone;
		this.province = province;
		this.city = city;
		this.country = country;
		this.terminalCollection = terminalCollection;
		this.openid = openid;
		this.type = type;
	}

	public WxClient() {
		super();
	}
}