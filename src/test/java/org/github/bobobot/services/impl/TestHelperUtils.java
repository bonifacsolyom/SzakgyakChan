package org.github.bobobot.services.impl;

import org.github.bobobot.dao.impl.InMemoryCommentNotification;
import org.github.bobobot.dao.impl.InMemoryReplyDAO;
import org.github.bobobot.dao.impl.InMemoryVoteNotification;
import org.github.bobobot.entities.*;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.services.IReplyService;

import java.time.LocalDateTime;

public class TestHelperUtils {

	static ReplyService createReplyService() {
		return new ReplyService(new InMemoryReplyDAO(), new InMemoryCommentNotification(), new InMemoryVoteNotification());
	}

	/**
	 * Creates a dummy user for testing with the following details:<p>
	 * ID: -1<p>
	 * is admin: true<p>
	 * username: tesztNev<p>
	 * email: tesztEmail@teszt.com<p>
	 * password hash: tesztJelszo<p>
	 *
	 * @return The newly created user
	 */
	static User createDummyUser() {
		return new User(-1, true, "tesztNev", "tesztEmail@teszt.com", "tesztJelszo");
	}

	/**
	 * Creates a dummy board for testing with the following details:<p>
	 * ID: -1<p>
	 * short name: t<p>
	 * long name: teszt board<p>
	 * @return The newly created board
	 */
	static Board createDummyBoard() {
		return new Board(-1, "t", "teszt board");
	}

	/**
	 * Creates a dummy thread for testing with the following details:<p>
	 * ID: -1<p>
	 * title: tesztTitle<p>
	 * @param board The thread's board
	 * @param user The thread's user
	 * @return The newly created thread
	 */
	static Thread createDummyThread(Board board, User user) {
		return new Thread(-1, "tesztTitle", board, user);
	}

	/**
	 * @see TestHelperUtils#createDummyThread(Board, User)
	 */
	static Thread createDummyThread(Board board) {
		return createDummyThread(board, createDummyUser());
	}

	/**
	 * @see TestHelperUtils#createDummyThread(Board, User)
	 */
	static Thread createDummyThread(User user) {
		return createDummyThread(createDummyBoard(), user);
	}

	/**
	 * @see TestHelperUtils#createDummyThread(Board, User)
	 */
	static Thread createDummyThread() {
		return createDummyThread(createDummyBoard(), createDummyUser());
	}

	static Reply createDummyReply(Thread thread, User user) {
		return new Reply(-1,
				"tesztContent",
				LocalDateTime.of(2020, 4, 20, 8, 0),
				0,
				createDummyImage(), thread, user);
	}

	static Reply createDummyReply(User user) {
		return createDummyReply(createDummyThread(), user);
	}


	static Reply createDummyReply(Thread thread) {
		return createDummyReply(thread, createDummyUser());
	}


	static Reply createDummyReply() {
		return createDummyReply(createDummyThread(), createDummyUser());
	}

	static Image createDummyImage() {
		return new Image(-1, false, "");
	}
}
