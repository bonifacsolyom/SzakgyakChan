package org.github.bobobot.services.impl;

import org.github.bobobot.entities.Board;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.User;

public class TestHelperUtils {

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
}
