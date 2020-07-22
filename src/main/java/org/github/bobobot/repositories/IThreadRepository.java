package org.github.bobobot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.github.bobobot.entities.Thread;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IThreadRepository extends JpaRepository<Thread, Integer> {

}
