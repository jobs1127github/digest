package com.tentinet.app.service.impl;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.compass.core.CompassTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tentinet.app.bean.MarkVo;
import com.tentinet.app.service.MarkVoService;

@Service(value="markVoServiceImpl") 
@Transactional
public class MarkVoServiceImpl implements MarkVoService{
	/**
	 * 注入compassTemplate，该bean已经在配置文件中以单例的形式被Spring注入。
	 */
	@Resource(name="compassTemplate")
	private CompassTemplate compassTemplate;
	/***
	 * 注入实体管理器类entityManager
	 * 持久化上下文
	 */
	@PersistenceContext
	protected EntityManager entityManager;
	
	@Override
	public QueryResult<MarkVo> query(String keyword,int firstResult,int maxResult) {
		return compassTemplate.execute(new QueryCallback(keyword,firstResult,maxResult));
	}

	@Override
	public void save_to_db(MarkVo m) {
		entityManager.persist(m);
	}

	@Override
	public void save_to_index(MarkVo m) {
		compassTemplate.save(m);
	}

	@Override
	public void update_to_db(MarkVo m) {
		entityManager.merge(m);
	}

}
