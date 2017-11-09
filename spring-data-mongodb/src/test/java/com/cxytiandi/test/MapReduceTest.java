package com.cxytiandi.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import com.cxytiandi.mongo.result.ValueObject;

public class MapReduceTest {
	
	private static MongoTemplate mongoTemplate;
	
	static {
		//加载spring
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
	}
	
	public static void main(String[] args) {
		complexMapreduce();
	}
	
	private static void complexMapreduce() {
		MapReduceOptions options = MapReduceOptions.options();
		options.outputCollection("Article_MapReduce");
		options.outputTypeReduce();
		options.finalizeFunction("classpath:finalize.js");
		MapReduceResults<ValueObject> reduceResults = mongoTemplate.mapReduce("article_info",  "classpath:map.js",  
				"classpath:reduce.js", options, ValueObject.class);
		reduceResults.forEach(System.out::println);
	}
}
