package org.github.bobobot.services.impl;

import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.VoteNotification;
import org.github.bobobot.services.IReplyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.github.bobobot.services.impl.TestHelperUtils.createDummyReply;
import static org.github.bobobot.services.impl.TestHelperUtils.createDummyThread;

@ActiveProfiles("test")
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ComponentScan("org.github.bobobot")
class ReplyServiceTest {

	@Autowired
	private TestEntityManager em;

	@Autowired
	private IReplyService service;

	@Test
	void postReply() {
		Reply originalReply = createDummyReply();

		em.persist(originalReply);
		Reply reply = service.findById(1L);

		assertThat(originalReply.getContent()).isEqualTo(reply.getContent());
	}

	@Test
	void checkIfReplyStoredTheThreadAfterPost() {
		Thread thread = createDummyThread();
		Reply originalReply = createDummyReply(thread);

		em.persist(originalReply);
		service.post(originalReply);

		assertThat(thread.getReplies().get(0).getContent()).isEqualTo(originalReply.getContent());
	}

	@Test
	void checkIfThreadStoredTheReplyAfterPost() {
		Thread thread = createDummyThread();
		Reply reply = createDummyReply(thread);

		em.persist(reply);
		service.post(reply);

		assertThat(reply.getThread()).isEqualTo(thread);
	}

	@Test
	void checkIfOtherRepliesAreNotifiedOfComment() {
		Thread thread = createDummyThread();
		thread = em.persist(thread);
		Reply reply1 = createDummyReply(thread);
		Reply reply2 = createDummyReply(thread);

		em.persist(reply1);
		service.post(reply1);
		assertThat(reply1.getUser().getNotifications()).hasSize(0);

		em.persist(reply2);
		service.post(reply2);
		assertThat(reply1.getUser().getNotifications()).hasSize(1);
	}

	@Test
	void voteOnReply() {
		Reply reply = createDummyReply();

		em.persist(reply);
		reply = service.post(reply);
		service.vote(1L, reply.getId(), VoteNotification.VoteType.UPVOTE);
		reply = service.findById(reply.getId());

		assertThat(reply.getVoteCount()).isEqualTo(1);
	}

	@Test
	void deleteReply() {
		Reply reply = new Reply();

		em.persist(reply);
		assertThat(service.list().size()).isEqualTo(1);
		service.delete(1L);
		assertThat(service.list().size()).isEqualTo(0);
	}

	@Test
	void deleteReplyButDoesntExist() {
		assertThatIllegalArgumentException().isThrownBy(() -> service.delete(1L));
	}

}