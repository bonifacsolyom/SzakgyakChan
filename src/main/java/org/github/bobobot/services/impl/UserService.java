package org.github.bobobot.services.impl;

import org.apache.commons.validator.routines.EmailValidator;
import org.github.bobobot.access.PermissionHandler;
import org.github.bobobot.entities.CommentNotification;
import org.github.bobobot.entities.Notification;
import org.github.bobobot.entities.User;
import org.github.bobobot.entities.VoteNotification;
import org.github.bobobot.repositories.IUserRepository;
import org.github.bobobot.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class UserService implements IUserService {

	@Autowired
	private IUserRepository repository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private EmailValidator emailValidator;

	private void validateEmail(String email) {
		if (!emailValidator.isValid(email)) throw new IllegalArgumentException("Hibás email cím!");
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
	public Optional<User> login(String email, String password) {
		User user = findByEmail(email);
		if (passwordEncoder.matches(password, user.getPasswordHash())) {
			PermissionHandler.setCurrentUser(user);
			return Optional.of(user);
		}
		return Optional.empty();
	}

	@Override
	public User update(User tempUser) {
		getUserIfPresent(repository.findById(tempUser.getId())); //throw an error if it doesn't exist
		validateEmail(tempUser.getEmail());
		return repository.save(tempUser);
	}

	@Override
	@Transactional
	public User addCommentNotification(User tempUser, CommentNotification notification) {
		User user = findById(tempUser.getId());
		user.addCommentNotification(notification);
		return user;
	}

	@Override
	public User addVoteNotification(User user, VoteNotification notification) {
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
		getUserIfPresent(repository.findById(id)); //throw an error if it doesn't exist
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
