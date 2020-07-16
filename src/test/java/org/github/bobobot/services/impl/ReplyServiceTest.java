package org.github.bobobot.services.impl;

import org.github.bobobot.dao.impl.InMemoryReplyDAO;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.services.IReplyService;
import org.junit.jupiter.api.Test;

import static org.github.bobobot.services.impl.TestHelperUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

class ReplyServiceTest {

	@Test
	void postReply() {
		IReplyService service = new ReplyService(new InMemoryReplyDAO());
		Reply originalReply = createDummyReply();

		service.post(originalReply);
		Reply reply = service.findById(0);

		assertThat(originalReply).isEqualTo(reply);
	}

	@Test
	void checkIfReplyStoredTheThreadAfterPost() {
		IReplyService service = new ReplyService(new InMemoryReplyDAO());
		Thread thread = createDummyThread();
		Reply originalReply = createDummyReply(thread);

		service.post(originalReply);

		assertThat(thread.getReplies().get(0)).isEqualTo(originalReply);
	}

	@Test
	void checkIfThreadStoredTheReplyAfterPost() {
		IReplyService service = new ReplyService(new InMemoryReplyDAO());
		Thread thread = createDummyThread();
		Reply reply = createDummyReply(thread);

		service.post(reply);

		assertThat(reply.getThread()).isEqualTo(thread);
	}

	@Test
	void checkIfOtherRepliesAreNotifiedOfComment() {
		IReplyService service = new ReplyService(new InMemoryReplyDAO());

		Thread thread = createDummyThread();
		Reply reply1 = createDummyReply(thread);
		//Az ID magától számlálódik, tehát nem baj hogy mindkettő -1
		Reply reply2 = createDummyReply(thread);

		service.post(reply1);
		service.post(reply2);

		assertThat(reply1.getUser().getNotifications()).hasSize(1);
	}

	@Test
	void voteOnReply() {

	}

	void deleteReply() {

	}

}