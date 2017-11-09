package com.cxytiandi.mongo.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.cxytiandi.mongo.autoid.GeneratedValue;

@Document
public class Student {
	@GeneratedValue
	@Id
	private long id;
	private String name;
	public void setId(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
