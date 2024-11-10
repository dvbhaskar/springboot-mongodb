package com.test.mongodb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.test.mongodb.model.Training;

public interface TrainingRepository extends MongoRepository <Training, String> {
	
	  List<Training> findByStarted(boolean started);
	  
	  List<Training> findByCourseContaining(String title);

}
