//package org.github.bobobot.services.impl;
//
//import org.github.bobobot.dao.impl.InMemoryUserDAO;
//import org.github.bobobot.entities.User;
//import org.junit.jupiter.api.Test;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.github.bobobot.services.impl.TestHelperUtils.createDummyUser;
//import static org.github.bobobot.services.impl.TestHelperUtils.createUserService;
//
//class UserServiceTest {
//
//	@Test
//	void findCreatedUserByName() {
//		UserService service = createUserService();
//
//		User originalUser = createDummyUser();
//
//		service.register(originalUser);
//		User user = service.findByUsername("tesztNev");
//
//		assertThat(user).isEqualTo(originalUser);
//	}
//
//	@Test
//	void updateUserEmail() {
//		UserService service = createUserService();
//
//		User user = service.register(createDummyUser());
//		int userID = user.getID();
//		service.update(userID, true, "tesztNev", "tesztEmail2@teszt.com", "tesztJelszo");
//		user = service.findById(userID);
//
//		assertThat(user.getEmail()).isEqualTo("tesztEmail2@teszt.com");
//	}
//
//	@Test
//	void testList() {
//		UserService service = createUserService();
//
//		User user1 = new User(0, true, "tesztNev1", "tesztEmail1@teszt.com", "tesztJelszo1");
//		User user2 = new User(1, true, "tesztNev2", "tesztEmail2@teszt.com", "tesztJelszo2");
//
//		service.register(true, "tesztNev1", "tesztEmail1@teszt.com", "tesztJelszo1");
//		service.register(true, "tesztNev2", "tesztEmail2@teszt.com", "tesztJelszo2");
//
//		List<User> userList = service.list();
//
//		assertThat(userList.get(0)).isEqualTo(user1);
//		assertThat(userList.get(1)).isEqualTo(user2);
//	}
//
//	@Test
//	void testValidEmail() {
//		UserService service = createUserService();
//		User user = createDummyUser();
//
//		assertThatCode(() -> service.register(user)).doesNotThrowAnyException();
//	}
//
//	@Test
//	void testInvalidEmail() {
//		UserService service = createUserService();
//		User user = new User(0, true, "tesztNev", "tesztEmail", "tesztJelszo");
//
//		assertThatIllegalArgumentException().isThrownBy(() -> service.register(user));
//	}
//
//	@Test
//	void testPasswordEncoding() {
//		UserService service = new UserService(new InMemoryUserDAO(), new BCryptPasswordEncoder());
//		User user = createDummyUser();
//		User newUser = new User(user);
//		newUser.setID(0);
//
//		service.register(user);
//		Optional<User> loggedInUser = service.login(newUser.getName(), newUser.getPasswordHash()); //A passwordHash itt még csak a sima jelszó
//		assertThat(loggedInUser.get()).isEqualTo(newUser);
//
//	}
//
//}