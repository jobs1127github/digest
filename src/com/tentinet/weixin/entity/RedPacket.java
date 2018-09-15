package com.tentinet.weixin.entity;

import java.io.Serializable;

/**
 * 微用户首次注时,系统从商务号发送的的红包
 * 红包的钱，是从公众平台对接的商户平台下单的。
 */
public class RedPacket implements Serializable{

	private static final long serialVersionUID = 7390924890743614714L;
	private String mch_billno;//商务订单号 
	private String re_openid;//红包发送给谁
	
	public String getRe_openid() {
		return re_openid;
	}
	public void setRe_openid(String re_openid) {
		this.re_openid = re_openid;
	}
	public String getMch_billno() {
		return mch_billno;
	}
	public void setMch_billno(String mch_billno) {
		this.mch_billno = mch_billno;
	}
}