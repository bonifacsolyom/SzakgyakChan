package org.github.bobobot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.services.IThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ThreadController {

	@Autowired
	IThreadService service;

	@GetMapping("/threads")
	ResponseEntity<List<Thread>> all() {
		try {
			ResponseEntity<List<Thread>> list = ResponseEntity.ok(service.list());
			log.info("Generated a list of all threads.");
			return list;
		} catch (IllegalArgumentException e) {
			log.error("Could not list threads: ", e);
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PostMapping("/threads")
	ResponseEntity<Thread> newThread(@RequestBody Thread thread) {
		try {
			ResponseEntity<Thread> createdThread = ResponseEntity.ok(service.create(thread));
			log.info("Created new thread with info: " + thread);
			return createdThread;
		} catch (IllegalArgumentException e) {
			log.error("Could not create new thread with info: " + thread, e);
			return ResponseEntity.badRequest().body(thread);
		}
	}

	@GetMapping("/thread/{id}")
	ResponseEntity<Thread> get(@PathVariable Long id) {
		try {
			ResponseEntity<Thread> foundThread = ResponseEntity.ok(service.findById(id));
			log.info("Returned thread with id: " + id);
			return foundThread;
		} catch (Exception e) {
			log.error("Could not get thread with id: " + id, e);
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PutMapping("/thread/{id}")
	ResponseEntity<Thread> update(@RequestBody Thread thread, @PathVariable Long id) {
		thread.setId(id);
		try {
			ResponseEntity<Thread> updatedThread = ResponseEntity.ok(service.update(thread));
			log.info("Updated thread with info: " + thread);
			return updatedThread;
		} catch (Exception e) {
			log.error("Could not update thread with info: " + thread, e);
			return ResponseEntity.badRequest().body(null);
		}
	}

	@DeleteMapping("/thread/{id}")
	ResponseEntity<Void> delete(@PathVariable Long id) {
		try {
			service.delete(id);
			log.info("Deleted thread with id: " + id);
			return ResponseEntity.ok().body(null);
		} catch (Exception e) {
			log.error("Could not delete thread: ", e);
			return ResponseEntity.badRequest().body(null);
		}
	}
}
