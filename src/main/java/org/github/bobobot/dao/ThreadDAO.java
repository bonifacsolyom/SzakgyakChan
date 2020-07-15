package org.github.bobobot.dao;

import org.github.bobobot.entities.Thread;

import java.util.ArrayList;
import java.util.Optional;

/**
 * This interface defines a DAO of a thread.
 */
public interface ThreadDAO {
	/**
	 * Creates a thread.
	 *
	 * @param thread The thread to be created.
	 * @return The created thread.
	 */
	Thread create(Thread thread);

	/**
	 * Updates a thread.
	 *
	 * @param thread The thread to be updated.
	 * @return The updated thread, wrapped in an optional.
	 */
	Optional<Thread> update(Thread thread);

	/**
	 * Selects a thread by its ID.
	 *
	 * @param ID The ID of the thread to be selected.
	 * @return The selected thread wrapped in an optional.
	 */
	Optional<Thread> select(int ID);

	/**
	 * Lists all threads.
	 *
	 * @return A list of all existing threads.
	 */
	ArrayList<Thread> list();

	/**
	 * Deletes a thread.
	 *
	 * @param ID The ID of the thread to be deleted.
	 * @return The deleted thread, wrapped in an optional.
	 */
	Optional<Thread> delete(int ID);
}
