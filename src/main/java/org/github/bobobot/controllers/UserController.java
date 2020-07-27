package org.github.bobobot.controllers;

import lombok.extern.java.Log;
import org.github.bobobot.entities.User;
import org.github.bobobot.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
public class UserController {

	@Autowired
	IUserService service;

	@GetMapping("/users")
	List<User> all() {
		return service.list();
	}

	@PostMapping("/users")
	User newUser(@RequestBody User user) {
		log.info("Created new user with info: " + user);
		return service.register(user);
	}

	@GetMapping("/user/{id}")
	User get(@PathVariable Long id) {
		return service.findById(id);
	}

	@PutMapping("/user/{id}")
	User update(@RequestBody User user, @PathVariable Long id) {
		user.setID(id);
		log.info("Updated user with info: " + user);
		return service.update(user);
	}

	@DeleteMapping("/user/{id}")
	void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
