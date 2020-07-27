package org.github.bobobot.dao.impl;

import org.github.bobobot.dao.IUserDAO;
import org.github.bobobot.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryUserDAO implements IUserDAO {
	List<User> memory = new ArrayList<>();

	@Override
	public User create(User user) {
		user.setId((long) memory.size());
		memory.add(user);
		return user;
	}

	@Override
	public Optional<User> update(User user) {
		Optional<User> memoryUser = memory.stream()
				.filter(u -> u.getId() == user.getId())
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
	public Optional<User> selectById(int id) {
		Optional<User> user = memory.stream()
				.filter(u -> u.getId() == id)
				.findFirst();

		return user;
	}

	@Override
	public Optional<User> selectByUsername(String name) {
		Optional<User> user = memory.stream()
				.filter(u -> u.getName().equals(name))
				.findFirst();

		return user;
	}

	@Override
	public Optional<User> selectByEmail(String email) {
		Optional<User> user = memory.stream()
				.filter(u -> u.getEmail().equals(email))
				.findFirst();

		return user;
	}

	@Override
	public List<User> list() {
		return memory;
	}

	@Override
	public Optional<User> delete(int id) {
		Optional<User> user = memory.stream()
				.filter(u -> u.getId() == id)
				.findFirst();

		if (user.isPresent()) {
			memory.remove(user.get());
		}

		return user;
	}
}
