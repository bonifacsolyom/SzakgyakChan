package org.github.bobobot.services;

import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.*;

import java.util.List;
import java.util.Optional;

public interface IUserService {
	/**
	 * Creates a user.
	 *
	 * @param tempUser The user to be created.
	 * @return The created user.
	 */
	User register(User tempUser);

	/**
	 * Creates a user. Consider using this method instead:
	 *
	 * @param isAdmin              Specifies whether a user is an administrator or not.
	 * @param name                 The name of the user.
	 * @param email                The email of the user.
	 * @param password             The password hash of the user.
	 * @param threads              The threads this user has posted.
	 * @param replies              The replies this user has posted.
	 * @param commentNotifications The comment notifications this user has.
	 * @param voteNotifications    The vote notifications this user has.
	 * @return The created user.
	 * @see IUserService#register(boolean, String, String, String)
	 */
	default User register(boolean isAdmin, String name, String email, String password, List<Thread> threads, List<Reply> replies, List<CommentNotification> commentNotifications, List<VoteNotification> voteNotifications) {
		return register(new User(isAdmin, name, email, password, threads, replies, commentNotifications, voteNotifications));
	}

	/**
	 * Creates a user.
	 *
	 * @param isAdmin  Specifies whether a user is an administrator or not.
	 * @param name     The name of the user.
	 * @param email    The email of the user.
	 * @param password The password of the user.
	 * @return The created user.
	 */
	default User register(boolean isAdmin, String name, String email, String password) {
		return register(new User(isAdmin, name, email, password));
	}

	Optional<User> login(String name, String password);

	/**
	 * Updates a user.
	 *
	 * @param tempUser The user to be updated.
	 * @return The updated user.
	 */
	User update(User tempUser);

	/**
	 * Updates a user.
	 *
	 * @param id      The ID of the user to be updated.
	 * @param isAdmin Specifies whether a user is an administrator or not.
	 * @param name    The name of the user.
	 * @param email   The email of the user.
	 * @return The updated user.
	 */
	default User update(Long id, boolean isAdmin, String name, String email, String password) {
		return update(new User(id, isAdmin, name, email, password));
	}

	/**
	 * Updates a user.
	 *
	 * @param id                   The ID of the user to be updated.
	 * @param isAdmin              Specifies whether a user is an administrator or not.
	 * @param name                 The name of the user.
	 * @param email                The email of the user.
	 * @param password             The password hash of the user.
	 * @param threads              The threads this user has posted.
	 * @param replies              The replies this user has posted.
	 * @param commentNotifications The comment notifications this user has.
	 * @param voteNotifications    The vote notifications this user has.
	 * @return The updated user.
	 */
	default User update(Long id, boolean isAdmin, String name, String email, String password, List<Thread> threads, List<Reply> replies, List<CommentNotification> commentNotifications, List<VoteNotification> voteNotifications) {
		return update(new User(id, isAdmin, name, email, password, threads, replies, commentNotifications, voteNotifications));
	}

	/**
	 * Adds a comment notification to a user
	 *
	 * @param user           the ID of the user
	 * @param notification The comment notification to be added
	 * @return The updated user
	 */
	User addCommentNotification(User user, CommentNotification notification);

	/**
	 * Adds a vote notification to a user
	 *
	 * @param id           the ID of the user
	 * @param notification The vote notification to be added
	 * @return The updated user
	 */
	User addVoteNotification(User user, VoteNotification notification);

	/**
	 * Lists all users.
	 *
	 * @return The list of all users.
	 */
	List<User> list();

	/**
	 * Finds a user by their ID.
	 *
	 * @param id The ID of the user to be found.
	 * @return The user, wrapped in an optional.
	 */
	User findById(Long id);

	/**
	 * Finds a user by their username.
	 *
	 * @param name The username of the user to be found.
	 * @return The user, wrapped in an optional.
	 */
	User findByUsername(String name);

	/**
	 * Finds a user by their email.
	 *
	 * @param email The email of the user to be found.
	 * @return The user, wrapped in an optional.
	 */
	User findByEmail(String email);

	/**
	 * Deletes a user.
	 *
	 * @param id The ID of the user to be deleted.
	 */
	void delete(Long id);

	/**
	 * Gets all notifications of a user
	 *
	 * @param id The ID of the user
	 * @return The notifications of the user
	 */
	List<Notification> getUsersNotifications(Long id);


	/**
	 * Gets all active (not read) notifications of a user
	 *
	 * @param id The ID of the user
	 * @return The active notifications of the user
	 */
	List<Notification> getUsersActiveNotifications(Long id);

	/**
	 * Counts a user's notifications
	 *
	 * @param id The ID of the user
	 * @return The amount of notifications of the user
	 */
	int getUsersNotificationCount(Long id);

	/**
	 * Counts a user's active notifications
	 *
	 * @param id The ID of the user
	 * @return The amount of active notifications of the user
	 */
	int getUsersActiveNotificationCount(Long id);
}
