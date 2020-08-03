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
	 * Creates a thread. Consider using the alternative method with only the first reply as the parameter.
	 * @see    IThreadService#create(String, Board, User, Reply)
	 *
	 * @param title   The title of the thread.
	 * @param board   The board this thread was posted on.
	 * @param user    The user who posted the thread.
	 * @param replies A list of replies to the thread.
	 * @return The created thread.
	 */
	default Thread create(String title, Board board, User user, List<Reply> replies) {
		return create(new Thread(title, board, user, replies));
	}

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
	default Thread create(String title, Board board, User user) {
		return create(new Thread(title, board, user));
	}

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
	 * @param id    The id of the thread.
	 * @param title The title of the thread.
	 * @param board The board of the thread.
	 * @param user  The user that posted the thread.
	 * @return The updated thread.
	 */
	default Thread update(Long id, String title, Board board, User user) {
		return update(new Thread(id, title, board, user));
	}

	/**
	 * Updates a thread.
	 *
	 * @param id      The id of the thread.
	 * @param title   The title of the thread.
	 * @param board   The board of the thread.
	 * @param user    The user that posted the thread.
	 * @param replies The replies of the thread.
	 * @return The updated thread.
	 */
	default Thread update(Long id, String title, Board board, User user, List<Reply> replies) {
		return update(new Thread(id, title, board, user, replies));
	}

	/**
	 * Lists all threads.
	 *
	 * @return A list of all threads.
	 */
	List<Thread> list();

	/**
	 * Finds a thread by its ID.
	 *
	 * @param id The ID of the thread to be found
	 * @return The found thread, wrapped in an optional.
	 */
	Thread findById(Long id);

	/**
	 * Deletes a thread.
	 *
	 * @param id The ID of the thread to be deleted.
	 */
	void delete(Long id);
}
