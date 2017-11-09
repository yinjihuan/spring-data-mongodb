package com.cxytiandi.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;

/**
 * 使用原始的java驱动语法来操作数据库
 * @author yinjihuan
 *
 */
public class MongoClient {
	private static DBCollection collection;
	
	static {
		//加载spring
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		MongoTemplate mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
		collection = mongoTemplate.getCollection("article_info");
	}
	
	public static void main(String[] args) {
		//添加数据
		collection.save(new BasicDBObject("author", "yinjihuan").append("title", "mongodb"));
		
		//编辑数据
		collection.update(new BasicDBObject("author", "yinjihuan"), 
					new BasicDBObject("$set", new BasicDBObject("title", "mongodb3.0")));
		
		//删除数据
		collection.remove(new BasicDBObject("author", "yinjihuan"));
		
		//查询所有数量
		long count = collection.count();
		
		//查询所有数据
		DBCursor cursor = collection.find();
		while (cursor.hasNext()) {
			DBObject data = cursor.next();
			System.out.println(data.toString());
		}
		
		//查询第一条数据
		DBObject data = collection.findOne();
		
		//带条件查询
		DBObject query = new BasicDBObject("author", "yinjihuan");
		cursor = collection.find(query);
		
		//大于（gt）查询
		query = new BasicDBObject("visit_count", new BasicDBObject("$gt", 10));
		cursor = collection.find(query);
		
		//小于（lt）查询
		query = new BasicDBObject("visit_count", new BasicDBObject("$lt", 10));
		cursor = collection.find(query);
		
		//查询固定的列
		query = new BasicDBObject("author", "yinjihuan");
		DBObject fields = new BasicDBObject("author", 1);
		cursor = collection.find(query, fields);
		
		//分页查询,当数据量较大的时候不建议使用skip，会很慢的
		//建议按id排序，每查询一页记住最后一条id,然后大于id在limit
		query = new BasicDBObject("author", "yinjihuan");
		cursor = collection.find(query, fields).skip(0).limit(10);
		
		//创建索引
		collection.createIndex(new BasicDBObject("author_idx", 1));
	}
	
}
