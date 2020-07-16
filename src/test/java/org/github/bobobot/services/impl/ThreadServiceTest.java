package org.github.bobobot.services.impl;

import org.github.bobobot.dao.impl.InMemoryThreadDAO;
import org.github.bobobot.dao.impl.InMemoryUserDAO;
import org.github.bobobot.entities.Board;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.github.bobobot.services.impl.TestHelperUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class ThreadServiceTest {


	@Test
	void postThread() {
		ThreadService service = new ThreadService(new InMemoryThreadDAO());
		Thread originalThread = createDummyThread();
		service.create(originalThread);
		Thread thread = service.findById(0);

		assertEquals(originalThread, thread);
	}

	@Test
	void checkIfUserStoredTheThreadAfterPost() {
		ThreadService threadService = new ThreadService(new InMemoryThreadDAO());
		UserService userService = new UserService(new InMemoryUserDAO());

		User originalUser = createDummyUser();
		Thread originalThread = createDummyThread(originalUser);
		userService.create(originalUser);
		User user = userService.findByUsername("tesztNev");
		threadService.create(originalThread);

		assertEquals(1, user.getThreads().size());
	}

	@Test
	void checkIfThreadStoredTheUserAfterPost() {
		ThreadService threadService = new ThreadService(new InMemoryThreadDAO());
		UserService userService = new UserService(new InMemoryUserDAO());

		User originalUser = createDummyUser();
		Thread originalThread = createDummyThread(originalUser);
		userService.create(originalUser);
		threadService.create(originalThread);
		Thread thread = threadService.findById(0);

		assertEquals(originalUser, thread.getUser());
	}

	@Test
	void updateThread() {
		ThreadService service = new ThreadService(new InMemoryThreadDAO());
		Board board = createDummyBoard();
		User user = createDummyUser();
		Thread originalThread = createDummyThread(board, user);

		service.create(originalThread);
		service.update(0, "tesztTitle1", board, user);
		Thread thread = service.findById(0);

		assertEquals("tesztTitle1", thread.getTitle());
	}

	@Test
	void deleteThread() {
		ThreadService service = new ThreadService(new InMemoryThreadDAO());
		service.create(createDummyThread());
		assertEquals(1, service.list().size());
		service.delete(0);
		assertEquals(0, service.list().size());
	}

	@Test
	void deleteThreadButIsntPresent() {
		ThreadService service = new ThreadService(new InMemoryThreadDAO());
		assertThrows(IllegalArgumentException.class, () -> service.delete(0));
	}
}