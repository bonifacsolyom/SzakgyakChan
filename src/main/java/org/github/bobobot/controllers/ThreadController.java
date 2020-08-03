package org.github.bobobot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.services.IThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@RestController
public class ThreadController {

	@Autowired
	private IThreadService service;

	@ExceptionHandler
	ModelAndView handleErrors(Exception e) {
		log.error("Error in thread controller: ", e);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error");
		modelAndView.addObject("message", e.getMessage());
		return modelAndView;
	}

	@GetMapping("/threads")
	ResponseEntity<List<Thread>> all() {
		log.info("Generating a list of all threads.");
		return ResponseEntity.ok(service.list());
	}

	@PostMapping("/threads")
	ResponseEntity<Thread> newThread(@RequestBody Thread thread) {
			log.info("Creating new thread with info: " + thread);
			return ResponseEntity.ok(service.create(thread));
	}

	@GetMapping("/thread/{id}")
	ResponseEntity<Thread> get(@PathVariable Long id) {
			log.info("Returning thread with id: " + id);
			return ResponseEntity.ok(service.findById(id));
	}

	@PutMapping("/thread/{id}")
	ResponseEntity<Thread> update(@RequestBody Thread thread, @PathVariable Long id) {
		thread.setId(id);
		log.info("Updating thread with info: " + thread);
		return ResponseEntity.ok(service.update(thread));
	}

	@DeleteMapping("/thread/{id}")
	ResponseEntity<Void> delete(@PathVariable Long id) {
		log.info("Deleting thread with id: " + id);
		service.delete(id);
		return ResponseEntity.ok().body(null);
	}
}
