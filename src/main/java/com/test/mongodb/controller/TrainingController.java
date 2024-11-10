package com.test.mongodb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.mongodb.model.Training;
import com.test.mongodb.repository.TrainingRepository;

@RestController
public class TrainingController {

	@Autowired
	TrainingRepository trainingRepository;

	@GetMapping("/trainings")
	public ResponseEntity<List<Training>> getAllTrainings(@RequestParam(required = false) String course) {

		try {
			List<Training> trainings = new ArrayList<Training>();
			if (course == null) {
				trainingRepository.findAll().forEach(trainings::add);
			} else {
				trainingRepository.findByCourseContaining(course).forEach(trainings::add);
			}

			if (trainings.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(trainings,HttpStatus.OK);

		} catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/trainings/{id}")
	public ResponseEntity<Training> getTrainingById(@PathVariable("id") String id) {
		Optional<Training> trainingData = trainingRepository.findById(id);

		if (trainingData.isPresent()) {
			return new ResponseEntity<>(trainingData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/trainings")
	public ResponseEntity<Training> createTraining(@RequestBody Training training) {
		try {
			Training newTraining = trainingRepository.save(new Training(training.getCourse(), training.getDescription(), training.isStarted()));
			return new ResponseEntity<>(newTraining, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/trainings/{id}")
	public ResponseEntity<Training> updateTraining(@PathVariable("id") String id, @RequestBody Training training) {
		Optional<Training> trainingData = trainingRepository.findById(id);

		if (trainingData.isPresent()) {
			Training newTraining = trainingData.get();
			newTraining.setCourse(training.getCourse());
			newTraining.setDescription(training.getDescription());
			newTraining.setStarted(training.isStarted());
			return new ResponseEntity<>(trainingRepository.save(newTraining), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/trainings/{id}")
	public ResponseEntity<HttpStatus> deleteTraining(@PathVariable("id") String id) {
		try {
			trainingRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/trainings")
	public ResponseEntity<HttpStatus> deleteAllTutorials() {
		try {
			trainingRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/trainings/started")
	public ResponseEntity<List<Training>> findByStarted() {
		try {
			List<Training> trainings = trainingRepository.findByStarted(true);

			if (trainings.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(trainings, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}






}
