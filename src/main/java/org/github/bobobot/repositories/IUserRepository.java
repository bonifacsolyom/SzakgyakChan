package org.github.bobobot.repositories;

import org.github.bobobot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByName(String name);

	Optional<User> findByEmail(String email);

}
