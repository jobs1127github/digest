package com.tentinet.app.service;

import java.util.List;

/**
 * Hibernate 只作对数据的维护,不作查询
 */
public interface ICommonHibernateBaseDao {
	/**
	 * 保存
	 * 
	 * @param entity
	 * @return
	 */
	public <T> boolean save(T entity);

	/**
	 * 修改
	 * 
	 * @param entity
	 * @return
	 */

	public <T> boolean update(T entity);

	/**
	 * 删除
	 * 
	 * @param entity
	 * @return
	 */
	public <T> boolean delete(T entity);

	/**
	 * 根据id得到单个对象
	 * @param type
	 * @param id
	 * @return
	 */
	public <T> T getEntityByLongId(Class<T> type, long id);
	
	/**
	 * 根据id得到单个对象
	 * @param type
	 * @param id
	 * @return
	 */
	public <T> T getEntityByStringId(Class<T> type, String id);

	/**
	 * 保存或修改
	 * @param entity
	 * @return
	 */
	public <T> boolean saveOrUpdate(T entity);

	/**
	 * 根据sql语句，获取一个list对象集合
	 * @param sql
	 * @param objects
	 * @return
	 */
	public <T> List<T> getList(String sql, Object... objects);

	/**
	 * 批量更新
	 * @param list
	 * @return
	 */
	public boolean updateAll(List<?> list);
}
