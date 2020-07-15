package org.github.bobobot.services;

import org.github.bobobot.entities.Board;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.User;

import java.util.ArrayList;

public interface IThreadService {

	/**
	 * Creates or updates a thread.
	 *
	 * @param title The title of the thread.
	 * @param board The board this thread was posted on.
	 * @param user The user who posted the thread.
	 * @param replies A list of replies to the thread.
	 * @return The created/updated thread.
	 */
	Thread create(String title, Board board, User user, ArrayList<Reply> replies);

	/**
	 * Creates or updates a thread.
	 *
	 * @param title The title of the thread.
	 * @param board The board this thread was posted on.
	 * @param user The user who posted the thread.
	 * @return The created/updated thread.
	 */
	Thread create(String title, Board board, User user);


	/**
	 * Creates or updates a thread.
	 *
	 * @param title The title of the thread.
	 * @return The created/updated thread.
	 */
	Thread update(int ID, String title, Board board, ArrayList<Reply> replies, User user);

	/**
	 * Lists all threads.
	 *
	 * @return A list of all threads.
	 */
	ArrayList<Thread> list();

	/**
	 * Finds a thread by its ID.
	 *
	 * @param ID The ID of the thread to be found
	 * @return The found thread, wrapped in an optional.
	 */
	Thread findById(int ID);

	/**
	 * Deletes a thread.
	 *
	 * @param ID The ID of the thread to be deleted.
	 */
	void delete(int ID);
}
