package com.tentinet.app.service;

import java.util.List;
import java.util.Map;

/**
 * IBaties
 * @author Jobs1127
 * 
 */
public interface ICommonIbatiesBaseDao {
	/**
	 * 查询单个对象
	 * 
	 * @param sqlId 是在使用ibaties时，用于在SQLMap里标识区分不同的select元素或其他元素的
	 * @param params
	 * @return
	 */
	public Object queryForObject(String sqlId, Map<String, Object> params);

	/**
	 * 查询列表信息
	 * 
	 * @param sqlId
	 * @param params
	 * @return
	 */
	public List<?> queryForList(String sqlId, Map<String, Object> params);

	/**
	 * 查询列表的数量
	 * 
	 * @param sqlId
	 * @param params
	 * @return
	 */
	public Integer queryForCount(String sqlId, Map<String, Object> params);

	/**
	 * 查询属性的名称
	 * 
	 * @param sqlId
	 * @param params
	 * @return
	 */
	public String queryForString(String sqlId, Map<String, Object> params);

	/**
	 * 更改少量的属性
	 */
	public boolean updateObject(String sqlId, Map<String, Object> params);

	/**
	 * 使用ibaties的方式做保存
	 * 
	 * @param sqlId
	 * @param entity
	 */
	public <T> boolean insertObject(String sqlId, T entity);

	/**
	 * 批量删除对象
	 */
	public void batchDeleteObjects(String sqlId, Map<String, Object> params);
}