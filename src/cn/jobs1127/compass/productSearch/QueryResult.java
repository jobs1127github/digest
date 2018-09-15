package cn.jobs1127.compass.productSearch;

import java.util.List;

public class QueryResult {
	private List<?> queryList;
	private Long totalRecords;
	public QueryResult() {
	}
	public List<?> getQueryList() {
		return queryList;
	}
	public void setQueryList(List<?> queryList) {
		this.queryList = queryList;
	}
	public Long getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}
}
