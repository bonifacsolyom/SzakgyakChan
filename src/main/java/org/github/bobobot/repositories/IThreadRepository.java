package org.github.bobobot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.github.bobobot.entities.Thread;

import java.util.List;
import java.util.Optional;

public interface IThreadRepository extends JpaRepository<Thread, Integer> {

}
