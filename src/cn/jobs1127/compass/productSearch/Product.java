package cn.jobs1127.compass.productSearch;
import java.io.Serializable;

import org.compass.annotations.Index;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableComponent;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;
import org.compass.annotations.Store;

/**
 * 
 * 把该类标识为搜索实体，该实体与索引的document进行映射
 * 
 * @author Jobs1127
 * @Searchable类似于hibernate框架的@Entity注解，可以同比理解。
 * @Entity标识实体与数据库表进行映射。
 * @Searchable标识实体与索引的document进行映射。
 * 
 * root=true表示该搜索实体是独立的搜索实体，root=false表示不是根实体，是某个根实体的子实体，比如Brand实体。
 * @Searchable默认为@Searchable(root=true)
 * 
 * 需要注意的是：Product实体关联其他实体，则需要注意这些实体他们都保存到同一份索引document文档中，故需要注意
 * 每个实体的索引属性名应该区分开。比如Product类中的name属性，默认映射到document中的属性名为name,
 * Brand类的name属性，默认映射到document中的属性也为name,这样就会冲突。故应该通过
 * @SearchableProperty(name="修改成具有区分的name属性")
 */
@Searchable(root=true)
public class Product implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;//产品标题
	private String display;//产品描述
	private String code;//产品货号
	
	private Brand brand;//产品品牌
	
	/**
	 * hibernate框架@Id标识该属性为表的标识字段，即主键id，默认映射表的id
	 * compass框架@SearchableId标识该属性为document文档的标识属性，默认映射document中的id字段。
	 * @return
	 * @SearchableId默认为：@SearchableId(name="id")
	 */
	@SearchableId(name="id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @SearchableProperty注解标识某个实体属性为具有搜索能力的属性，该实体属性会被搜索，故要提供给他搜索能力。
	 * name="prodcutname"，name指定该实体bean的属性与document中的哪个字段匹配。
	 * 这里标识this.name实体属性与索引document中的prodcutname字段相映射。
	 * index=Index.NOT_ANALYZED表示不需要分词，但是要建立索引。
	 * index=Index.ANALYZED表示既要分词，又要建立索引，默认值
	 * store=Store.YES要把索引值保存到索引文件（document）中去。默认为YES
	 * boost的默认值为boost=1,用于设置属性在索引文件中的重要性。
	 * boost应用场景：比如搜索的关键字，既在name里，又在display描述里，而我们的需求是想，name中如果包含关键字，
	 * name排在display的前面，可以通过boost来指定，谁靠前排。
	 */
	@SearchableProperty(index=Index.ANALYZED,store=Store.YES,name="productName",boost=2)
	public String getName() {
		return name;
	} 
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * name="productdisplay"
	 */
	@SearchableProperty(index=Index.ANALYZED,store=Store.YES,name="productDisplay")
	public String getDisplay() {
		return display;
	}
	/**
	 * 该字段不会被搜索，所以不用给其注解其具有搜索能力，也不会将其保存到索引document文档中。
	 * @return
	 */
	@SearchableProperty(index=Index.NO,store=Store.YES)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	/**
	 * 复合类型的属性，通过@SearchableComponent来标识
	 * @return
	 */
	@SearchableComponent
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Product(Integer id, String name, String display) {
		this.id = id;
		this.name = name;
		this.display = display;
	}
	public Product() {
	}
	public Product(Integer id) {
		this.id = id;
	}
}
