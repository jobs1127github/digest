package cn.jobs1127.serializable.object;

import java.io.Serializable;
import java.util.Date;
/*
 * 实现可序列化接口
 */
public class Person implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;   //名字
	private int year;     //年龄
	private String city; //城市
	private Date birth; //生日
	
	public Person(String name, int year, String city, Date birth) {
		super();
		this.name = name;
		this.year = year;
		this.city = city;
		this.birth = birth;
	}
	public Person() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birth == null) ? 0 : birth.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + year;
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
		Person other = (Person) obj;
		if (birth == null) {
			if (other.birth != null)
				return false;
		} else if (!birth.equals(other.birth))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	/*
	 * @see java.lang.Object#toString()
	 * 重写toString，不然序列化之后显示的是内存地址
	 */
	public String toString(){
		return this.name+" "+this.year+" "+this.city+" "+this.birth.toString();
	}
}
