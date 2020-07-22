package org.github.bobobot.repositories;

import org.github.bobobot.entities.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IReplyRepository extends JpaRepository<Reply, Integer> {

	@Override
	Reply save(Reply reply);

	@Override
	Optional<Reply> findById(Integer integer);

	Optional<Reply> findByThread(Thread thread);

	@Override
	List<Reply> findAll();

	@Override
	void delete(Reply reply);
}
