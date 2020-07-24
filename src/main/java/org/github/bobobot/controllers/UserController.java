package org.github.bobobot.controllers;

import org.github.bobobot.entities.User;
import org.github.bobobot.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
		return service.register(user);
	}

	@GetMapping("/user/{id}")
	User get(@PathVariable Long id) {
		return service.findById(id);
	}

	@PutMapping("/user/{id}")
	User update(@RequestBody User user, @PathVariable Long id) {
		user.setID(id);
		return service.update(user);
	}

	@DeleteMapping("/users/{id}")
	void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
