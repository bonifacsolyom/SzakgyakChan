package org.github.bobobot.services;

import org.github.bobobot.entities.Board;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.User;

import java.util.List;

public interface IThreadService {

	/**
	 * Creates a thread.
	 *
	 * @param thread The thread to be created.
	 * @return The created thread.
	 */
	Thread create(Thread thread);

	/**
	 * Creates a thread. Consider using this method instead:
	 *
	 * @param title   The title of the thread.
	 * @param board   The board this thread was posted on.
	 * @param user    The user who posted the thread.
	 * @param replies A list of replies to the thread.
	 * @return The created thread.
	 * @see IThreadService#create(String, Board, User, Reply)
	 */
	Thread create(String title, Board board, User user, List<Reply> replies);

	/**
	 * Creates a thread.
	 *
	 * @param title     The title of the thread.
	 * @param board     The board this thread was posted on.
	 * @param user      The user who posted the thread.
	 * @param firstPost The first post to a thread.
	 * @return The created thread.
	 */
	Thread create(String title, Board board, User user, Reply firstPost);

	/**
	 * Creates a thread. Consider using this method instead:
	 *
	 * @param title The title of the thread.
	 * @param board The board this thread was posted on.
	 * @param user  The user who posted the thread.
	 * @return The created thread.
	 * @see IThreadService#create(String, Board, User, Reply)
	 */
	Thread create(String title, Board board, User user);

	/**
	 * Updates a thread.
	 *
	 * @param thread The thread to be updated.
	 * @return The updated thread.
	 */
	Thread update(Thread thread);


	/**
	 * Updates a thread.
	 *
	 * @param ID    The ID of the thread.
	 * @param title The title of the thread.
	 * @param board The board of the thread.
	 * @param user  The user that posted the thread.
	 * @return The updated thread.
	 */
	Thread update(Long ID, String title, Board board, User user);

	/**
	 * Updates a thread.
	 *
	 * @param ID      The ID of the thread.
	 * @param title   The title of the thread.
	 * @param board   The board of the thread.
	 * @param user    The user that posted the thread.
	 * @param replies The replies of the thread.
	 * @return The updated thread.
	 */
	Thread update(Long ID, String title, Board board, User user, List<Reply> replies);

	/**
	 * Lists all threads.
	 *
	 * @return A list of all threads.
	 */
	List<Thread> list();

	/**
	 * Finds a thread by its ID.
	 *
	 * @param ID The ID of the thread to be found
	 * @return The found thread, wrapped in an optional.
	 */
	Thread findById(Long ID);

	/**
	 * Deletes a thread.
	 *
	 * @param ID The ID of the thread to be deleted.
	 */
	void delete(Long ID);
}
