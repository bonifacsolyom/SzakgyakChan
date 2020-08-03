package org.github.bobobot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.entities.User;
import org.github.bobobot.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@RestController
public class UserController {

	@Autowired
	private IUserService service;

	@ExceptionHandler
	ModelAndView handleErrors(Exception e) {
		log.error("Error in user controller: ", e);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error");
		modelAndView.addObject("message", e.getMessage());
		return modelAndView;
	}

	@GetMapping("/users")
	ResponseEntity<List<User>> all() {
		log.info("Generating a list of all users.");
		return ResponseEntity.ok(service.list());
	}

	@PostMapping("/users")
	ResponseEntity<User> newUser(@RequestBody User user) {
		log.info("Creating new user with info: " + user);
		return ResponseEntity.ok(service.register(user));
	}

	@GetMapping("/user/{id}")
	ResponseEntity<User> get(@PathVariable Long id) {
		log.info("Returning user with id: " + id);
		return ResponseEntity.ok(service.findById(id));
	}

	@PutMapping("/user/{id}")
	ResponseEntity<User> update(@RequestBody User user, @PathVariable Long id) {
		user.setId(id);
		log.info("Updating user with info: " + user);
		return ResponseEntity.ok(service.update(user));
	}

	@DeleteMapping("/user/{id}")
	ResponseEntity<Void> delete(@PathVariable Long id) {
		log.info("Deleting user with id: " + id);
		service.delete(id);
		return ResponseEntity.ok().body(null);
	}
}
