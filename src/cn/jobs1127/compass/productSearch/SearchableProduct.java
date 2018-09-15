package cn.jobs1127.compass.productSearch;

public interface SearchableProduct {
	/**
	 * 添加索引
	 */
	public void buildIndex();
	/**
	 * 删除索引
	 * @param p
	 */
	public void deleteIndex(Product p);
	/**
	 * 更新索引
	 * @param p
	 */
	public void updateIndex(Product p);
	/**
	 * 查询索引
	 * @param keyword
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public QueryResult queryIndex(String keyword,int firstResult,int maxResults);
	public QueryResult queryIndex(String keyword,int categoryid,int firstResult,int maxResults);
	public void destory ();
}
