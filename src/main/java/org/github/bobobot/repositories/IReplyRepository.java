package org.github.bobobot.repositories;

import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReplyRepository extends JpaRepository<Reply, Long> {

	List<Reply> findAllByThread(Thread thread);

}
