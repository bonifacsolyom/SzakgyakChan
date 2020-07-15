package org.github.bobobot.dao;

import org.github.bobobot.entities.Reply;

import java.util.ArrayList;
import java.util.Optional;

/**
 * This interface defines a DAO of a reply.
 */
public interface ReplyDAO {

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
	 * Lists all replies.
	 *
	 * @return A list of all replies.
	 */
	ArrayList<Reply> list();

	/**
	 * Deletes a reply.
	 *
	 * @param ID The ID of the reply to be deleted.
	 * @return The deleted reply.
	 */
	Optional<Reply> delete(int ID);
}
