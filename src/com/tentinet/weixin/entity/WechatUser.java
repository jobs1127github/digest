package com.tentinet.weixin.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信用户Dto
 */
public class WechatUser implements Serializable {
	private static final long serialVersionUID = 3806451553814640001L;
	/**
	 * 微信ID
	 */
    private String openId;
    /**
     * 性别
     */
    private String sex;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 用户积分
     */
    private Integer integral;
    /**
     * 用户佣金
     */
    private Integer commission;
    /**
     * 头像
     */
    private String headimgurl;
    /**
     * 国家
     */
    private String country;
    /**
     * 省份
     */
    private String province;
    /**
     * 地市
     */
    private String city;
    /**
     * 对应数据库里面的ID，可以有，可以没有。跟openId一一对应
     */
    private Integer userId;
    
    private Date createTime;

    private Date updateTime;
    
    private Integer source;
    
    private String status;  //Y:关注  N：取消关注
    
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Integer getCommission() {
		return commission;
	}

	public void setCommission(Integer commission) {
		this.commission = commission;
	}

	public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}