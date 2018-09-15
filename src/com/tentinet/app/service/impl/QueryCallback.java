package com.tentinet.app.service.impl;

import org.compass.core.CompassCallback;
import org.compass.core.CompassException;
import org.compass.core.CompassHits;
import org.compass.core.CompassQueryBuilder;
import org.compass.core.CompassSession;
import org.compass.core.CompassQuery.SortDirection;
import org.compass.core.CompassQuery.SortPropertyType;

import com.tentinet.app.bean.MarkVo;

public class QueryCallback implements CompassCallback<QueryResult<MarkVo>>{
	private String keyword;
	private int firstResult;
	private int maxResult;
	
	@Override
	public QueryResult<MarkVo> doInCompass(CompassSession session) throws CompassException {
		/***
		 * 高级查询
		//查询指定类别的匹配记录，并按position降序排序 
		CompassQueryBuilder query = session.queryBuilder();
		//addMust相当于SQL语句的and
		//sql:categoryid = 1 and (xxx like ?) order by position desc
		CompassHits hits = query.bool().addMust(query.spanEq("categoryid", categoryid))
		.addMust(query.queryString(keyword).toQuery()).toQuery()
		//默认为升序
		.addSort("position",SortPropertyType.FLOAT,SortDirection.REVERSE).hits();
		**/
		
		
		
		//简单的搜索
		CompassHits hits = session.find(keyword);
		QueryResult<MarkVo> qr = new QueryResult<MarkVo>();
		qr.setTotalRecords(new Long(hits.length()));
		
		if (firstResult < 0) firstResult = 0;
		if (maxResult > hits.length()) maxResult = hits.length(); 
		for (int i=firstResult; i<maxResult; i++) {
			if(hits.data(i) instanceof MarkVo) {
				//hits.data(i)将对应的索引号的索引加载到内存
				MarkVo m = (MarkVo)hits.data(i);
				if (hits.highlighter(i).fragment("markName") != null) {
					m.setMark_name(hits.highlighter(i).fragment("markName"));
				}
				qr.getQueryList().add(m);
			} 
		}
		return qr;
	}
	
	public QueryCallback() {
		super();
	}

	public QueryCallback(String keyword, int firstResult, int maxResult) {
		super();
		this.keyword = keyword;
		this.firstResult = firstResult;
		this.maxResult = maxResult;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public int getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}
}
