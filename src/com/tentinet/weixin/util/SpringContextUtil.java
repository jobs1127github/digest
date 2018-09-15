package com.tentinet.weixin.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 初始化ApplicationContext,通过ApplicationContext获取单例的bean对象
 * @author jobs1127
 *
 */
public class SpringContextUtil implements ApplicationContextAware{
	
	private static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext() {
		if(applicationContext==null){
			applicationContext=new ClassPathXmlApplicationContext("classpath:/spring/applicationContext.xml");
		}
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext=applicationContext;
	}
	
	public static Object getBean(String beanName){
		if(applicationContext==null){
			getApplicationContext();
		}
		return applicationContext.getBean(beanName);
	}
}
