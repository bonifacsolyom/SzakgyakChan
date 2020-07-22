package org.github.bobobot.repositories;

import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IReplyRepository extends JpaRepository<Reply, Integer> {

	List<Reply> findAllByThread(Thread thread);

}
