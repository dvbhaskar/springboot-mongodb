package com.test.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "trainings")
public class Training {
	
	@Id
	private String id;
	private String course;
	private String description;
	private boolean started;
	
	public Training() {
		
	}
	
	public Training(String course, String description, boolean started) {
		this.course = course;
		this.description = description;
		this.started = started;
	}
	
	public String getId() {
		return id;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}
	
	@Override
	public String toString() {
		return "Training [id=" + id + ", Course=" + course + ", desc=" + description + ", started=" + started + "]";
	}

}
