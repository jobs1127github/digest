package com.tentinet.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-09-06
 */
@Entity
@Table(name = "wx_prize")
public class WxPrize implements Serializable{
	@Id
	@Column(name = "id")
	@GeneratedValue
	private Integer id;
	
	@Column(name = "openid",nullable = false, length = 100, scale = 0)
    private String openid;

    /**
     * 奖品名称
     */
	@Column(name = "prize_name", length = 100)
    private String prizeName;

    /**
     * 发货日期
     */
	@Column(name = "delivery_time", length = 100)
    private String deliveryTime;

    /**
     * 发货状态
     */
	@Column(name = "delivery_status", length = 100)
    private String deliveryStatus;

    /**
     * 发货人
     */
	@Column(name = "delivery_man", length = 100)
    private String deliveryMan;

    /**
     * 收货人
     */
	@Column(name = "consignee", length = 100)
    private String consignee;

    /**
     * 收货地址
     */
	@Column(name = "address", length = 100)
    private String address;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName == null ? null : prizeName.trim();
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime == null ? null : deliveryTime.trim();
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus == null ? null : deliveryStatus.trim();
    }

    public String getDeliveryMan() {
        return deliveryMan;
    }

    public void setDeliveryMan(String deliveryMan) {
        this.deliveryMan = deliveryMan == null ? null : deliveryMan.trim();
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee == null ? null : consignee.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public WxPrize() {
	}
}