package org.github.bobobot.services.impl;

import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.routines.EmailValidator;
import org.github.bobobot.dao.IUserDAO;
import org.github.bobobot.dao.impl.InMemoryBoardDAO;
import org.github.bobobot.dao.impl.InMemoryUserDAO;
import org.github.bobobot.entities.*;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.services.IUserService;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class UserService implements IUserService {

	private IUserDAO dao = new InMemoryUserDAO();


	private void validateEmail(String email) {
		EmailValidator validator = EmailValidator.getInstance();
		if (!validator.isValid(email)) throw new IllegalArgumentException("Hibás email cím!");
	}

	@Override
	public User create(User tempUser) {
		validateEmail(tempUser.getEmail());
		return dao.create(tempUser);
	}

	@Override
	public User create(boolean isAdmin, String name, String email, String passwordHash, ArrayList<Thread> threads, ArrayList<Reply> replies, ArrayList<Notification> notifications) {
		return create(new User(-1, isAdmin, name, email, passwordHash, threads, replies, notifications));
	}

	public User create(boolean isAdmin, String name, String email, String passwordHash) {
		return create(new User(-1, isAdmin, name, email, passwordHash));

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
	public User update(int ID, boolean isAdmin, String name, String email, String passwordHash, ArrayList<Thread> threads, ArrayList<Reply> replies, ArrayList<Notification> notifications) {
		return update(new User(ID, isAdmin, name, email, passwordHash, threads, replies, notifications));
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
