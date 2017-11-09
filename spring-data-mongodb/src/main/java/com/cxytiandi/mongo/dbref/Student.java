package com.cxytiandi.mongo.dbref;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Student {
	@Id
	private String id;
	//学生姓名
	private String stuName;
	//引用班级
	@DBRef
	private Class classObj;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public Class getClassObj() {
		return classObj;
	}
	public void setClassObj(Class classObj) {
		this.classObj = classObj;
	}
	
}
