package org.github.bobobot.repositories;

import org.github.bobobot.entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBoardRepository extends JpaRepository<Board, Long> {

	Optional<Board> findByShortName(String shortName);

}
