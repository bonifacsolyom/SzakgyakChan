package org.github.bobobot.services;

import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.User;
import org.github.bobobot.entities.VoteNotification.VoteType;

import java.util.List;

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
	 * @param votes   The summarized score of the reply.
	 * @param thread  The thread the reply belongs to.
	 * @param user	  The user the reply belongs to.
	 * @return The created reply.
	 */
	Reply post(String content, int votes, Thread thread, User user);

	/**
	 * Creates a reply.
	 *
	 * @param content The content of the reply.
	 * @param votes   The summarized score of the reply.
	 * @param thread  The thread the reply belongs to.
	 * @param user	  The user the reply belongs to.
	 * @return The created reply.
	 */
	Reply post(String content, int votes, String image, Thread thread, User user);

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
	 * @param ID      The ID of the reply.
	 * @param content The content of the reply.
	 * @param votes   The summarized score of the reply.
	 * @param thread  The thread the reply belongs to.
	 * @param user    The user the reply belongs to.
	 * @return The updated reply.
	 */
	Reply update(Long ID, String content, int votes, Thread thread, User user);

	/**
	 * Updates a reply.
	 *
	 * @param ID      The ID of the reply.
	 * @param content The content of the reply.
	 * @param votes   The summarized score of the reply.
	 * @param thread  The thread the reply belongs to.
	 * @param user    The user the reply belongs to.
	 * @return The updated reply.
	 */
	Reply update(Long ID, String content, int votes, String image, Thread thread, User user);

	/**
	 * Lists all replies.
	 *
	 * @return A list of all replies.
	 */
	List<Reply> list();

	/**
	 * Finds a reply by its ID.
	 *
	 * @param ID The ID of the reply to be found
	 * @return The found reply, wrapped in an optional.
	 */
	Reply findById(Long ID);

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
	 * @param ID       The ID of the reply.
	 * @param voteType The type of the vote
	 * @return
	 */
	Reply vote(Long ID, VoteType voteType);

	/**
	 * Deletes a reply.
	 *
	 * @param ID The ID of the reply to be deleted.
	 */
	void delete(Long ID);
}
