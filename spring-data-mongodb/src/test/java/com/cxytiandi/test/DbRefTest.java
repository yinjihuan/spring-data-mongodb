package com.cxytiandi.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.cxytiandi.mongo.dbref.Class;
import com.cxytiandi.mongo.dbref.Student;

/**
 * 集合关联测试类
 * @author yinjihuan
 *
 */
public class DbRefTest {
	private static MongoTemplate mongoTemplate;
	
	static {
		//加载spring
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
	}
	
	public static void main(String[] args) {
		//saveStudent();
		//findStudent();
		//initData();
		//manyToOneData();
		//findClass();
		findStudentsByClass();
	}
	
	private static void findStudentsByClass() {
		mongoTemplate.find(
				Query.query(Criteria.where("classObj.$id")
						.is(new ObjectId("57fa4b99d4c68bb7d044d616"))), Student.class)
		.forEach(stu -> {
			System.err.println(stu.getStuName());
		});
	}
	
	private static void findClass() {
		Class classObj = mongoTemplate.findOne(Query.query(Criteria.where("className").is("五年级一班")),Class.class);
		classObj.getStudents().forEach(stu -> {
			System.err.println(stu.getStuName());
		});
	}
	
	private static void manyToOneData() {
		Student student = new Student();
		student.setStuName("李学生");
		Class classObj = mongoTemplate.findOne(Query.query(Criteria.where("className").is("五年级一班")),Class.class);
		student.setClassObj(classObj);
		mongoTemplate.save(student);
		
		List<Student> students = classObj.getStudents();
		if (students == null) {
			students = new ArrayList<>();
		}
		students.add(student);
		classObj.setStudents(students);
		mongoTemplate.save(classObj);
	}
	
	/**
	 * 保存不了数据
	 * @author yinjihuan
	 */
	private static void initData() {
		Class classObj = new Class();
		classObj.setClassName("五年级一班");
		classObj.setOpenDate(new Date());
		
		List<Student> students = new ArrayList<>();
		Student student = new Student();
		student.setStuName("李学生");
		student.setClassObj(classObj);
		students.add(student);
		
		Student student2 = new Student();
		student2.setStuName("王学生");
		student2.setClassObj(classObj);
		students.add(student2);
		
		classObj.setStudents(students);
		mongoTemplate.save(student);
		mongoTemplate.save(student2);
		mongoTemplate.save(classObj);
		
	}
	
	private static void findStudent() {
		//自动带出班级信息
		Student student = mongoTemplate.findOne(Query.query(Criteria.where("stuName").is("张学生")), Student.class);
		System.out.println(student.getStuName() + "\t" + student.getClassObj().getClassName());
	}
	
	private static void saveStudent() {
		Class classObj = new Class();
		classObj.setClassName("五年级一班");
		classObj.setOpenDate(new Date());
		mongoTemplate.save(classObj);
		
		Student student = new Student();
		student.setStuName("张学生");
		
		student.setClassObj(classObj);
		
		mongoTemplate.save(student);
	}
}
