package org.github.bobobot.dao;

import org.github.bobobot.entities.Board;

import java.util.ArrayList;
import java.util.Optional;

/**
 * This interface defines a DAO of a board.
 */
public interface BoardDAO {

	/**
	 * Creates a board. Shouldn't be accessed by the user or the admin.
	 *
	 * @param board The board to be created.
	 * @return The created board.
	 */
	Board create(Board board);

	/**
	 * Updates a board
	 *
	 * @param board The board to be updated
	 * @return The updated board.
	 */
	Optional<Board> update(Board board);

	/**
	 * Selects a board by its ID.
	 *
	 * @param ID The ID of the board to be selected.
	 * @return The selected board wrapped in an optional, in case the board wasn't found.
	 */
	Optional<Board> select(int ID);

	/**
	 * Lists all existing boards.
	 *
	 * @return list of all existing boards.
	 */
	ArrayList<Board> list();

	/**
	 * Deletes a board.
	 *
	 * @param ID The ID of the board to be deleted
	 * @return The deleted board, wrapped in an optional.
	 */
	Optional<Board> delete(int ID);
}
