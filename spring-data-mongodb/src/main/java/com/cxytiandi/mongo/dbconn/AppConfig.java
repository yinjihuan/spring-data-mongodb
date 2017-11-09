package com.cxytiandi.mongo.dbconn;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
public class AppConfig {
	public @Bean MongoDbFactory mongoDbFactory() throws Exception {
		//mongodb地址，集群环境填多个
		List<ServerAddress> seeds = Arrays.asList(new ServerAddress("localhost", 27017));
		
		//用户认证信息，参数为用户，数据库，密码
		//MongoCredential com.mongodb.MongoCredential.createCredential(String userName, String database, char[] password)
		MongoCredential mongoCredential = MongoCredential.createCredential("cxytiandi", "cxytiandi", "cxytiandi".toCharArray());
		List<MongoCredential> credentialsList = Arrays.asList(mongoCredential);
		
		//连接池参数配置
		MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
		// 每个主机的连接数
		int connPerHost = 20;
		builder.connectionsPerHost(connPerHost);
		// 线程队列数
		int threadCount = 20;
		builder.threadsAllowedToBlockForConnectionMultiplier(threadCount);
		// 最大等待连接的线程阻塞时间（单位：毫秒）
		int maxWaitTime = 1000;
		builder.maxWaitTime(maxWaitTime);
		// 连接超时的时间。0是默认和无限（单位：毫秒）
		int timeOut = 1000;
		builder.connectTimeout(timeOut);

		MongoClientOptions options = builder.build();
		
		MongoClient mongoClient = new MongoClient(seeds, credentialsList, options);
		//这里第二个参数也就是cxytiandi是用户认证的库名,在哪个库认证就表示登陆哪个库
		return new SimpleMongoDbFactory(mongoClient, "cxytiandi");
	}

	public @Bean MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongoDbFactory());
	}

	public static void main(String[] args) throws UnknownHostException, Exception {
		AppConfig appConfig = new AppConfig();
		MongoTemplate mongoTemplate = appConfig.mongoTemplate();
		mongoTemplate.getCollectionNames().forEach(System.out::println);
	}

}
