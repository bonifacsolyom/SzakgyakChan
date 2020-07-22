package org.github.bobobot.services.impl;

import org.apache.commons.validator.routines.EmailValidator;
import org.github.bobobot.dao.IUserDAO;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.*;
import org.github.bobobot.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public class UserService implements IUserService {

	private PasswordEncoder passwordEncoder;
	private final IUserDAO dao;

	public UserService(IUserDAO dao) {
		this.dao = dao;
	}

	public UserService(IUserDAO dao, PasswordEncoder passwordEncoder) {
		this.dao = dao;
		this.passwordEncoder = passwordEncoder;
	}

	private void validateEmail(String email) {
		EmailValidator validator = EmailValidator.getInstance();
		if (!validator.isValid(email)) throw new IllegalArgumentException("Hibás email cím!");
	}

	private User getUserIfPresent(Optional<User> user) {
		if (!user.isPresent()) {
			throw new IllegalArgumentException("User not found!");
		}
		return user.get();
	}

	@Override
	public User register(User tempUser) {
		validateEmail(tempUser.getEmail());
		//a tempUser passwordHash-je itt még csak az enkódolatlan jelszó
		tempUser.setPasswordHash(passwordEncoder.encode(tempUser.getPasswordHash()));
		return dao.create(tempUser);
	}

	@Override
	public User register(boolean isAdmin, String name, String email, String password, List<Thread> threads, List<Reply> replies, List<CommentNotification> commentNotifications, List<VoteNotification> voteNotifications) {
		return register(new User(-1, isAdmin, name, email, password, threads, replies, commentNotifications, voteNotifications));
	}

	@Override
	public User register(boolean isAdmin, String name, String email, String password) {
		return register(new User(-1, isAdmin, name, email, password));
	}

	@Override
	public Optional<User> login(String name, String password) {
		User user = findByUsername(name);
		if (passwordEncoder.matches(password, user.getPasswordHash())) return Optional.of(user);
		return Optional.empty();
	}

	@Override
	public User update(User tempUser) {
		validateEmail(tempUser.getEmail());
		Optional<User> user = dao.update(tempUser);
		return getUserIfPresent(user);
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
	public User addCommentNotification(int ID, CommentNotification notification) {
		Optional<User> optionalUser = dao.selectById(ID);
		User user = getUserIfPresent(optionalUser);
		user.addCommentNotification(notification);
		return update(user);
	}

	@Override
	public User addVoteNotification(int ID, VoteNotification notification) {
		Optional<User> optionalUser = dao.selectById(ID);
		User user = getUserIfPresent(optionalUser);
		user.addVoteNotification(notification);
		return update(user);
	}

	@Override
	public List<User> list() {
		return dao.list();
	}

	@Override
	public User findById(int ID) {
		Optional<User> user = dao.selectById(ID);
		return getUserIfPresent(user);
	}

	@Override
	public User findByUsername(String name) {
		Optional<User> user = dao.selectByUsername(name);
		return getUserIfPresent(user);
	}

	@Override
	public User findByEmail(String email) {
		Optional<User> user = dao.selectByEmail(email);
		return getUserIfPresent(user);
	}

	@Override
	public void delete(int ID) {
		Optional<User> user = dao.delete(ID);
		getUserIfPresent(user); //throw error if not found
	}


	@Override
	public List<Notification> getUsersNotifications(int ID) {
		User user = getUserIfPresent(dao.selectById(ID));
		return user.getNotifications();
	}

	@Override
	public List<Notification> getUsersActiveNotifications(int ID) {
		User user = getUserIfPresent(dao.selectById(ID));
		List<Notification> notifications = user.getNotifications();
		notifications.removeIf(n -> n.isRead());
		return notifications;
	}

	@Override
	public int getUsersNotificationCount(int ID) {
		return getUsersNotifications(ID).size();
	}

	@Override
	public int getUsersActiveNotificationCount(int ID) {
		return getUsersActiveNotifications(ID).size();
	}
}
