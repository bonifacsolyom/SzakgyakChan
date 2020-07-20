package org.github.bobobot.dao;

import org.github.bobobot.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserDAO {
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
	Optional<User> selectById(int ID);

	/**
	 * Selects a user by their ID.
	 *
	 * @param name The Username of the user to be selected.
	 * @return The selected user, wrapped in an optional.
	 */
	Optional<User> selectByUsername(String name);

	/**
	 * Selects a user by their ID.
	 *
	 * @param email The email of the user to be selected.
	 * @return The selected user, wrapped in an optional.
	 */
	Optional<User> selectByEmail(String email);

	/**
	 * Returns a list of all users
	 *
	 * @return A list of all users
	 */
	List<User> list();

	/**
	 * Deletes a user.
	 *
	 * @param ID The ID of the user to be deleted.
	 * @return The deleted user, wrapped in an optional.
	 */
	Optional<User> delete(int ID);
}
