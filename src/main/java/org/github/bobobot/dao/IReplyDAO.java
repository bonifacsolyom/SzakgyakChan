package org.github.bobobot.dao;

import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;

import java.util.List;
import java.util.Optional;

/**
 * This interface defines a DAO of a reply.
 */
public interface IReplyDAO {

	/**
	 * Creates a reply.
	 *
	 * @param reply The reply to be created.
	 * @return The created reply.
	 */
	Reply create(Reply reply);

	/**
	 * Updates a reply.
	 *
	 * @param reply The reply to be updated.
	 * @return
	 */
	Optional<Reply> update(Reply reply);

	/**
	 * Selects a reply by its ID.
	 *
	 * @param ID The ID of the reply to be selected
	 * @return The selected reply, wrapped in an optional.
	 */
	Optional<Reply> select(int ID);

	/**
	 * Selects a reply by the thread it belongs to.
	 *
	 * @param thread The thread of the reply to be selected
	 * @return The selected reply, wrapped in an optional.
	 */
	List<Reply> selectByThread(Thread thread);

	/**
	 * Lists all replies.
	 *
	 * @return A list of all replies.
	 */
	List<Reply> list();

	/**
	 * Deletes a reply.
	 *
	 * @param ID The ID of the reply to be deleted.
	 * @return The deleted reply.
	 */
	Optional<Reply> delete(int ID);
}
