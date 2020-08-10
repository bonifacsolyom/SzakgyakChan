package org.github.bobobot.repositories;

import org.github.bobobot.entities.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IThreadRepository extends JpaRepository<Thread, Long> {

}
