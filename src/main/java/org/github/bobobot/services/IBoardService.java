package org.github.bobobot.services;

import org.github.bobobot.dao.IBoardDAO;
import org.github.bobobot.entities.Board;

import java.util.ArrayList;
import java.util.Optional;

public interface IBoardService {

	/**
	 * Creates a board
	 * @param shortName The short name of the board.
	 * @param longName  The longer description of the board - should only be a couple words long.
	 * @return The created board.
	 */
	Board create(String shortName, String longName);


	/**
	 * Updates a board
	 * @param ID        the ID of the board to be updated
	 * @param shortName The short name of the board.
	 * @param longName  The longer description of the board - should only be a couple words long.
	 * @return The updated board.
	 */
	Board update(int ID, String shortName, String longName);

	/**
	 * Lists all existing boards.
	 *
	 * @return list of all existing boards.
	 */
	ArrayList<Board> list();

	/**
	 * Finds a board by its ID.
	 *
	 * @param ID The ID of the board to be found.
	 * @return The found board wrapped in an optional.
	 */
	Board findById(int ID);

	/**
	 * Finds a board by its short name.
	 *
	 * @param shortName The short name of the board to be found.
	 * @return The found board wrapped in an optional.
	 */
	Board findByShortName(String shortName);

	/**
	 * Deletes a board.
	 *
	 * @param ID The ID of the board to be deleted.
	 */
	void delete(int ID);
}
