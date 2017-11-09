package com.cxytiandi.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationFunctionExpressions;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypeBasedAggregationOperationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import com.cxytiandi.mongo.result.ArticleResult;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import java.util.ArrayList;
import java.util.List;
/**
 * aggreate操作示列
 * @author yinjihuan
 *
 */
public class AggreateTest {
	private static MongoTemplate mongoTemplate;
	
	static {
		//加载spring
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
	}
	
	public static void main(String[] args) {
		//aggregation();
		aggreate();
	}
	
	/**
	 * 聚合使用
	 * 统计每个用户的文章数量
	 */
	private static void aggregation() {
		Aggregation agg = newAggregation(
			    group("author").count().as("count").first("author").as("name"),
			    project("name","count"),
			    sort(Direction.DESC, "count"),
			    match(Criteria.where("count").gt(0))
		);
		AggregationResults<ArticleResult> results = mongoTemplate.aggregate(agg, "article_info", ArticleResult.class);
		List<ArticleResult> tagCount = results.getMappedResults();
		for (ArticleResult studentResult : tagCount) {
			System.out.println(studentResult.getName() + "\t" + studentResult.getCount());
		}
	}
	
    /**
     * db.article_info.aggregate([
	    {
	        "$group": {
	            "_id": "$author",
	            "count": {
	                "$sum": 1
	            },
	            "name": {
	                "$first": "$author"
	            }
	        }
	    },
	    {
	        "$project": {
	            "name": 1,
	            "count": 1,
	            "_id": 0
	        }
	    },
	    {
	        "$match": {
	            "count": {
	                "$gt": 0
	            }
	        }
	    }
	]);
     * @author yinjihuan
     */
	private static void aggreate() {
		List<DBObject> pipeline = new ArrayList<DBObject>();
		BasicDBObject group = new BasicDBObject();
		group.put("$group", new  BasicDBObject("_id","$author")
			.append("count", new  BasicDBObject("$sum",1)).append("name", new BasicDBObject("$first","$author")));
		BasicDBObject project = new BasicDBObject();
		project.put("$project", new  BasicDBObject("name",1).append("count", 1).append("_id", 0));
		pipeline.add(group);
		pipeline.add(project);
		System.out.println(pipeline.toString());
		AggregationOutput output = mongoTemplate.getCollection("article_info").aggregate(pipeline);
		Iterable<DBObject> iterable = output.results();
		for (DBObject dbObject : iterable) {
			System.out.println(dbObject);
		}
	}
}
