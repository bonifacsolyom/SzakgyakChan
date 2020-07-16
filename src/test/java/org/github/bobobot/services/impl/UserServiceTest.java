package org.github.bobobot.services.impl;

import org.github.bobobot.entities.Notification;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

	@Test
	void findCreatedUserByName() {
		UserService service = new UserService();

		User originalUser = new User(0, true, "tesztNev", "tesztEmail", "tesztJelszo");

		service.create(true, "tesztNev", "tesztEmail", "tesztJelszo");
		User user = service.findByUsername("tesztNev");

		assertEquals(user, originalUser);

	}

	@Test
	void updateUserEmail() {
		UserService service = new UserService();

		User user = service.create(true, "tesztNev", "tesztEmail", "tesztJelszo");
		int userID = user.getID();
		service.update(userID, true, "tesztNev", "tesztEmail2", "tesztJelszo");
		user = service.findById(userID);

		assertEquals(user.getEmail(), "tesztEmail2");
	}

	@Test
	void testList() {
		UserService service = new UserService();

		User user1 = new User(0, true, "tesztNev1", "tesztEmail1", "tesztJelszo1");
		User user2 = new User(1, true, "tesztNev2", "tesztEmail2", "tesztJelszo2");

		service.create(true, "tesztNev1", "tesztEmail1", "tesztJelszo1");
		service.create(true, "tesztNev2", "tesztEmail2", "tesztJelszo2");

		ArrayList<User> userList = service.list();

		assertEquals(userList.get(0), user1);
		assertEquals(userList.get(1), user2);
	}

	void testInvalidEmail() {
		UserService service = new UserService();

		User user1 = new User(0, true, "tesztNev1", "tesztEmail1", "tesztJelszo1");
	}



}