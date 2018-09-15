package cn.jobs1127.compass.productSearch;
import org.compass.annotations.Index;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;
import org.compass.annotations.Store;

@Searchable
public class Promote {
	private Integer id;
	private String name;
	private String display;
	
	@SearchableId
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@SearchableProperty(index=Index.ANALYZED,store=Store.YES,name="prodcutname")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@SearchableProperty(index=Index.ANALYZED,store=Store.YES,name="productdisplay")
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public Promote(Integer id, String name, String display) {
		this.id = id;
		this.name = name;
		this.display = display;
	}
	public Promote() {
	}
	public Promote(Integer id) {
		this.id = id;
	}
}
