package org.github.bobobot.services.impl;

import org.apache.commons.validator.routines.EmailValidator;
import org.github.bobobot.dao.IUserDAO;
import org.github.bobobot.entities.*;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.services.IUserService;

import java.util.List;
import java.util.Optional;

public class UserService implements IUserService {

	private IUserDAO dao;

	public UserService(IUserDAO dao) {
		this.dao = dao;
	}

	private void validateEmail(String email) {
		EmailValidator validator = EmailValidator.getInstance();
		if (!validator.isValid(email)) throw new IllegalArgumentException("Hibás email cím!");
	}

	@Override
	public User register(User tempUser) {
		validateEmail(tempUser.getEmail());
		return dao.create(tempUser);
	}

	@Override
	public User register(boolean isAdmin, String name, String email, String passwordHash, List<Thread> threads, List<Reply> replies, List<CommentNotification> commentNotifications, List<VoteNotification> voteNotifications) {
		return register(new User(-1, isAdmin, name, email, passwordHash, threads, replies, commentNotifications, voteNotifications));
	}

	@Override
	public User register(boolean isAdmin, String name, String email, String passwordHash) {
		return register(new User(-1, isAdmin, name, email, passwordHash));

	}

	@Override
	public User update(User tempUser) {
		validateEmail(tempUser.getEmail());
		Optional<User> user = dao.update(tempUser);
		if (!user.isPresent()) { throw new IllegalArgumentException("User not found!"); }
		return user.get();
	}

	@Override
	public User update(int ID, boolean isAdmin, String name, String email, String passwordHash) {
		return update(new User(ID, isAdmin, name, email, passwordHash));
	}

	@Override
	public User update(int ID, boolean isAdmin, String name, String email, String passwordHash, List<Thread> threads, List<Reply> replies, List<CommentNotification> commentNotifications, List<VoteNotification> voteNotifications) {
		return update(new User(ID, isAdmin, name, email, passwordHash, threads, replies, commentNotifications, voteNotifications));
	}

	@Override
	public List<User> list() {
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
