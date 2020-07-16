package org.github.bobobot.services.impl;

import org.github.bobobot.entities.Board;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ThreadServiceTest {


	@Test
	void postThread() {
		ThreadService service = new ThreadService();
		Board board = new Board(0, "t", "teszt board");

		User user = new User(0, true, "tesztNev", "tesztEmail", "tesztJelszo");
		Thread originalThread = new Thread(0, "tesztTitle", board, user);
		service.create("tesztTitle", board, user);
		Thread thread = service.findById(0);

		assertEquals(originalThread, thread);
	}

	@Test
	void checkIfUserStoredTheThreadAfterPost() {
		ThreadService threadService = new ThreadService();
		UserService userService = new UserService();
		Board board = new Board(0, "t", "teszt board");

		User originalUser = new User(0, true, "tesztNev", "tesztEmail", "tesztJelszo");
		Thread originalThread = new Thread(0, "tesztTitle", board, originalUser);
		userService.create(true, "tesztNev", "tesztEmail", "tesztJelszo");
		User user = userService.findByUsername("tesztNev");
		threadService.create("tesztTitle", board, user);

		assertEquals(1, user.getThreads().size());
	}

	@Test
	void checkIfThreadStoredTheUserAfterPost() {
		ThreadService threadService = new ThreadService();
		UserService userService = new UserService();
		Board board = new Board(0, "t", "teszt board");

		User originalUser = new User(0, true, "tesztNev", "tesztEmail", "tesztJelszo");
		Thread originalThread = new Thread(0, "tesztTitle", board, originalUser);
		userService.create(true, "tesztNev", "tesztEmail", "tesztJelszo");
		threadService.create("tesztTitle", board, originalUser);
		Thread thread = threadService.findById(0);

		assertEquals(originalUser, thread.getUser());
	}

	@Test
	void updateThread() {
		ThreadService service = new ThreadService();
		Board board = new Board(0, "t", "teszt board");

		User user = new User(0, true, "tesztNev", "tesztEmail", "tesztJelszo");
		service.create("tesztTitle", board, user);
		service.update(0, "tesztTitle1", board, user);
		Thread thread = service.findById(0);

		assertEquals("tesztTitle1", thread.getTitle());
	}

	@Test
	void deleteThread() {
		ThreadService service = new ThreadService();
		Board board = new Board(0, "t", "teszt board");

		User user = new User(0, true, "tesztNev", "tesztEmail", "tesztJelszo");
		service.create("tesztTitle", board, user);
		assertEquals(1, service.list().size());
		service.delete(0);
		assertEquals(0, service.list().size());
	}

	@Test
	void deleteThreadButIsntPresent() {
		ThreadService service = new ThreadService();
		assertThrows(IllegalArgumentException.class,  () -> service.delete(0));
	}
}