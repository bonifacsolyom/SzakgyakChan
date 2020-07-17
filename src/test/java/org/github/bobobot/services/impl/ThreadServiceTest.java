package org.github.bobobot.services.impl;

import org.github.bobobot.dao.impl.InMemoryThreadDAO;
import org.github.bobobot.dao.impl.InMemoryUserDAO;
import org.github.bobobot.entities.Board;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.github.bobobot.services.impl.TestHelperUtils.*;

class ThreadServiceTest {


	@Test
	void postThread() {
		ThreadService service = createThreadService();
		Thread originalThread = createDummyThread();
		service.create(originalThread);
		Thread thread = service.findById(0);

		assertThat(originalThread).isEqualTo(thread);
	}

	@Test
	void checkIfUserStoredTheThreadAfterPost() {
		ThreadService threadService = createThreadService();
		UserService userService = new UserService(new InMemoryUserDAO());

		User originalUser = createDummyUser();
		Thread originalThread = createDummyThread(originalUser);
		userService.register(originalUser);
		User user = userService.findByUsername("tesztNev");
		threadService.create(originalThread);

		assertThat(user.getThreads().size()).isEqualTo(1);
	}

	@Test
	void checkIfThreadStoredTheUserAfterPost() {
		ThreadService threadService = createThreadService();
		UserService userService = new UserService(new InMemoryUserDAO());

		User originalUser = createDummyUser();
		Thread originalThread = createDummyThread(originalUser);
		userService.register(originalUser);
		threadService.create(originalThread);
		Thread thread = threadService.findById(0);

		assertThat(originalUser).isEqualTo(thread.getUser());
	}

	@Test
	void updateThread() {
		ThreadService service = createThreadService();
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
		ThreadService service = createThreadService();
		service.create(createDummyThread());
		assertThat(service.list().size()).isEqualTo(1);
		service.delete(0);
		assertThat(service.list().size()).isEqualTo(0);
	}

	@Test
	void deleteThreadButIsntPresent() {
		ThreadService service = createThreadService();
		assertThatIllegalArgumentException().isThrownBy(() -> service.delete(0));
	}


}