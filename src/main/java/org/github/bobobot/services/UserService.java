package org.github.bobobot.services;

import org.github.bobobot.entities.Notification;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.User;

import java.util.ArrayList;
import java.util.Optional;

public interface UserService {
	//TODO: Jelszóhash helyett ez még csak jelszót kapjon

	/**
	 * Creates or updates a user
	 *
	 * @param ID            The ID of the user to be updated.
	 * @param isAdmin       Specifies whether a user is an administrator or not.
	 * @param name          The name of the user.
	 * @param email         The email of the user.
	 * @param passwordHash  The password hash of the user.
	 * @param threads       The threads this user has posted.
	 * @param replies       The replies this user has posted.
	 * @param notifications The notifications this user has.
	 * @return The created/updated user.
	 */
	User createOrUpdate(int ID, boolean isAdmin, String name, String email, String passwordHash, ArrayList<Thread> threads, ArrayList<Reply> replies, ArrayList<Notification> notifications);

	/**
	 * Lists all users.
	 *
	 * @return The list of all users.
	 */
	ArrayList<User> list();

	/**
	 * Finds a user by their ID.
	 *
	 * @param ID The ID of the user to be found.
	 * @return The user, wrapped in an optional.
	 */
	Optional<User> findById(int ID);

	/**
	 * Finds a user by their username.
	 *
	 * @param name The username of the user to be found.
	 * @return The user, wrapped in an optional.
	 */
	Optional<User> findByUsername(String name);

	/**
	 * Finds a user by their email.
	 *
	 * @param email The email of the user to be found.
	 * @return The user, wrapped in an optional.
	 */
	Optional<User> findByEmail(String email);

	/**
	 * Deletes a user.
	 *
	 * @param ID The ID of the user to be deleted.
	 */
	void delete(int ID);
}
