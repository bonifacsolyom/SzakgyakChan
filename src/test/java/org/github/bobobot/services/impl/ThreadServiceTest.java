package org.github.bobobot.services.impl;

import org.github.bobobot.config.ApplicationConfig;
import org.github.bobobot.entities.Board;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.User;
import org.github.bobobot.services.IThreadService;
import org.github.bobobot.services.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.github.bobobot.services.impl.TestHelperUtils.*;

@ActiveProfiles("test")
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ContextConfiguration(classes = ApplicationConfig.class)
class ThreadServiceTest {

	@Autowired
	private TestEntityManager em;

	@Autowired
	private IThreadService threadService;

	@Autowired
	private IUserService userService;

	@Test
	void postThread() {
		Thread originalThread = createDummyThread();
		em.persist(originalThread);
		threadService.create(originalThread);
		Thread thread = threadService.findById(1L);

		assertThat(originalThread.getTitle()).isEqualTo(thread.getTitle());
	}

	@Test
	void checkIfUserStoredTheThreadAfterPost() {
		User originalUser = createDummyUser();
		Thread originalThread = new Thread();
		originalThread.setUser(originalUser);
		em.persist(originalUser);
		em.persist(originalThread);
		userService.register(originalUser);
		threadService.create(originalThread);
		User user = userService.findByUsername("tesztNev");

		assertThat(user.getThreads().size()).isEqualTo(1);
	}

	@Test
	void checkIfThreadStoredTheUserAfterPost() {
		User originalUser = createDummyUser();
		Thread originalThread = createDummyThread(originalUser);
		em.persist(originalUser);
		em.persist(originalThread);
		userService.register(originalUser);
		threadService.create(originalThread);
		Thread thread = threadService.findById(1L);

		assertThat(originalUser).isEqualTo(thread.getUser());
	}

	@Test
	void updateThread() {
		Board board = createDummyBoard();
		User user = createDummyUser();
		Thread originalThread = createDummyThread(board, user);

		threadService.create(originalThread);
		threadService.update(1L, "tesztTitle1", board, user);
		Thread thread = threadService.findById(1L);

		assertThat("tesztTitle1").isEqualTo(thread.getTitle());
	}

	@Test
	void deleteThread() {
		Thread thread = new Thread();

		em.persist(thread);

		assertThat(threadService.list().size()).isEqualTo(1);
		threadService.delete(thread.getID());
		assertThat(threadService.list().size()).isEqualTo(0);
	}

	@Test
	void deleteThreadButIsntPresent() {
		assertThatIllegalArgumentException().isThrownBy(() -> threadService.delete(1L));
	}


}