package org.github.bobobot.services;

import org.github.bobobot.entities.Reply;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface ReplyService {

	/**
	 * Creates a reply.
	 *
	 * @param content The content of the reply.
	 * @param date    The date of the reply.
	 * @param votes   The summarized score of the reply.
	 * @param thread  The thread the reply belongs to.
	 * @return The created reply.
	 */
	Reply create(String content, LocalDateTime date, int votes, Thread thread);

	/**
	 * Updates a reply.
	 *
	 * @param ID      The ID of the reply.
	 * @param content The content of the reply.
	 * @param date    The date of the reply.
	 * @param votes   The summarized score of the reply.
	 * @param thread  The thread the reply belongs to.
	 * @return The updated reply.
	 */
	Reply update(int ID, String content, LocalDateTime date, int votes, Thread thread);

	/**
	 * Lists all replies.
	 *
	 * @return A list of all replies.
	 */
	ArrayList<Reply> list();

	/**
	 * Finds a reply by its ID.
	 *
	 * @param ID The ID of the reply to be found
	 * @return The found reply, wrapped in an optional.
	 */
	Reply findById(int ID);

	/**
	 * Lists all replies that belong to the specified thread.
	 *
	 * @param thread The thread the replies belong to
	 * @return All replies that belong to the thread
	 */
	ArrayList<Reply> listByThread(Thread thread);

	/**
	 * Deletes a reply.
	 *
	 * @param ID The ID of the reply to be deleted.
	 */
	void delete(int ID);
}
