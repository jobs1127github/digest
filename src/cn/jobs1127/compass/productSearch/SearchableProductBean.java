package cn.jobs1127.compass.productSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.compass.annotations.config.CompassAnnotationsConfiguration;
import org.compass.core.Compass;
import org.compass.core.CompassException;
import org.compass.core.CompassHits;
import org.compass.core.CompassQueryBuilder;
import org.compass.core.CompassSession;
import org.compass.core.CompassTransaction;
import org.compass.core.CompassQuery.SortDirection;
import org.compass.core.CompassQuery.SortPropertyType;
import org.compass.core.config.CompassConfiguration;
import org.compass.core.config.CompassEnvironment;

/**
 * 实现接口
 * 
 * @author Jobs1127
 *
 */
public class SearchableProductBean implements SearchableProduct {
	CompassConfiguration conf = null;
	Compass compass = null;//类似于hibernate的sessionFactory
	/**
	 * 通过构造方法调用时，顺便实例化compass对象
	 */
	public SearchableProductBean() {
		//编程式配置
		conf = new CompassAnnotationsConfiguration();
		/**
		 * file://indexfile相对路径，绝对路径：file://D:/indexfile
		 * 在硬盘文件中建立索引。
		 */
		conf.setSetting(CompassEnvironment.CONNECTION, "file://indexfile");
		
		/**
		 * 在内存中建立索引文件
		 */
	    //conf.setSetting(CompassEnvironment.CONNECTION, "ram://indexfile");
	 
		/***
		 * compass默认的分词器是一元分词，这里把默认的分词器修改成paoding分词器（字典分词器）
		 */
		conf.setSetting("compass.engine.analyzer.default.type", "net.paoding.analysis.analyzer.PaodingAnalyzer");
		
		/**
		 * 高亮显示，在关键字周边加上HTML标签
		 */
		conf.setSetting("compass.engine.highlighter.default.formatter.simple.pre", "<font color='red'>");
		conf.setSetting("compass.engine.highlighter.default.formatter.simple.post", "</font>");
	    /***
	     * 扫描目标包下的带有@Searchable标识的实体。
	     */
		conf.addScan("cn.jobs1127.compass");
		compass = conf.buildCompass();
	}
	/**
	 * compass支持增量索引，社会上很多公司，他们的产品并没有实现增量索引，
	 * 原因：
	 * 1、Lucene版本较低，不支持增量索引。
	 * 2、要实现增量索引，技术要求稍微高点，有难度。
	 * 他们的解决方法是：晚上2~3点，采用定时器，把索引文件删除，重新生成索引，缺点是实时性不强，在
	 * 索引文件更新的时候，用户是访问不了的。
	 * 构建索引，添加索引实体到索引document文档中。
	 */
	public void buildIndex() {
		CompassSession session = null;
		CompassTransaction tx = null;
		try {
			session = compass.openSession();
			tx = session.beginLocalTransaction();
			
			Product p1 = new Product(1,"a篮球","很好的篮球");
			Brand brand = new Brand();
			brand.setCode(UUID.randomUUID().toString());
			brand.setName("耐克");
			brand.setProduct(p1);
			p1.setBrand(brand);
			session.create(p1);//类似于hibernate的save方法
			Product p2 = new Product(2,"b篮球","哈哈篮球");
			
			brand = new Brand();
			brand.setCode(UUID.randomUUID().toString());
			brand.setName("李零");
			brand.setProduct(p2);
			p2.setBrand(brand);
			session.create(p2);
			
			Product p3 = new Product(3,"c篮球","红色的篮球");
			brand = new Brand();
			brand.setCode(UUID.randomUUID().toString());
			brand.setName("特别");
			brand.setProduct(p3);
			p3.setBrand(brand);
			
			session.create(p3);
			
			tx.commit();
		} catch (CompassException ce) {
			ce.getStackTrace();
			tx.rollback();
		} finally {
			if (session != null)session.close();
		}
	}
	/**
	 * 删除索引
	 */
	public void deleteIndex(Product p) {
		CompassSession session = null;
		CompassTransaction tx = null;
		try {
			session = compass.openSession();
			tx = session.beginLocalTransaction();
			session.delete(p);
			tx.commit();
		} catch (CompassException ce) {
			ce.getStackTrace();
			tx.rollback();
		} finally {
			if (session != null)session.close();
		}
	}
	/**
	 * 更新索引
	 */
	public void updateIndex(Product p) {
		CompassSession session = null;
		CompassTransaction tx = null;
		try {
			session = compass.openSession();
			tx = session.beginLocalTransaction();
			session.delete(p);
			session.save(p);
			tx.commit();
		} catch (CompassException ce) {
			ce.getStackTrace();
			tx.rollback();
		} finally {
			if (session != null)session.close();
		}
	}
	/**
	 * 查询索引，分页查询
	 */
	public QueryResult queryIndex(String keyword,int firstResult,int maxResults) {
		CompassSession session = null;
		CompassTransaction tx = null;
		QueryResult qr = new QueryResult();
		List<Object> list = new ArrayList<Object>();
		try {
			session = compass.openSession();
			tx = session.beginLocalTransaction();
			/**
			 * @SearchableProperty这个标识的就是索引字段，
			 * 查询所有的索引字段，判断是否包含该keyword关键字。
			 * hits集合里存放的是搜索到索引号
			 */
			CompassHits hits = session.find(keyword);
			System.out.println("keyword="+keyword);
			System.out.println("hits="+hits.length());
			if (firstResult < 0) firstResult = 0;
			if (maxResults > hits.length()) maxResults = hits.length(); 
			int len = firstResult + maxResults;
			for (int i=firstResult; i<len; i++) {
				if(hits.data(i) instanceof Product) {
					//hits.data(i)将对应的索引号的索引加载到内存
					Product product = (Product)hits.data(i);
					if (hits.highlighter(i).fragment("productName") != null) {
						product.setName(hits.highlighter(i).fragment("productName"));
					}
					if (hits.highlighter(i).fragment("productDisplay") != null) {
						product.setDisplay(hits.highlighter(i).fragment("productDisplay"));
					}
					list.add(product);
				} 
			}
			qr.setQueryList(list);
			qr.setTotalRecords(new Long(hits.length()));
			tx.commit();
			hits.close();
		} catch (CompassException ce) {
			ce.getStackTrace();
			tx.rollback();
		} finally {
			if (session != null)session.close();
		}
		return qr;
	}
	/**
	 * 高级的查询
	 */
	public QueryResult queryIndex(String keyword,int categoryid,int firstResult,int maxResults) {
		CompassSession session = null;
		CompassTransaction tx = null;
		QueryResult qr = new QueryResult();
		List<Product> list = new ArrayList<Product>();
		try {
			session = compass.openSession();
			tx = session.beginLocalTransaction();
			//查询指定类别的匹配记录，并按position降序排序 
			CompassQueryBuilder query = session.queryBuilder();
			/**
			 * addMust相当于SQL语句的and
			 * sql:categoryid = 1 and (xxx like ?) order by position desc
			 */
			CompassHits hits = query.bool().addMust(query.spanEq("categoryid", categoryid))
			.addMust(query.queryString(keyword).toQuery()).toQuery()
			//默认为升序
			.addSort("position",SortPropertyType.FLOAT,SortDirection.REVERSE).hits();
			if (firstResult < 0) firstResult = 0;
			if (maxResults > hits.length()) maxResults = hits.length(); 
			int len = firstResult + maxResults;
			for (int i=firstResult; i<len; i++) {
				Product product = (Product)hits.data(i);
				if (hits.highlighter(i).fragment("productName") != null) {
					product.setName(hits.highlighter(i).fragment("productName"));
				}
				
				if (hits.highlighter(i).fragment("productDisplay") != null) {
					product.setDisplay(hits.highlighter(i).fragment("productDisplay"));
				}

				list.add(product);
			}
			qr.setQueryList(list);
			qr.setTotalRecords(new Long(hits.length()));
			tx.commit();
			hits.close();
		} catch (CompassException ce) {
			ce.getStackTrace();
			tx.rollback();
		} finally {
			if (session != null)session.close();
		}
		return qr;
	}
	public void destory () {
		if (compass != null)compass.close();
	}
}
