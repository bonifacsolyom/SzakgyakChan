package org.github.bobobot.services.impl;

import org.github.bobobot.dao.impl.InMemoryReplyDAO;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.VoteNotification;
import org.github.bobobot.services.IReplyService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.github.bobobot.services.impl.TestHelperUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

class ReplyServiceTest {

	@Test
	void postReply() {
		IReplyService service = createReplyService();
		Reply originalReply = createDummyReply();

		service.post(originalReply);
		Reply reply = service.findById(0);

		assertThat(originalReply).isEqualTo(reply);
	}

	@Test
	void checkIfReplyStoredTheThreadAfterPost() {
		IReplyService service = createReplyService();
		Thread thread = createDummyThread();
		Reply originalReply = createDummyReply(thread);

		service.post(originalReply);

		assertThat(thread.getReplies().get(0)).isEqualTo(originalReply);
	}

	@Test
	void checkIfThreadStoredTheReplyAfterPost() {
		IReplyService service = createReplyService();
		Thread thread = createDummyThread();
		Reply reply = createDummyReply(thread);

		service.post(reply);

		assertThat(reply.getThread()).isEqualTo(thread);
	}

	@Test
	void checkIfOtherRepliesAreNotifiedOfComment() {
		IReplyService service = createReplyService();

		Thread thread = createDummyThread();
		Reply reply1 = createDummyReply(thread);
		//Az ID magától számlálódik, tehát nem baj hogy mindkettő -1
		Reply reply2 = createDummyReply(thread);

		service.post(reply1);
		assertThat(reply1.getUser().getNotifications()).hasSize(0);

		service.post(reply2);
		assertThat(reply1.getUser().getNotifications()).hasSize(1);
	}

	@Test
	void voteOnReply() {
		IReplyService service = createReplyService();
		Reply reply = createDummyReply();

		reply = service.post(reply);
		service.vote(reply.getID(), VoteNotification.VoteType.UPVOTE);
		reply = service.findById(reply.getID());

		assertThat(reply.getVotes()).isEqualTo(1);
	}

	@Test
	void deleteReply() {
		IReplyService service = createReplyService();
		Reply reply = createDummyReply();

		service.post(reply);
		assertThat(service.list().size()).isEqualTo(1);
		service.delete(0);
		assertThat(service.list().size()).isEqualTo(0);
	}

	@Test
	void deleteReplyButDoesntExist() {
		IReplyService service = createReplyService();

		assertThatIllegalArgumentException().isThrownBy(() -> service.delete(0));
	}

}