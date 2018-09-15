package com.tentinet.weixin.entity;

import java.io.Serializable;
import java.util.Map;
/**
 * 模板消息类
 * @author jobs1127
 *
 */
public class TemplateMsgContent implements Serializable{
	private static final long serialVersionUID = -4230331141997429430L;
	private String touser;//发送消息给谁
	private String template_id;//模板ID
	private String url;//点击详情时的URL
	private String topcolor;//标题的颜色
	/**
	 * 详细内容
		{{first.DATA}}
		充值金额：{{keyword1.DATA}}
		充值门店：{{keyword2.DATA}}
		当前余额：{{keyword3.DATA}}
		{{remark.DATA}}
		在发送时，需要将内容中的参数（{{.DATA}}内为参数）赋值替换为需要的信息
		第一个参数：first
		第二个参数：keyword1，还可以设置参数的颜色
	 * @param msg
	 * @return
	 * 这是个模板消息，必须先申请模板，然后把模板id配置在weixin-config-properties里
	 */
	private Map<String,TemplateData> data;//内容 集合
	
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTopcolor() {
		return topcolor;
	}
	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}
	public Map<String, TemplateData> getData() {
		return data;
	}
	public void setData(Map<String, TemplateData> data) {
		this.data = data;
	}
}