package org.github.bobobot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IThreadRepository extends JpaRepository<Thread, Integer> {

	@Override
	Thread save(Thread thread);

	@Override
	Optional<Thread> findById(Integer integer);

	@Override
	List<Thread> findAll();

	@Override
	void delete(Thread thread);
}
