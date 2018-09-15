package com.tentinet.app.service.impl;

import java.util.ArrayList;
import java.util.List;
/**
 * 索引的查询结果
 * 
 * @author Jobs1127
 *
 * @param <T>
 */
public class QueryResult<T> {
	private List<T> queryList = new ArrayList<T>();
	private Long totalRecords;
	public QueryResult() {
	}
	
	public List<T> getQueryList() {
		return queryList;
	}
	public void setQueryList(List<T> queryList) {
		this.queryList = queryList;
	}
	public Long getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}
}
