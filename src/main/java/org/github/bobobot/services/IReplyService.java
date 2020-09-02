package org.github.bobobot.services;

import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.User;
import org.github.bobobot.entities.VoteNotification.VoteType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IReplyService {

	/**
	 * Creates a reply.
	 *
	 * @param reply The reply to be created.
	 * @return The created reply.
	 */
	Reply post(Reply reply);

	/**
	 * Creates a reply.
	 *
	 * @param content The content of the reply.
	 * @param thread  The thread the reply belongs to.
	 * @param user    The user the reply belongs to.
	 * @return The created reply.
	 */
	default Reply post(String content, Thread thread, User user) {
		return post(new Reply(content, LocalDateTime.now(), thread, user, null));
	}

	/**
	 * Creates a reply.
	 *
	 * @param content   The content of the reply.
	 * @param thread    The thread the reply belongs to.
	 * @param user      The user the reply belongs to.
	 * @param imagePath The image path of the post
	 * @return The created reply.
	 */
	default Reply post(String content, Thread thread, User user, String imagePath) {
		return post(new Reply(content, LocalDateTime.now(), thread, user, imagePath));
	}

	/**
	 * Creates a reply.
	 *
	 * @param content The content of the reply.
	 * @param thread  The thread the reply belongs to.
	 * @param user    The user the reply belongs to.
	 * @return The created reply.
	 */
	default Reply post(String content, int votes, Thread thread, User user) {
		return post(new Reply(content, LocalDateTime.now(), thread, user, null).setDebugVoteCount(votes));
	}

	/**
	 * Creates a reply.
	 *
	 * @param content The content of the reply.
	 * @param votes   The summarized score of the reply.
	 * @param thread  The thread the reply belongs to.
	 * @param user    The user the reply belongs to.
	 * @return The created reply.
	 */
	default Reply post(String content, int votes, String image, Thread thread, User user) {
		return post(new Reply(content, LocalDateTime.now(), thread, user, image));
	}

	/**
	 * Updates a reply.
	 *
	 * @param reply The reply to be updated
	 * @return The updated reply.
	 */
	Reply update(Reply reply);

	/**
	 * Updates a reply.
	 *
	 * @param id      The ID of the reply.
	 * @param content The content of the reply.
	 * @param thread  The thread the reply belongs to.
	 * @param user    The user the reply belongs to.
	 * @return The updated reply.
	 */
	default Reply update(Long id, String content, Set<Long> usersUpvoted, Set<Long> usersDownvoted, Thread thread, User user) {
		return update(new Reply(id, content, LocalDateTime.now(), thread, user, null, usersUpvoted, usersDownvoted));
	}

	/**
	 * Updates a reply.
	 *
	 * @param id      The ID of the reply.
	 * @param content The content of the reply.
	 * @param thread  The thread the reply belongs to.
	 * @param user    The user the reply belongs to.
	 * @return The updated reply.
	 */
	default Reply update(Long id, String content, Set<Long> usersUpvoted, Set<Long> usersDownvoted, String image, Thread thread, User user) {
		return update(new Reply(id, content, LocalDateTime.now(), thread, user, image, usersUpvoted, usersDownvoted));
	}

	/**
	 * Lists all replies.
	 *
	 * @return A list of all replies.
	 */
	List<Reply> list();

	/**
	 * Finds a reply by its ID.
	 *
	 * @param id The ID of the reply to be found
	 * @return The found reply, wrapped in an optional.
	 */
	Reply findById(Long id);

	/**
	 * Lists all replies that belong to the specified thread.
	 *
	 * @param thread The thread the replies belong to
	 * @return All replies that belong to the thread
	 */
	List<Reply> listByThread(Thread thread);

	/**
	 * Votes on a reply.
	 *
	 * @param userId
	 * @param id       The ID of the reply.
	 * @param voteType The type of the vote
	 * @return
	 */
	Reply vote(Long userId, Long id, VoteType voteType);

	Optional<VoteType> getUserVote(Long userId, Long replyId);

	/**
	 * Deletes a reply.
	 *
	 * @param id The ID of the reply to be deleted.
	 */
	void delete(Long id);
}
