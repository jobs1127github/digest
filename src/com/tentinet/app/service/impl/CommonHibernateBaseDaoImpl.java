package com.tentinet.app.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.tentinet.app.service.ICommonHibernateBaseDao;
/**
 * @Repository仓库，持久层
 * @author jobs1127
 *
 */
@Repository(value = "commonHibernateBaseDao")
public class CommonHibernateBaseDaoImpl implements ICommonHibernateBaseDao {
	
	/**
	 * <bean id="commonHibernateBaseDao"
		class="com.tentinet.app.service.impl.CommonHibernateBaseDaoImpl">
		<property name="hibernateTemplate" ref="hibernateTemplate"></property>
		</bean>
		在配置文件里注入了单例
	 */
	private HibernateTemplate hibernateTemplate;//默认Spring容器会出applicationContext.xml里找，可以不写,如果有多个实现类实现了该接口，则必须明确指定实现类名

	/***
	 * 注入实体管理器类entityManager
	 * 持久化上下文
	 */
	@PersistenceContext
	protected EntityManager entityManager;
	/***
	 * HibernateTemplate的说明：
	 * 在谈Spring事务管理之前我们想一下在我们不用Spring的时候，在Hibernate中我们是怎么进行数据操作的。
	 * 在Hibernate中我们每次进行一个操作的的时候我们都是要先开启事务，然后进行数据操作，然后提交事务，关闭事务，
	 * 我们这样做的原因是因为Hibernate默认的事务自动提交是false，他是需要我们人为的手动提交事务，
	 * 假如你不想每次都手动提交事务的话，你可以在hibernate.cfg.xml文件中把它设置为事务自动提交：
	 * <property name="hibernate.connection.autocommit">true</property> 
	 * 
	 * 当我们Spring对我们的Hibernate进行整合之后，我们不再是每次都去拿Session进行数据操作了，
	 * 也不需要每次都开启事务，提交事务了，我们只需要Spring给我们提供的一个HibernateTemplate，
	 * 我们直接用这个类里面给我们提供的数据操作方法就可以操作数据了。
	 * 有的人直接HibernateTemplate里面提供的方法操作数据，成功了，有的人却又失败了，这到底是怎么回事呢？
	 * 其实这里要看我们是怎样集成我们的Hibernate和Spring，如果在集成的过程中，
	 * 我们抛弃了hibernate.cfg.xml文件，直接在Spring的的配置文件中进行配置数据源的话，
	 * 那你直接用HibernateTemplate里面提供的方法是可以成功操作数据的，
	 * 如果你还是用hibernate.cfg.xml来配置数据源，在Spring的配置文件中引用hibernate.cfg.xml文件，
	 * 那么你不能成功，这其中的原因就是因为如果你用 hibernate.cfg.xml文件配置数据源，
	 * 就像我们前面说的，Hibernate默认是手动提交事务，
	 * 而HibernateTemplatel提供的方法里面并没有提供事务提交，
	 * 而如果你用Spring的配置文件来配置数据源，Sping默认是自动提交的，所以就会成功。
	 * 
	 * 有时候在我保存一个数据之后，我希望他能继续保存另一条数据，我希望在保存完两条或者多条之后一起进行事务提交，
	 * 这样即使出错，我们可以回滚，取保数据的一致性，要么都成功要么都失败，
	 * 这时候我们就不能每保存完一条数据之后事务就自动提交，因为这样它们不在同一个事务当中，
	 * 我们不能保证数据的一致行。所以这时候我们就需要手动的来配置我们的事务，
	 * 这就需要用到Spring为Hibernate提供的事务管理机制，
	 * Spring提供的事务管理可以分为两类：编程式的和声明式的，编程式，其实就是在代码里面来控制，
	 * 像Hibernate操作数据一样，开启事务，提交事务，这种方式有一定的局限性，
	 * 所以我们一般是用声明式来配置我们的事务。
	 * 
	 * 声明式事务配置 ：
	 * 1、配置事务管理器； 
	 * 2、事务的传播特性；
	 * 3、那些类那些方法使用事务。
	 */
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public <T> boolean save(T entity) {
		boolean result = false;
		try {
			hibernateTemplate.save(entity);
			//entityManager.persist(entity);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	public <T> boolean update(T entity) {

		boolean result = false;
		try {
			hibernateTemplate.update(entity);
			result = true;
		} catch (Exception e) {
			result=false;
		}
		return result;
	}

	public <T> boolean delete(T entity) {
		boolean result = false;
		try {
			hibernateTemplate.delete(entity);
			result = true;
		} catch (Exception e) {
			result=false;
		}
		return result;
	}

	/*public <T> T getEntityById(Class<T> type, long id) {
		try {
			return (T) hibernateTemplate.get(type, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/

	@Override
	public <T> T getEntityByLongId(Class<T> type, long id) {
		try {
			return (T) hibernateTemplate.get(type, id);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public <T> T getEntityByStringId(Class<T> type, String id) {
		try {
			return (T) hibernateTemplate.get(type, id);
		} catch (Exception e) {
			return null;
		}
	}
	
	public <T> boolean saveOrUpdate(T entity) {

		boolean result = false;
		try {
			hibernateTemplate.saveOrUpdate(entity);
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getList(String sql, Object... objects) {
		try{
			if(null == objects || objects.length <= 0){
				return this.hibernateTemplate.find(sql);	
			}else{
				return this.hibernateTemplate.find(sql, objects);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean updateAll(List<?> list) {
		boolean result = true;
		try{
			this.hibernateTemplate.saveOrUpdateAll(list);
		}catch (Exception e) {
			result = false;
		}
		return result;
	}
	
}
