package cn.jobs1127.compass.productSearch;

import java.io.Serializable;

import org.compass.annotations.Index;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableProperty;
import org.compass.annotations.SearchableReference;
import org.compass.annotations.Store;

/**
 * Brand是一个搜索实体，但是不是独立的搜索实体，他是关联在Product搜索实体里的子搜索实体。
 * @author Jobs1127
 *
 */
@Searchable(root=false)
public class Brand implements Serializable{
	private static final long serialVersionUID = -4540465642606278764L;
	/** 品牌id**/
	private String code;
	/** 品牌名**/
	private String name;
	/** 是否可见**/
	private Boolean visible = true;
	/** logo图片路径:/images/brand/2008/12/12/ooo.gif" **/
	private String logopath;
	
	private Product product;
	
	public Brand(String code) {
		this.code = code;
	}

	public Brand() {}
	
	public Brand(String name, String logopath) {
		this.name = name;
		this.logopath = logopath;
	}
	/**
	 * 如果在搜索的结果里，要显示该品牌的code，则应该把该品牌的code保存到索引document文档中。
	 * 
	 * index=Index.NO不建立搜索的索引，store=Store.YES但是存放到索引文件中。
	 * @return
	 */
	@SearchableProperty(index=Index.NO,store=Store.YES)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	/***
	 * 品牌的名称，用户可能会搜索，故应该进行建立索引。
	 * index=Index.NOT_ANALYZED建立索引，但是不分词。
	 * @return
	 */
	@SearchableProperty(index=Index.NOT_ANALYZED,store=Store.YES,name="brandName")
	public String getName() {
		return name;
	}
	
	/**
	 * Product中关联了Brand对象，而Brand类里又关联了Product对象，
	 * 则需要指向引用回原来的搜索实体 
	 * @return
	 */
	@SearchableReference
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	public String getLogopath() {
		return logopath;
	}
	public void setLogopath(String logopath) {
		this.logopath = logopath;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		final Brand other = (Brand) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}
}
