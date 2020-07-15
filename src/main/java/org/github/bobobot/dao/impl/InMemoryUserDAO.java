package org.github.bobobot.dao.impl;

import org.github.bobobot.dao.UserDAO;
import org.github.bobobot.entities.User;

import java.util.ArrayList;
import java.util.Optional;

public class InMemoryUserDAO implements UserDAO {
	ArrayList<User> memory = new ArrayList<>();

	@Override
	public User create(User user) {
		user.setID(memory.size());
		memory.add(user);
		return user;
	}

	@Override
	public Optional<User> update(User user) {
		Optional<User> memoryUser = memory.stream()
				.filter(u -> u.getID() == user.getID())
				.findFirst();

		if (memoryUser.isPresent()) {
			memoryUser.get().setAdmin(user.isAdmin());
			memoryUser.get().setName(user.getName());
			memoryUser.get().setEmail(user.getEmail());
			memoryUser.get().setPasswordHash(user.getPasswordHash());
		}

		return memoryUser;
	}

	@Override
	public Optional<User> select(int ID) {
		Optional<User> user = memory.stream()
				.filter(u -> u.getID() == ID)
				.findFirst();

		return user;
	}

	@Override
	public Optional<User> delete(int ID) {
		Optional<User> user = memory.stream()
				.filter(u -> u.getID() == ID)
				.findFirst();

		if (user.isPresent()) {
			memory.remove(user.get());
		}

		return user;
	}
}
