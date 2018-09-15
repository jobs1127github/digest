package cn.jobs1127.test;

import java.util.Date;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tentinet.app.bean.MarkVo;

public class TestJpa {
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	@Before
	public void init(){
		/**
		 * createEntityManager()：用于创建实体管理器对象实例。
		 * createEntityManager(Map map)：用于创建实体管理器对象实例的重载方法，Map 参数用于提供 EntityManager 的属性。
		 */
		entityManagerFactory = Persistence.createEntityManagerFactory("jpa-jobs1127");
		entityManager = entityManagerFactory.createEntityManager();
		
	}
	@After
	public void destroy(){
		entityManager.close();
		entityManagerFactory.close();
	}
	
	/**
	 * 类似于 hibernate 的 save 方法. 使对象由临时状态变为持久化状态. 
	 * 和 hibernate 的 save 方法的不同之处: 若对象有 id, 则不能执行 insert操作, 而会抛出异常. 
	 */
	@Test
	public void testPersistence(){
		MarkVo m = new MarkVo();
		m.setMark_code(UUID.randomUUID().toString());
		m.setMark_name("哈哈背景3");
		/**
		 * 不开启事务是不会执行持久化操作的，
		 * 可以猜测这个entityManager对象类似Hibernate的getCurrentSession()的session对象
		 */
		entityManager.getTransaction().begin();
		entityManager.persist(m);
		entityManager.getTransaction().commit();
	}
}
