package com.tentinet.app.service.impl;

import javax.annotation.Resource;

import org.compass.core.Compass;
import org.compass.core.CompassException;
import org.compass.core.CompassTemplate;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import cn.jobs1127.compass.productSearch.Product;

import com.tentinet.app.service.ProductService;
/**
 * 
 * @author Jobs1127
 * 声明事务
 */
@Transactional
public class ProductServiceImpl implements ProductService{
	/**
	 * 注入hibernateTemplate
	 */
	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 方式1：
	 * 这里直接使用配置文件中的compassTemplate实例
	 */
	@Resource(name="compassTemplate")
	private CompassTemplate compassTemplate;
	
	private Compass compass;

	public Compass getCompass() {
		return compass;
	}
	/***
	 * 方式2：
	 * 通过注入compass时，实例化CompassTemplate对象。
	 * @param compass
	 */
	@Resource
	public void setCompass(Compass compass) {
		compassTemplate = new CompassTemplate(compass);
	}
	
	/**
	 * 通过spring的事务，声明式事务管理，保证下面的2个操作在同一个事务中。
	 * 
	 * 这种方式是在保存实体对象到数据库时，同时在通过compassTemplate把实体bean在保存到索引文件中，
	 * 其实compass还提供了更加简单方便的方法，我们不用关心索引的创建和删除，通过compassGps来实现自动的创建和删除索引的功能。
	 */
	@Override
	public void save(Product p) {
		try {
			//1、保存对象p到数据库中。
			hibernateTemplate.save(p);
			//2、保存对象p到索引document文档中。
			compassTemplate.save(p);
		} catch (Exception e) {
			e.printStackTrace();
			hibernateTemplate.getSessionFactory().getCurrentSession().getTransaction().rollback();
		} 
	}

}
