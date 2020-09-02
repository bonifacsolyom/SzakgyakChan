package org.github.bobobot.repositories;

import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReplyRepository extends JpaRepository<Reply, Long> {

	/**
	 * Finds all replies that belong to a specific thread.
	 *
	 * @param thread The thread that the replies should belong to.
	 * @return A list of all replies that belong to a specific thread.
	 */
	List<Reply> findAllByThread(Thread thread);

}
