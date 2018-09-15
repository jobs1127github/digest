/**
 * 
 */
package com.tentinet.app.excel.model;

/**
 * @author jobs1127
 *
 */
public class GoldDownLoadView {
	private String name;
	private String type;
	private String time;
	private String count;
	public GoldDownLoadView(){}
	public GoldDownLoadView(String name,String type,String time,String count){
		this.name = name;
		this.type = type;
		this.time = time;
		this.count = count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
}
