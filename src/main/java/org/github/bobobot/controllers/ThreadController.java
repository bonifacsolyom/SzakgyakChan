package org.github.bobobot.controllers;

import org.github.bobobot.entities.Thread;
import org.github.bobobot.services.IThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
		return service.create(thread);
	}

	@GetMapping("/thread/{id}")
	Thread get(@PathVariable Long id) {
		return service.findById(id);
	}

	@PutMapping("/thread/{id}")
	Thread update(@RequestBody Thread thread, @PathVariable Long id) {
		thread.setID(id);
		return service.update(thread);
	}

	@DeleteMapping("/threads/{id}")
	void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
