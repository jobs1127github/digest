package com.tentinet.app.service;

import com.tentinet.app.bean.MarkVo;
import com.tentinet.app.service.impl.QueryResult;

public interface MarkVoService {
	/**
	 * 更新数据库中的实体
	 * @param m
	 */
	public void update_to_db(MarkVo m);
	/***
	 * 保存实体对象到数据库中
	 * @param m
	 */
	public void save_to_db(MarkVo m);
	/***
	 * 保存实体对象到索引document文档中
	 * @param m
	 */
	public void save_to_index(MarkVo m);
	/**
	 * 搜索MarkVo
	 * @param keyword 关键字
	 * @param firstResult 分页的开始下标
	 * @param maxResult 分页的最大下标
	 * @return 结果集
	 */
	public QueryResult<MarkVo> query(String keyword,int firstResult,int maxResult);
}
