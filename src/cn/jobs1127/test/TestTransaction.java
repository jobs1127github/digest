package cn.jobs1127.test;

import java.util.Date;
import java.util.UUID;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tentinet.app.bean.MarkVo;
import com.tentinet.app.service.SystemService;

public class TestTransaction {
	@Test
	public void test() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		
		SystemService s = (SystemService)applicationContext.getBean("systemService");
		MarkVo m1 = new MarkVo();
		m1.setCreated_by(new Date() + "");
		m1.setCreated_time(new Date() + "");
		m1.setMark_code(UUID.randomUUID().toString());
		m1.setMark_name("测试BB哈哈16");
		m1.setUpdated_by(new Date() + "");
		m1.setUpdated_time(new Date() + "");
		
		MarkVo m2 = s.getMarkVoInfosById("f34c93bd-c2d1-467d-b6a4-90ccf14c55da");
		System.out.println("m2="+m2.getMark_name());
		
		s.savaMarkVo_test(m1, m2);
		//s.savaMarkVo(m1);
		System.out.println("end");
	}
}
