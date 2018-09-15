package cn.jobs1127.test;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tentinet.app.bean.MarkVo;
import com.tentinet.app.service.MarkVoService;
import com.tentinet.app.service.impl.QueryResult;

public class TestCompass {
	MarkVoService markVoService = null;
	@Before
	public void before() {
		//启动Spring容器。
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		//获取业务接口
		markVoService = (MarkVoService)applicationContext.getBean("markVoServiceImpl");
	}
	
	/**
	 * 启动Spring容器，看看是否在d盘创建了index文件夹，如果创建了说明spring集成compass框架成功。
	 */
	@Test
	public void test_start_spring() {
		//观察markvo文件夹下的segments_1 为1，如果test_save_to_index保存成功，这个数会累加1.
	}
	/**
	 * 保存实体到索引文档中
	 */
	@Test
	public void test_save_to_index() {
		MarkVo m = new MarkVo();
		m.setMark_code(UUID.randomUUID().toString());
		m.setMark_name("全文检索 哈哈");
		markVoService.save_to_index(m);
		//观察markvo文件夹下的segments_2 ，从segments_1变成了segments_2。说明保存实体到索引文档成功。
	} 
	/***
	 * 保存实体到数据库中
	 */
	@Test
	public void test_save_to_db() {
		MarkVo m = new MarkVo();
		m.setMark_code(UUID.randomUUID().toString());
		m.setMark_name("全文检索 你妹 ");
		markVoService.save_to_db(m);
		//观察数据库，发现已经保存成功，观察markvo文件夹下的segments_3，从2变化到3，说明保存实体到数据库，
		//触发了gps保存该实体到索引文档中。说明compass的gps功能能正常工作。
	} 
	
	/***
	 * 保存实体到数据库中
	 */
	@Test
	public void test_update_to_db() {
		MarkVo m = new MarkVo();
		m.setMark_code("7f7f01f4-b487-4e0e-8405-40d07ef9e7b8");
		m.setMark_name("全文检索 你大爷 ");
		markVoService.update_to_db(m);
		//观察数据库，发现已经保存成功，观察markvo文件夹下的segments_3，从2变化到3，说明保存实体到数据库，
		//触发了gps保存该实体到索引文档中。说明compass的gps功能能正常工作。
	} 
	
	/***
	 * 搜索 索引 类似于百度的搜索 可以通过空格来把关键字隔开 联合查询
	 */
	@Test 
	public void test_query() {
		String keyword = "全文 哈哈";
		int firstResult = 0;
		int maxResult = 5;
		QueryResult<MarkVo> qr = markVoService.query(keyword, firstResult, maxResult);
		for(MarkVo m:qr.getQueryList()) {
			System.out.println(m.getMark_code()+"--"+m.getMark_name());
		}
	}
}
