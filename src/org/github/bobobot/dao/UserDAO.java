package org.github.bobobot.dao;

import org.github.bobobot.entities.Notification;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.User;

import java.util.ArrayList;
import java.util.Optional;

public interface UserDAO {
	/**
	 * Creates a new user.
	 * @param isAdmin Specifies whether a user is an administrator or not.
	 * @param name The name of the user.
	 * @param email The email of the user.
	 * @param passwordHash The password hash of the user.
	 * @param threads The threads this user has posted.
	 * @param replies The replies this user has posted.
	 * @param notifications The notifications this user has.
	 * @return The created user.
	 */
	User create(boolean isAdmin, String name, String email, String passwordHash, ArrayList<Thread> threads, ArrayList<Reply> replies, ArrayList<Notification> notifications);

	/**
	 * Updates a user.
	 * @param ID The ID of the user to be updated.
	 * @param isAdmin Specifies whether a user is an administrator or not.
	 * @param name The name of the user.
	 * @param email The email of the user.
	 * @param passwordHash The password hash of the user.
	 * @param threads The threads this user has posted.
	 * @param replies The replies this user has posted.
	 * @param notifications The notifications this user has.
	 * @return The updated user.
	 */
	Optional<User> update(int ID, boolean isAdmin, String name, String email, String passwordHash, ArrayList<Thread> threads, ArrayList<Reply> replies, ArrayList<Notification> notifications);

	/**
	 * Selects a user by their ID.
	 * @param ID The ID of the user to be selected.
	 * @return The selected user, wrapped in an optional.
	 */
	Optional<User> select(int ID);

	/**
	 * Deletes a user.
	 * @param ID The ID of the user to be deleted.
	 * @return The deleted user, wrapped in an optional.
	 */
	Optional<User> delete(int ID);
}
