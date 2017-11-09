package com.cxytiandi.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.cxytiandi.mongo.index.Person;
/**
 * 索引测试
 * @author yinjihuan
 *
 */
public class IndexTest {
	private static MongoTemplate mongoTemplate;
	
	static {
		//加载spring
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
	}
	
	public static void main(String[] args) {
		//addPerson();
		getIndexInfos();
	}
	
	public static void getIndexInfos() {
		mongoTemplate.getCollection("person").getIndexInfo().forEach( index -> {
			System.out.println(index);
		});
	}
	
	public static void addPerson() {
		Person person = new Person();
		person.setName("yinjihuan");
		person.setCity("上海");
		person.setRegion("虹口");
		person.setAge(25);
		mongoTemplate.save(person);
	}
}
