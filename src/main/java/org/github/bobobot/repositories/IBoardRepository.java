package org.github.bobobot.repositories;

import org.github.bobobot.entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBoardRepository extends JpaRepository<Board, Long> {

	/**
	 * Finds a board by its short name.
	 *
	 * @param shortName The short name of the board.
	 * @return The found board wrapped in an optional.
	 */
	Optional<Board> findByShortName(String shortName);
}
