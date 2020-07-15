package org.github.bobobot.dao;

import org.github.bobobot.entities.User;

import java.util.Optional;

public interface UserDAO {
	/**
	 * Creates a new user.
	 *
	 * @param user The user to be created.
	 * @return The created user.
	 */
	User create(User user);

	/**
	 * Updates a user.
	 *
	 * @param user The user to be updated.
	 * @return The updated user.
	 */
	Optional<User> update(User user);

	/**
	 * Selects a user by their ID.
	 *
	 * @param ID The ID of the user to be selected.
	 * @return The selected user, wrapped in an optional.
	 */
	Optional<User> select(int ID);

	/**
	 * Deletes a user.
	 *
	 * @param ID The ID of the user to be deleted.
	 * @return The deleted user, wrapped in an optional.
	 */
	Optional<User> delete(int ID);
}
