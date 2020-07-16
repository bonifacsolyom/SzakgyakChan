package org.github.bobobot.services.impl;

import org.github.bobobot.dao.IUserDAO;
import org.github.bobobot.dao.impl.InMemoryBoardDAO;
import org.github.bobobot.dao.impl.InMemoryUserDAO;
import org.github.bobobot.entities.*;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.services.IUserService;

import java.util.ArrayList;
import java.util.Optional;

public class UserService implements IUserService {

	private IUserDAO dao = new InMemoryUserDAO();

	@Override
	public User create(boolean isAdmin, String name, String email, String passwordHash, ArrayList<Thread> threads, ArrayList<Reply> replies, ArrayList<Notification> notifications) {
		User user = new User(-1, isAdmin, name, email, passwordHash, threads, replies, notifications);
		return dao.create(user);
	}

	public User create(boolean isAdmin, String name, String email, String passwordHash) {
		User user = new User(-1, isAdmin, name, email, passwordHash);
		return dao.create(user);
	}

	@Override
	public User update(int ID, boolean isAdmin, String name, String email, String passwordHash) {
		Optional<User> user = dao.update(new User(ID, isAdmin, name, email, passwordHash));
		if (!user.isPresent()) { throw new IllegalArgumentException("User not found!"); }
		return user.get();
	}

	@Override
	public User update(int ID, boolean isAdmin, String name, String email, String passwordHash, ArrayList<Thread> threads, ArrayList<Reply> replies, ArrayList<Notification> notifications) {
		Optional<User> user = dao.update(new User(ID, isAdmin, name, email, passwordHash, threads, replies, notifications));
		if (!user.isPresent()) { throw new IllegalArgumentException("User not found!"); }
		return user.get();
	}

	@Override
	public ArrayList<User> list() {
		return dao.list();
	}

	@Override
	public User findById(int ID) {
		Optional<User> user = dao.selectById(ID);
		if (!user.isPresent()) { throw new IllegalArgumentException("User not found!"); }
		return user.get();
	}

	@Override
	public User findByUsername(String name) {
		Optional<User> user = dao.selectByUsername(name);
		if (!user.isPresent()) { throw new IllegalArgumentException("User not found!"); }
		return user.get();
	}

	@Override
	public User findByEmail(String email) {
		Optional<User> user = dao.selectByEmail(email);
		if (!user.isPresent()) { throw new IllegalArgumentException("User not found!"); }
		return user.get();
	}

	@Override
	public void delete(int ID) {
		Optional<User> user = dao.delete(ID);
		if (!user.isPresent()) { throw new IllegalArgumentException("User not found!"); }
	}
}
