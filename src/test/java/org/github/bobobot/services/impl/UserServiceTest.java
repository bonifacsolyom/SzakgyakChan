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
		ArrayList<Thread> threads = new ArrayList<>();
		ArrayList<Reply> replies = new ArrayList<>();
		ArrayList<Notification> notifications = new ArrayList<>();

		User originalUser = new User(0, true, "tesztNev", "tesztEmail", "tesztJelszo", threads, replies, notifications);

		service.create(true, "tesztNev", "tesztEmail", "tesztJelszo", threads, replies, notifications);
		User user = service.findByUsername("tesztNev");

		assertEquals(user, originalUser);

	}

	@Test
	void updateUserEmail() {
		UserService service = new UserService();
		ArrayList<Thread> threads = new ArrayList<>();
		ArrayList<Reply> replies = new ArrayList<>();
		ArrayList<Notification> notifications = new ArrayList<>();

		User user = service.create(true, "tesztNev", "tesztEmail", "tesztJelszo", threads, replies, notifications);
		int userID = user.getID();
		service.update(userID, true, "tesztNev", "tesztEmail2", "tesztJelszo", threads, replies, notifications);
		user = service.findById(userID);

		assertEquals(user.getEmail(), "tesztEmail2");
	}

	@Test
	void testList() {
		UserService service = new UserService();
		ArrayList<Thread> threads = new ArrayList<>();
		ArrayList<Reply> replies = new ArrayList<>();
		ArrayList<Notification> notifications = new ArrayList<>();

		User user1 = new User(0, true, "tesztNev1", "tesztEmail1", "tesztJelszo1", threads, replies, notifications);
		User user2 = new User(1, true, "tesztNev2", "tesztEmail2", "tesztJelszo2", threads, replies, notifications);

		service.create(true, "tesztNev1", "tesztEmail1", "tesztJelszo1", threads, replies, notifications);
		service.create(true, "tesztNev2", "tesztEmail2", "tesztJelszo2", threads, replies, notifications);

		ArrayList<User> userList = service.list();

		assertEquals(userList.get(0), user1);
		assertEquals(userList.get(1), user2);
	}



}