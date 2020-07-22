package org.github.bobobot.services.impl;

import org.github.bobobot.dao.IUserDAO;
import org.github.bobobot.dao.impl.*;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.*;

import java.time.LocalDateTime;

public class TestHelperUtils {

/*	static ReplyService createReplyService() {
		return new ReplyService(new InMemoryReplyDAO(), new InMemoryCommentNotificationDAO(), new InMemoryVoteNotificationDAO());
	}

	static NotificationService createNotificationService() {
		return new NotificationService(new InMemoryCommentNotificationDAO(), new InMemoryVoteNotificationDAO(), new InMemoryUserDAO());
	}

	static NotificationService createNotificationService(IUserDAO userDAO) {
		return new NotificationService(new InMemoryCommentNotificationDAO(), new InMemoryVoteNotificationDAO(), userDAO);
	}

	static UserService createUserService() {
		return new UserService(new InMemoryUserDAO());
	}

	static UserService createUserService(IUserDAO userDAO) {
		return new UserService(userDAO);
	}

	static BoardService createBoardService() {
		return new BoardService(new InMemoryBoardDAO());
	}

	static ThreadService createThreadService() {
		return new ThreadService(new InMemoryThreadDAO());
	}
*/

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
	 *
	 * @return The newly created board
	 */
	static Board createDummyBoard() {
		return new Board(-1, "t", "teszt board");
	}

	/**
	 * Creates a dummy thread for testing with the following details:<p>
	 * ID: -1<p>
	 * title: tesztTitle<p>
	 *
	 * @param board The thread's board
	 * @param user  The thread's user
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

	/**
	 * Creates a dummy reply with the following details:<p>
	 * ID: -1<p>
	 * content: tesztContent<p>
	 * date: 2020.04.20 8:00<p>
	 * votes: 0<p>
	 * image: Dummy image<p>
	 *
	 * @param thread The thread of the reply
	 * @param user   The user of the reply
	 * @return The newly created reply.
	 */
	static Reply createDummyReply(Thread thread, User user) {
		return new Reply(-1,
				"tesztContent",
				LocalDateTime.of(2020, 4, 20, 8, 0),
				0,
				createDummyImage(), thread, user);
	}

	/**
	 * @see TestHelperUtils#createDummyReply(Thread, User)
	 */
	static Reply createDummyReply(User user) {
		return createDummyReply(createDummyThread(), user);
	}


	/**
	 * @see TestHelperUtils#createDummyReply(Thread, User)
	 */
	static Reply createDummyReply(Thread thread) {
		return createDummyReply(thread, createDummyUser());
	}


	/**
	 * @see TestHelperUtils#createDummyReply(Thread, User)
	 */
	static Reply createDummyReply() {
		return createDummyReply(createDummyThread(), createDummyUser());
	}

	/**
	 * Creates a dummy image with the following details:<p>
	 * ID: -1<p>
	 * exists: false<p>
	 * path: ""<p>
	 *
	 * @return The newly created image.
	 */
	static Image createDummyImage() {
		return new Image(-1, false, "");
	}

	/**
	 * Creates a dummy comment notification with the following details:<p>
	 * ID: -1<p>
	 * read: false<p>
	 * reply content: tesztContent<p>
	 *
	 * @param user The user that the notification belongs to
	 * @return The newly created dummy comment notification.
	 */
	static CommentNotification createDummyCommentNotification(User user) {
		return new CommentNotification(-1, false, user, "tesztContent");
	}

	/**
	 * @see TestHelperUtils#createDummyCommentNotification(User)
	 */
	static CommentNotification createDummyCommentNotification() {
		return createDummyCommentNotification(createDummyUser());
	}

	/**
	 * Creates a dummy vote notification with the following details:<p>
	 * ID: -1<p>
	 * read: false<p>
	 * vote type: UPVOTE<p>
	 *
	 * @param user The user that the notification belongs to
	 * @return The newly created dummy vote notification
	 */
	static VoteNotification createDummyVoteNotification(User user) {
		return new VoteNotification(-1, false, user, VoteNotification.VoteType.UPVOTE);
	}

	/**
	 * @see TestHelperUtils#createDummyVoteNotification(User)
	 */
	static VoteNotification createDummyVoteNotification() {
		return createDummyVoteNotification(createDummyUser());
	}
}
