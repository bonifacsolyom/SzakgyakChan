package org.github.bobobot.services.impl;

import org.github.bobobot.config.ApplicationConfig;
import org.github.bobobot.entities.User;
import org.github.bobobot.services.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.github.bobobot.services.impl.TestHelperUtils.createDummyUser;

@ActiveProfiles("test")
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ContextConfiguration(classes = ApplicationConfig.class)
class UserServiceTest {

	@Autowired
	private TestEntityManager em;

	@Autowired
	private IUserService service;

	@Test
	void findCreatedUserByName() {
		User originalUser = createDummyUser();

		em.persist(originalUser);
		service.register(originalUser);
		User user = service.findByUsername("tesztNev");

		assertThat(user).isEqualTo(originalUser);
	}

	@Test
	void updateUserEmail() {
		User user = service.register(createDummyUser());
		em.persist(user);
		Long userID = user.getId();
		service.update(userID, true, "tesztNev", "tesztEmail2@teszt.com", "tesztJelszo");
		user = service.findById(userID);

		assertThat(user.getEmail()).isEqualTo("tesztEmail2@teszt.com");
	}

	@Test
	void testList() {
		User user1 = new User(true, "tesztNev1", "tesztEmail1@teszt.com", "tesztJelszo1");
		User user2 = new User(true, "tesztNev2", "tesztEmail2@teszt.com", "tesztJelszo2");

		service.register(true, "tesztNev1", "tesztEmail1@teszt.com", "tesztJelszo1");
		service.register(true, "tesztNev2", "tesztEmail2@teszt.com", "tesztJelszo2");

		List<User> userList = service.list();

		assertThat(userList.get(0).getName()).isEqualTo(user1.getName());
		assertThat(userList.get(1).getName()).isEqualTo(user2.getName());
	}

	@Test
	void testValidEmail() {
		User user = createDummyUser();

		assertThatCode(() -> service.register(user)).doesNotThrowAnyException();
	}

	@Test
	void testInvalidEmail() {
		User user = new User(true, "tesztNev", "tesztEmail", "tesztJelszo");

		assertThatIllegalArgumentException().isThrownBy(() -> service.register(user));
	}

	@Test
	void testPasswordEncoding() {
		User user = createDummyUser();
		User newUser = new User(user);

		service.register(user);
		Optional<User> loggedInUser = service.login(newUser.getName(), newUser.getPasswordHash()); //A passwordHash itt még csak a sima jelszó
		assertThat(loggedInUser.get().getName()).isEqualTo(newUser.getName());
	}
}