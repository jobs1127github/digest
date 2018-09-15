package com.tentinet.app.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.tentinet.app.service.ICommonIbatiesBaseDao;
/**
 * 持久层
 * @author jobs1127
 * 负责数据的增删改查
 */
@Repository(value = "commonBatiesBaseDao")
public class CommonBatiesBaseDaoImpl implements ICommonIbatiesBaseDao {

	protected static final Logger log = Logger.getLogger(CommonBatiesBaseDaoImpl.class);
	/**
	 * sqlMapClient在dataAccessContext-ibaties.xml配置文件中以单例的形式初始化。
	 * public abstract interface com.ibatis.sqlmap.client.SqlMapClient 
	 * extends com.ibatis.sqlmap.client.SqlMapExecutor, 
	 * com.ibatis.sqlmap.client.SqlMapTransactionManager {
	 * 具体的执行者是SqlMapExecutor接口。该接口提供了相关的方法对数据进行crud操作。
	 */
	private SqlMapClient sqlMapClient;

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}

	public SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}

	public Object queryForObject(String sqlId, Map<String, Object> params) {
		try {
			return sqlMapClient.queryForObject(sqlId, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<?> queryForList(String sqlId, Map<String, Object> params) {
		try {
			System.out.println("datasource="+sqlMapClient.getDataSource().getClass().getName());
			
			return sqlMapClient.queryForList(sqlId, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Integer queryForCount(String sqlId, Map<String, Object> params) {
		try {
			return (Integer) sqlMapClient.queryForObject(sqlId, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public String queryForString(String sqlId, Map<String, Object> params) {
		try {
			return (String) sqlMapClient.queryForObject(sqlId, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean updateObject(String sqlId, Map<String, Object> params) {
		boolean rel = false;
		int i = 0;
		try {
			i=sqlMapClient.update(sqlId, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (i > 0) {
			rel = true;
		}
		return rel;
	}

	public <T> boolean insertObject(String sqlId, T entity) {
		boolean rel = true;
		try {
			sqlMapClient.insert(sqlId, entity);
		} catch (Exception e) {
			rel = false;
			e.printStackTrace();
		}
		return rel;
	}

	@Override
	public void batchDeleteObjects(String sqlId, Map<String, Object> params) {
		try {
			sqlMapClient.delete(sqlId, params);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
