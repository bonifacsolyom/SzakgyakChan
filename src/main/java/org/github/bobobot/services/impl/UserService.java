package org.github.bobobot.services.impl;

import org.apache.commons.validator.routines.EmailValidator;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.*;
import org.github.bobobot.repositories.IUserRepository;
import org.github.bobobot.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

public class UserService implements IUserService {

	@Autowired
	private IUserRepository repository;
	@Autowired
	private PasswordEncoder passwordEncoder;

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
		tempUser.setPasswordHash(passwordEncoder.encode(tempUser.getPasswordHash()));
		return repository.save(tempUser);
	}

	@Override
	public User register(boolean isAdmin, String name, String email, String password, List<Thread> threads, List<Reply> replies, List<CommentNotification> commentNotifications, List<VoteNotification> voteNotifications) {
		return register(new User(isAdmin, name, email, password, threads, replies, commentNotifications, voteNotifications));
	}

	@Override
	public User register(boolean isAdmin, String name, String email, String password) {
		return register(new User(isAdmin, name, email, password));
	}

	@Override
	public Optional<User> login(String name, String password) {
		User user = findByUsername(name);
		if (passwordEncoder.matches(password, user.getPasswordHash())) return Optional.of(user);
		return Optional.empty();
	}

	@Override
	public User update(User tempUser) {
		getUserIfPresent(repository.findById(tempUser.getId())); //dobjunk errort ha nem létezik
		validateEmail(tempUser.getEmail());
		tempUser.setPasswordHash(passwordEncoder.encode(tempUser.getPasswordHash()));
		return repository.save(tempUser);
	}

	@Override
	public User update(Long id, boolean isAdmin, String name, String email, String passwordHash) {
		return update(new User(id, isAdmin, name, email, passwordHash));
	}

	@Override
	public User update(Long id, boolean isAdmin, String name, String email, String passwordHash, List<Thread> threads, List<Reply> replies, List<CommentNotification> commentNotifications, List<VoteNotification> voteNotifications) {
		return update(new User(id, isAdmin, name, email, passwordHash, threads, replies, commentNotifications, voteNotifications));
	}

	@Override
	public User addCommentNotification(Long id, CommentNotification notification) {
		Optional<User> optionalUser = repository.findById(id);
		User user = getUserIfPresent(optionalUser);
		user.addCommentNotification(notification);
		return update(user);
	}

	@Override
	public User addVoteNotification(Long id, VoteNotification notification) {
		Optional<User> optionalUser = repository.findById(id);
		User user = getUserIfPresent(optionalUser);
		user.addVoteNotification(notification);
		return update(user);
	}

	@Override
	public List<User> list() {
		return repository.findAll();
	}

	@Override
	public User findById(Long id) {
		Optional<User> user = repository.findById(id);
		return getUserIfPresent(user);
	}

	@Override
	public User findByUsername(String name) {
		Optional<User> user = repository.findByName(name);
		return getUserIfPresent(user);
	}

	@Override
	public User findByEmail(String email) {
		Optional<User> user = repository.findByEmail(email);
		return getUserIfPresent(user);
	}

	@Override
	public void delete(Long id) {
		getUserIfPresent(repository.findById(id)); //dobjunk errort ha nem létezik
		repository.deleteById(id);
	}


	@Override
	public List<Notification> getUsersNotifications(Long id) {
		User user = getUserIfPresent(repository.findById(id));
		return user.getNotifications();
	}

	@Override
	public List<Notification> getUsersActiveNotifications(Long id) {
		User user = getUserIfPresent(repository.findById(id));
		List<Notification> notifications = user.getNotifications();
		notifications.removeIf(n -> n.isRead());
		return notifications;
	}

	@Override
	public int getUsersNotificationCount(Long id) {
		return getUsersNotifications(id).size();
	}

	@Override
	public int getUsersActiveNotificationCount(Long id) {
		return getUsersActiveNotifications(id).size();
	}
}
