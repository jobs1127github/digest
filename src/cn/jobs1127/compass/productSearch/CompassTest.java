package cn.jobs1127.compass.productSearch;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import cn.jobs1127.compass.productSearch.Product;
import cn.jobs1127.compass.productSearch.QueryResult;
import cn.jobs1127.compass.productSearch.SearchableProduct;
import cn.jobs1127.compass.productSearch.SearchableProductBean;
/***
 * hibernate通过把实体对象保存到数据库，然后查询数据库，得到条件的结果集。
 * compass通过把实体对象保存到索引document文档中，然后查询索引字段，得到索引结果集。
 * 
 * @author Jobs1127
 *
 */
public class CompassTest {
	static SearchableProduct service = null;
	@BeforeClass
	public static void init() {
		service = new SearchableProductBean();
	}
	@Test
	public void testBuildIndex() {
		/** 
		 * 建立索引成功后，刷新项目，会发现indexfile文件下多了index等文件
		 */
		service.buildIndex();
	}
	@Test
	public void testQueryIndex() {
		//QueryResult qr = service.queryIndex("篮球", 0, 5);
		QueryResult qr = service.queryIndex("compass", 0, 5);
		Product product = null;
		System.out.println("qr="+qr);
		if(qr.getQueryList() != null && qr.getQueryList().size() >0) {
			for (Object obj:qr.getQueryList()) {
				if (obj instanceof Product) {
					product = (Product)obj;
					System.out.println(product.getId()+"--------"+product.getName()+"-------"+product.getDisplay());
				} 
			}
		}
	}
	@AfterClass
	public static void destory() {
		service.destory();
	}

}
