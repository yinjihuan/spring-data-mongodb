package com.cxytiandi.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.cxytiandi.mongo.document.Student;

public class AutoIdTest {
private static MongoTemplate mongoTemplate;
	
	static {
		//加载spring
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
	}
	
	public static void main(String[] args) {
		Student student = new Student();
		student.setName("yinjihuan");
		mongoTemplate.save(student);
	}
}
