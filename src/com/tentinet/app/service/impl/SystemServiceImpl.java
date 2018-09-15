package com.tentinet.app.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tentinet.app.bean.DataDictionaryVo;
import com.tentinet.app.bean.MarkVo;
import com.tentinet.app.bean.QianDao;
import com.tentinet.app.service.ICommonHibernateBaseDao;
import com.tentinet.app.service.ICommonIbatiesBaseDao;
import com.tentinet.app.service.SystemService;
/**
 * 使用JPA的声明式事务管理。
 * 
 * @author Jobs1127
 */
@Transactional
@Service(value="systemService")
public class SystemServiceImpl implements SystemService {
	
	@Autowired
	private ICommonIbatiesBaseDao commonBatiesBaseDao;
	
	@Autowired
	private ICommonHibernateBaseDao commonHibernateBaseDao;
	
	
	/**
	 * 这里注入hibernateTemplate，是为了测试事务，在业务逻辑层来处理事务，粒度比DAO层大
	 */
	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
    
	public ICommonIbatiesBaseDao getCommonBatiesBaseDao() {
		return commonBatiesBaseDao;
	}


	public void setCommonBatiesBaseDao(ICommonIbatiesBaseDao commonBatiesBaseDao) {
		this.commonBatiesBaseDao = commonBatiesBaseDao;
	}


	public ICommonHibernateBaseDao getCommonHibernateBaseDao() {
		return commonHibernateBaseDao;
	}


	public void setCommonHibernateBaseDao(
			ICommonHibernateBaseDao commonHibernateBaseDao) {
		this.commonHibernateBaseDao = commonHibernateBaseDao;
	}


	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}


	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}


	/**
	 * 查找数据字典下拉
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DataDictionaryVo> loadinitDatas(Map<String, Object> params) {
		List<DataDictionaryVo> listDatas=null;
		try {
			listDatas=(List<DataDictionaryVo>) commonBatiesBaseDao.queryForList("system.loadinitDatas", params);
		} catch (Exception e) {
			listDatas=null;
		}
		return listDatas;
	}
    
 
	@Override
	public List<DataDictionaryVo> queryDataDictionaryInfos(
			Map<String, Object> params) {
		List<DataDictionaryVo> list=null;
		try {
			list=(List<DataDictionaryVo>) commonBatiesBaseDao.queryForList("system.queryDataDictionaryInfos", params);
		} catch (Exception e) {
			list=null;
		}
		return list;
	}

	@Override
	public boolean saveDataDictionary(DataDictionaryVo data) {
		boolean isResult=false;
		try {
			String sql = "from DataDictionaryVo where t_data_item = '"+data.getDataItem()+"'";
			List<DataDictionaryVo> list = commonHibernateBaseDao.getList(sql);
			if(list.isEmpty()) {
				commonHibernateBaseDao.save(data);
				isResult=true;
			} else {
				isResult=false;
			}
		} catch (Exception e) {
			isResult=false;
		}
		return isResult;
	}

	@Override
	public boolean updateDataDictionary(DataDictionaryVo data) {
		boolean isResult=false;
		try {
			String sql = "from DataDictionaryVo where t_data_item = '"+data.getDataItem()+"'";
			List<DataDictionaryVo> list = commonHibernateBaseDao.getList(sql);
			if(list.isEmpty()) {
				commonHibernateBaseDao.update(data);
				isResult=true;
			} else if (list.size()==1) {
				commonHibernateBaseDao.update(data);
				isResult=true;
			}else {
				isResult=false;
			}
		} catch (Exception e) {
			isResult=false;
		}
		return isResult;
	}

	@Override
	public boolean deleteDataDictionary(DataDictionaryVo data) {
		boolean isResult=false;
		try {
			commonHibernateBaseDao.update(data);
			isResult=true;
		} catch (Exception e) {
			isResult=false;
		}
		return isResult;
	}

	@Override
	public Integer queryDataDictionaryCount(Map<String, Object> params) {
		Integer count=0;
		try {
			count=commonBatiesBaseDao.queryForCount("system.queryDataDictionaryCount", params);
		} catch (Exception e) {
			 count=0;
		}
		return count;
	}

	@Override
	public DataDictionaryVo getDataDictionaryVoInfosById(String datakey) {
		DataDictionaryVo data=null;
		try {
			data=commonHibernateBaseDao.getEntityByStringId(DataDictionaryVo.class, datakey);
		} catch (Exception e) {
			data=null;
		}
		return data;
	}
	
	/**
	 * 标签查询
	 */
	@SuppressWarnings("unchecked")
	public List<DataDictionaryVo> loadinitMark(Map<String, Object> params) {
		List<DataDictionaryVo> listDatas=null;
		try {
			listDatas=(List<DataDictionaryVo>) commonBatiesBaseDao.queryForList("system.loadinitMark", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listDatas;
	}
	
	@SuppressWarnings("unchecked")
	public List<DataDictionaryVo> loadinitExpert(Map<String, Object> params) {
		List<DataDictionaryVo> listDatas=null;
		try {
			listDatas=(List<DataDictionaryVo>) commonBatiesBaseDao.queryForList("system.loadinitExpert", params);
		} catch (Exception e) {
			listDatas=null;
		}
		return listDatas;
	}
	
	@SuppressWarnings("unchecked")
	public List<DataDictionaryVo> loadinitGroup(Map<String, Object> params) {
		List<DataDictionaryVo> listDatas=null;
		try {
			listDatas=(List<DataDictionaryVo>) commonBatiesBaseDao.queryForList("system.loadinitGroup", params);
		} catch (Exception e) {
			listDatas=null;
		}
		return listDatas;
	}


	@Override
	public Integer queryBiaoQianCount(Map<String, Object> params) {
		Integer count=0;
		try {
			count=commonBatiesBaseDao.queryForCount("system.queryBiaoQianCount", params);
		} catch (Exception e) {
			 count=0;
		}
		return count;
	}


	@Override
	public MarkVo getMarkVoInfosById(String datakey) {
		MarkVo data=null;
		try {
			data=commonHibernateBaseDao.getEntityByStringId(MarkVo.class, datakey);
		} catch (Exception e) {
			data=null;
		}
		return data;
	}


	@Override
	public boolean updateMarkVo(String mark_code,String mark_name) {
		boolean isResult=false;
		try {
			MarkVo m = commonHibernateBaseDao.getEntityByStringId(MarkVo.class, mark_code);
			m.setMark_name(mark_name);
			commonHibernateBaseDao.update(m);
			isResult=true;
		} catch (Exception e) {
			isResult=false;
		}
		return isResult;
	}


	@Override
	public boolean deleteMarkVo(MarkVo data) {
		boolean isResult=false;
		try {
			commonHibernateBaseDao.delete(data);
			isResult=true;
		} catch (Exception e) {
			isResult=false;
		}
		return isResult;
	}

	@Override
	public boolean savaMarkVo(MarkVo data) {
		boolean isResult=false;
		try {
			commonHibernateBaseDao.save(data);
			isResult=true;
		} catch (Exception e) {
			isResult=false;
		}
		return isResult;
		
	}


	@Override
	public int getCountQiandao(String andsql) {
		String sql = " select count(*) from QianDao t where 1=1 "+andsql+" group by t.openid";
		List<QianDao> list = commonHibernateBaseDao.getList(sql);
		if(list != null && !list.isEmpty()) {
			return list.size();
		}
		return 0;
	}


	@Override
	public List<QianDao> getQiandaolist(String andsql) {
		List<QianDao> list = null;
		String sql = " from QianDao t where 1=1 "+andsql;
		list = commonHibernateBaseDao.getList(sql);
		return list;
	}


	@Override
	public void updateQiandao(QianDao qd) {
		qd.setCount(0);
		qd.setLjcount(qd.getLjcount()==null?1:qd.getLjcount()+1);
		commonHibernateBaseDao.update(qd);
	}


	@Override
	public QianDao getQianDaoByOpenId(String openid) {
		return commonHibernateBaseDao.getEntityByStringId(QianDao.class, openid);
	}

	/***
	 * savaMarkVo_test()一个方法里，有2个业务操作，2个动作，同时在一个事务里
	 * 这里是用session来手动开事务，即声明式事务的编程式事务。
	 * 必须配置：
	 * <!-- Hibernate框架session会话与当前线程绑定 -->
				<prop key="hibernate.current_session_context_class">
					thread
				</prop>
	 */
	//@Override
	public boolean savaMarkVo_test2(MarkVo data, MarkVo data2) {
		boolean isResult=false;
		try {
			Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.save(data);
			/**
			 * 模拟运行时异常，测试如果出现异常，是否回滚事务，正常情况下是，上面的保存应该回滚。
			 * 如果5/1则可以正常操作2个动作，1、保存，2、删除，不出现异常，要么同时完成，要么同时失败。
			 */
			System.out.println(5/0);
			session.delete(data2);
			session.getTransaction().commit();
			isResult=true;
		} catch (Exception e) {
			e.printStackTrace();
			isResult=false;
		}
		return isResult;
	}
	
	@Transactional
	/***
	 * 这里没有手动设置事务，使用声明式事务的xml方式。
	 * <import resource="classpath:spring/transaction-hibernate.xml"/>
	 * 这时候可以把相应方法里事务开起和提交的语句去掉，让Spring来管理事务。
	 * 通过Spring的AOP面向方面编程，植入事务。这个时候必须注释掉如下语句：
	 * 必须把hibernate的 hibernate.current_session_context_class 这项属性去掉！
	 * 否则事务是不起效的！！
	 * 也就是hibernate配置文件的<property name="current_session_context_class">thread</property> 
	 * 或者Spring配置文件的 <prop key="hibernate.current_session_context_class">thread</prop>
	 * 
	 * 不管是通过xml配置的事务，还是有注解的事务，这种方式都是碰到异常后立马提交了，并没有把两个操作放在同一个
	 * 事务中，如果发现异常要回滚的话，必须手动回滚。
	 */
	//@Override
	public boolean savaMarkVo_test3(MarkVo data, MarkVo data2) {
		boolean isResult=false;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			session.save(data);
			/**
			 * 模拟运行时异常，测试如果出现异常，是否回滚事务，正常情况下是，上面的保存应该回滚。
			 * 如果5/1则可以正常操作2个动作，1、保存，2、删除，不出现异常，要么同时完成，要么同时失败。
			 */
			System.out.println(5/0);
			session.delete(data2);
			isResult=true;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			isResult=false;
		}
		return isResult;
	}
	/**
	 * 使用模板设计模式的Spring提供的 hibernateTemplate对象来curd，
	 * 这个事务是Spring提供的，不需要设置什么配置，只需要数据源是Spring里配置的就行。
	 * 得到的结果和声明式事务是不一样的。
	 */
	@Override
	public boolean savaMarkVo_test(MarkVo data, MarkVo data2) {
		boolean isResult=false;
		try {
			commonHibernateBaseDao.save(data);
			/***事务在***/
			/**
			 * 模拟运行时异常，测试如果出现异常，是否回滚事务，正常情况下是，上面的保存应该回滚。
			 * 如果5/1则可以正常操作2个动作，1、保存，2、删除，不出现异常，要么同时完成，要么同时失败。
			 */
			System.out.println(5/0);
			commonHibernateBaseDao.delete(data2);
			isResult=true;
		} catch (Exception e) {
			/**
			 * 出现异常后，即使在这里回滚也没用，
			 * 因为事务在commonHibernateBaseDao.save(data);的周围开启和结束，
			 */
			hibernateTemplate.getSessionFactory().getCurrentSession().getTransaction().rollback();
			e.printStackTrace();
			isResult=false;
		}
		return isResult;
	}


	@Override
	public void updateMark(MarkVo m) {
		hibernateTemplate.update(m);
	}
}
