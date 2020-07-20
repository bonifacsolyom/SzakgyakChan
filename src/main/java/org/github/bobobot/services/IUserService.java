package org.github.bobobot.services;

import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.*;

import java.util.List;

public interface IUserService {
	//TODO: Jelszóhash helyett ez még csak jelszót kapjon

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
	 * @param passwordHash         The password hash of the user.
	 * @param threads              The threads this user has posted.
	 * @param replies              The replies this user has posted.
	 * @param commentNotifications The comment notifications this user has.
	 * @param voteNotifications    The vote notifications this user has.
	 * @return The created user.
	 * @see IUserService#register(boolean, String, String, String)
	 */
	User register(boolean isAdmin, String name, String email, String passwordHash, List<Thread> threads, List<Reply> replies, List<CommentNotification> commentNotifications, List<VoteNotification> voteNotifications);

	/**
	 * Creates a user.
	 *
	 * @param isAdmin      Specifies whether a user is an administrator or not.
	 * @param name         The name of the user.
	 * @param email        The email of the user.
	 * @param passwordHash The password hash of the user.
	 * @return The created user.
	 */
	User register(boolean isAdmin, String name, String email, String passwordHash);

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
	 * @param ID      The ID of the user to be updated.
	 * @param isAdmin Specifies whether a user is an administrator or not.
	 * @param name    The name of the user.
	 * @param email   The email of the user.
	 * @return The updated user.
	 */
	User update(int ID, boolean isAdmin, String name, String email, String passwordHash);

	/**
	 * Updates a user.
	 *
	 * @param ID                   The ID of the user to be updated.
	 * @param isAdmin              Specifies whether a user is an administrator or not.
	 * @param name                 The name of the user.
	 * @param email                The email of the user.
	 * @param passwordHash         The password hash of the user.
	 * @param threads              The threads this user has posted.
	 * @param replies              The replies this user has posted.
	 * @param commentNotifications The comment notifications this user has.
	 * @param voteNotifications    The vote notifications this user has.
	 * @return The updated user.
	 */
	User update(int ID, boolean isAdmin, String name, String email, String passwordHash, List<Thread> threads, List<Reply> replies, List<CommentNotification> commentNotifications, List<VoteNotification> voteNotifications);

	User addCommentNotification(int ID, CommentNotification notification);

	User addVoteNotification(int ID, VoteNotification notification);

	/**
	 * Lists all users.
	 *
	 * @return The list of all users.
	 */
	List<User> list();

	/**
	 * Finds a user by their ID.
	 *
	 * @param ID The ID of the user to be found.
	 * @return The user, wrapped in an optional.
	 */
	User findById(int ID);

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
	 * @param ID The ID of the user to be deleted.
	 */
	void delete(int ID);

	List<Notification> getUsersNotifications(int ID);

	List<Notification> getUsersActiveNotifications(int ID);

	int getUsersNotificationCount(int ID);

	int getUsersActiveNotificationCount(int ID);
}
