package org.github.bobobot.services.impl;

import org.github.bobobot.dao.impl.InMemoryUserDAO;
import org.github.bobobot.entities.Notification;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.github.bobobot.services.impl.TestHelperUtils.createDummyUser;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

	@Test
	void findCreatedUserByName() {
		UserService service = new UserService(new InMemoryUserDAO());

		User originalUser = createDummyUser();

		service.create(originalUser);
		User user = service.findByUsername("tesztNev");

		assertEquals(user, originalUser);

	}

	@Test
	void updateUserEmail() {
		UserService service = new UserService(new InMemoryUserDAO());

		User user = service.create(createDummyUser());
		int userID = user.getID();
		service.update(userID, true, "tesztNev", "tesztEmail2@teszt.com", "tesztJelszo");
		user = service.findById(userID);

		assertEquals(user.getEmail(), "tesztEmail2@teszt.com");
	}

	@Test
	void testList() {
		UserService service = new UserService(new InMemoryUserDAO());

		User user1 = new User(0, true, "tesztNev1", "tesztEmail1@teszt.com", "tesztJelszo1");
		User user2 = new User(1, true, "tesztNev2", "tesztEmail2@teszt.com", "tesztJelszo2");

		service.create(true, "tesztNev1", "tesztEmail1@teszt.com", "tesztJelszo1");
		service.create(true, "tesztNev2", "tesztEmail2@teszt.com", "tesztJelszo2");

		ArrayList<User> userList = service.list();

		assertEquals(userList.get(0), user1);
		assertEquals(userList.get(1), user2);
	}

	@Test
	void testValidEmail() {
		UserService service = new UserService(new InMemoryUserDAO());
		User user = createDummyUser();

		assertDoesNotThrow(() -> service.create(user));
	}

	@Test
	void testInvalidEmail() {
		UserService service = new UserService(new InMemoryUserDAO());
		User user = new User(0, true, "tesztNev", "tesztEmail", "tesztJelszo");

		assertThrows(IllegalArgumentException.class, () -> service.create(user));
	}



}