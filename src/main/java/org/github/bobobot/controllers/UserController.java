package org.github.bobobot.controllers;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.entities.User;
import org.github.bobobot.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class UserController {

	@Autowired
	IUserService service;

	@GetMapping("/users")
	ResponseEntity<List<User>> all() {
		try {
			ResponseEntity<List<User>> list = ResponseEntity.ok(service.list());
			log.info("Generated a list of all users.");
			return list;
		} catch (IllegalArgumentException e) {
			log.error("Could not list users: ", e);
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PostMapping("/users")
	ResponseEntity<User> newUser(@RequestBody User user) {
		try {
			ResponseEntity<User> createdUser = ResponseEntity.ok(service.register(user));
			log.info("Created new user with info: " + user);
			return createdUser;
		} catch (IllegalArgumentException e) {
			log.error("Could not create new user with info: " + user, e);
			return ResponseEntity.badRequest().body(user);
		}
	}

	@GetMapping("/user/{id}")
	ResponseEntity<User> get(@PathVariable Long id) {
		try {
			ResponseEntity<User> foundUser = ResponseEntity.ok(service.findById(id));
			log.info("Returned user with id: " + id);
			return foundUser;
		} catch (Exception e) {
			log.error("Could not get user with id: " + id, e);
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PutMapping("/user/{id}")
	ResponseEntity<User> update(@RequestBody User user, @PathVariable Long id) {
		user.setID(id);
		try {
			ResponseEntity<User> updatedUser = ResponseEntity.ok(service.update(user));
			log.info("Updated user with info: " + user);
			return updatedUser;
		} catch (Exception e) {
			log.error("Could not update user with info: " + user, e);
			return ResponseEntity.badRequest().body(null);
		}
	}

	@DeleteMapping("/user/{id}")
	ResponseEntity<Void> delete(@PathVariable Long id) {
		try {
			service.delete(id);
			log.info("Deleted user with id: " + id);
			return ResponseEntity.ok().body(null);
		} catch (Exception e) {
			log.error("Could not delete user: ", e);
			return ResponseEntity.badRequest().body(null);
		}
	}
}
