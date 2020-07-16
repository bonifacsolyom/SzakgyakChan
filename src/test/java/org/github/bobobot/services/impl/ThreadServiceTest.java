package org.github.bobobot.services.impl;

import org.github.bobobot.dao.impl.InMemoryThreadDAO;
import org.github.bobobot.dao.impl.InMemoryUserDAO;
import org.github.bobobot.entities.Board;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.github.bobobot.services.impl.TestHelperUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class ThreadServiceTest {


	@Test
	void postThread() {
		ThreadService service = new ThreadService(new InMemoryThreadDAO());
		Thread originalThread = createDummyThread();
		service.create(originalThread);
		Thread thread = service.findById(0);

		assertThat(originalThread).isEqualTo(thread);
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

		assertThat(user.getThreads().size()).isEqualTo(1);
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

		assertThat(originalUser).isEqualTo(thread.getUser());
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

		assertThat("tesztTitle1").isEqualTo(thread.getTitle());
	}

	@Test
	void deleteThread() {
		ThreadService service = new ThreadService(new InMemoryThreadDAO());
		service.create(createDummyThread());
		assertThat(service.list().size()).isEqualTo(1);
		service.delete(0);
		assertThat(service.list().size()).isEqualTo(0);
	}

	@Test
	void deleteThreadButIsntPresent() {
		ThreadService service = new ThreadService(new InMemoryThreadDAO());
		assertThatIllegalArgumentException().isThrownBy(() -> service.delete(0));
	}


}