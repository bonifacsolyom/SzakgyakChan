package org.github.bobobot.dao.impl;

import org.github.bobobot.dao.UserDAO;
import org.github.bobobot.entities.Notification;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.User;

import java.util.ArrayList;
import java.util.Optional;

public class InMemoryUserDAO implements UserDAO {
	ArrayList<User> memory = new ArrayList<>();

	@Override
	public User create(boolean isAdmin, String name, String email, String passwordHash) {
		User user = new User(memory.size(), isAdmin, name, email, passwordHash);
		memory.add(user);
		return user;
	}

	@Override
	public Optional<User> update(int ID, boolean isAdmin, String name, String email, String passwordHash, ArrayList<Thread> threads, ArrayList<Reply> replies, ArrayList<Notification> notifications) {
		Optional<User> user = memory.stream()
				.filter(u -> u.getID() == ID)
				.findFirst();

		if (user.isPresent()) {
			user.get().setAdmin(isAdmin);
			user.get().setName(name);
			user.get().setEmail(email);
			user.get().setPasswordHash(passwordHash);
		}

		return user;
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
