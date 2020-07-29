package org.github.bobobot.repositories;

import org.github.bobobot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

	/**
	 * Finds a user by their name.
	 * @param name The name of the user.
	 * @return The found user, wrapped in an optional.
	 */
	Optional<User> findByName(String name);

	/**
	 * Finds a user by their email.
	 * @param email The email of the user.
	 * @return The found user, wrapped in an optional.
	 */
	Optional<User> findByEmail(String email);

}
