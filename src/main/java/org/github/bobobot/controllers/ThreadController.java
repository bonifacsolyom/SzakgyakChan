package org.github.bobobot.controllers;

import lombok.extern.java.Log;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.services.IThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
public class ThreadController {

	@Autowired
	IThreadService service;

	@GetMapping("/threads")
	List<Thread> all() {
		return service.list();
	}

	@PostMapping("/threads")
	Thread newThread(@RequestBody Thread thread) {
		log.info("Created new thread with info: " + thread);
		return service.create(thread);
	}

	@GetMapping("/thread/{id}")
	Thread get(@PathVariable Long id) {
		return service.findById(id);
	}

	@PutMapping("/thread/{id}")
	Thread update(@RequestBody Thread thread, @PathVariable Long id) {
		thread.setID(id);
		log.info("Updated thread with info: " + thread);
		return service.update(thread);
	}

	@DeleteMapping("/thread/{id}")
	void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
