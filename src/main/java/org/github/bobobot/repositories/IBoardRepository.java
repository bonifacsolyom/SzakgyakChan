package org.github.bobobot.repositories;

import org.github.bobobot.entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBoardRepository extends JpaRepository<Board, Integer> {

	@Override
	Board save(Board board);

	@Override
	Optional<Board> findById(Integer integer);

	Optional<Board> findByShortName(String shortName);

	@Override
	List<Board> findAll();

	@Override
	void delete(Board board);
}
