package com.cxytiandi.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.cxytiandi.mongo.batchupdate.BathUpdateOptions;
import com.cxytiandi.mongo.batchupdate.MongoBaseDao;
import com.cxytiandi.mongo.document.Article;
/**
 * 批量更新测试类
 * @author yinjihuan
 *
 */
public class BatchUpdateTest {
	
	private static MongoTemplate mongoTemplate;
	
	static {
		//加载spring
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
	}
	
	/**
	 * db.runCommand(
	   	{
	      update: "article_info",
	      updates: [
	         { q: { author: "jason" }, u: { $set: { title: "批量更新" } }, multi: true },
	         { q: { author: "yinjihuan"}, u: { $set: { title: "批量更新" }}, upsert: true }
	      ],
	      ordered: false
	   	}
	   )
	 * @author yinjihuan
	 * @param args
	 */
	public static void main(String[] args) {
		List<BathUpdateOptions> list = new ArrayList<BathUpdateOptions>();
		list.add(new BathUpdateOptions(Query.query(Criteria.where("author").is("yinjihuan")),Update.update("title", "批量更新"), true, true));
		list.add(new BathUpdateOptions(Query.query(Criteria.where("author").is("jason")),Update.update("title", "批量更新"), true, true));
		int n = MongoBaseDao.bathUpdate(mongoTemplate, Article.class, list);
		System.out.println("受影响的行数："+n);
	}
}
