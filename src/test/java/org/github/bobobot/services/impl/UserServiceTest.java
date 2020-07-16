package org.github.bobobot.services.impl;

import org.github.bobobot.dao.impl.InMemoryUserDAO;
import org.github.bobobot.entities.Notification;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;
import static org.github.bobobot.services.impl.TestHelperUtils.createDummyUser;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

	@Test
	void findCreatedUserByName() {
		UserService service = new UserService(new InMemoryUserDAO());

		User originalUser = createDummyUser();

		service.create(originalUser);
		User user = service.findByUsername("tesztNev");

		assertThat(user).isEqualTo(originalUser);
	}

	@Test
	void updateUserEmail() {
		UserService service = new UserService(new InMemoryUserDAO());

		User user = service.create(createDummyUser());
		int userID = user.getID();
		service.update(userID, true, "tesztNev", "tesztEmail2@teszt.com", "tesztJelszo");
		user = service.findById(userID);

		assertThat(user.getEmail()).isEqualTo("tesztEmail2@teszt.com");
	}

	@Test
	void testList() {
		UserService service = new UserService(new InMemoryUserDAO());

		User user1 = new User(0, true, "tesztNev1", "tesztEmail1@teszt.com", "tesztJelszo1");
		User user2 = new User(1, true, "tesztNev2", "tesztEmail2@teszt.com", "tesztJelszo2");

		service.create(true, "tesztNev1", "tesztEmail1@teszt.com", "tesztJelszo1");
		service.create(true, "tesztNev2", "tesztEmail2@teszt.com", "tesztJelszo2");

		ArrayList<User> userList = service.list();

		assertThat(userList.get(0)).isEqualTo(user1);
		assertThat(userList.get(1)).isEqualTo(user2);
	}

	@Test
	void testValidEmail() {
		UserService service = new UserService(new InMemoryUserDAO());
		User user = createDummyUser();

		assertThatCode(() -> service.create(user)).doesNotThrowAnyException();
	}

	@Test
	void testInvalidEmail() {
		UserService service = new UserService(new InMemoryUserDAO());
		User user = new User(0, true, "tesztNev", "tesztEmail", "tesztJelszo");

		assertThatIllegalArgumentException().isThrownBy(() -> service.create(user));
	}



}