package com.cxytiandi.mongo.gridfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

public class GridFsTest {
	private static GridFsTemplate gridFsTemplate;
	
	static {
		//加载spring
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		gridFsTemplate = (GridFsTemplate) ac.getBean("gridFsTemplate");
	}
	
	public static void main(String[] args)  throws Exception {
		//uploadFile();
		//getFile("57c17bb0d4c666b6e53ba795");
		//removeFile("57c17bb0d4c666b6e53ba795");
	}
	
	/**
	 * 上传文件
	 * @author yinjihuan
	 * @throws Exception
	 */
	public static void uploadFile() throws Exception {
		File file = new File("/Users/yinjihuan/Downlaods/logo.png");
		InputStream content = new FileInputStream(file);
		//存储文件的额外信息，比如用户ID,后面要查询某个用户的所有文件时就可以直接查询
		DBObject metadata = new BasicDBObject("userId", "1001");
		GridFSFile gridFSFile = gridFsTemplate.store(content, file.getName(), "image/png", metadata);
		String fileId = gridFSFile.getId().toString();
		System.out.println(fileId);
	}
	
	/**
	 * 根据文件ID查询文件
	 * @author yinjihuan
	 * @param fileId
	 * @return
	 * @throws Exception
	 */
	public static GridFSDBFile getFile(String fileId) throws Exception {
		return gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
	}
	
	/**
	 * 根据文件ID删除文件
	 * @author yinjihuan
	 * @param fileId
	 * @throws Exception
	 */
	public static void removeFile(String fileId) throws Exception {
		gridFsTemplate.delete(Query.query(Criteria.where("_id").is(fileId)));
	}
}
