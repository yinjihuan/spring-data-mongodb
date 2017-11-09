package com.cxytiandi.mongo.dbref;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * 班级集合
 * @author yinjihuan
 *
 */
@Document
public class Class {
	@Id
	private String id;
	//班级名称
	private String className;
	//开班时间
	private Date openDate;
	//引用学生信息
	@DBRef
	private List<Student> students;
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	public List<Student> getStudents() {
		return students;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	
}
