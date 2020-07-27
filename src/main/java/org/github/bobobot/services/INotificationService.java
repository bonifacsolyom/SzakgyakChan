package org.github.bobobot.services;

import org.github.bobobot.entities.CommentNotification;
import org.github.bobobot.entities.Notification;
import org.github.bobobot.entities.User;
import org.github.bobobot.entities.VoteNotification;

import java.util.List;

public interface INotificationService {

	//TODO: túl sok a kódismétlődés, ezt biztos meg lehet oldani szebben is
	//valahogy a két notificationtípust jobban kéne együtt kezelni

	/**
	 * Creates a new notification.
	 *
	 * @param notification The notification to be created.
	 * @return The newly created notification.
	 */
	CommentNotification create(CommentNotification notification);

	/**
	 * Creates a comment notification with the arguments provided.
	 *
	 * @param read         Whether the notification was read or not
	 * @param user         The user that the notification belongs to
	 * @param replyContent The content of the reply that the notification was
	 * @return The newly created notification
	 */
	CommentNotification create(boolean read, User user, String replyContent);

	/**
	 * Creates a vote notification.
	 *
	 * @param notification The notification to be created
	 * @return The newly created notification.
	 */
	VoteNotification create(VoteNotification notification);

	/**
	 * Creates a vote notification with the arguments provided.
	 *
	 * @param read     Whether the notification was read or not
	 * @param user     The user that the notification belongs to
	 * @param voteType The type of the vote (UPVOTE or DOWNVOTE)
	 * @return The newly created notification.
	 */
	VoteNotification create(boolean read, User user, VoteNotification.VoteType voteType);

	/**
	 * Updates a comment notification.
	 *
	 * @param notification The comment notification to be updated.
	 * @return The updated comment notification.
	 */
	CommentNotification update(CommentNotification notification);

	/**
	 * Updates a comment notification with the arguments provided.
	 *
	 * @param read         Whether the notification was read or not
	 * @param user         The user that the notification belongs to
	 * @param replyContent The content of the reply that the notification was
	 * @return The updated notification
	 */
	CommentNotification update(Long id, boolean read, User user, String replyContent);

	/**
	 * Updates a vote notification.
	 *
	 * @param notification The notification to be created
	 * @return The updated notification.
	 */
	VoteNotification update(VoteNotification notification);

	/**
	 * Updates a vote notification with the arguments provided.
	 *
	 * @param read     Whether the notification was read or not
	 * @param user     The user that the notification belongs to
	 * @param voteType The type of the vote (UPVOTE or DOWNVOTE)
	 * @return The updated notification.
	 */
	VoteNotification update(Long id, boolean read, User user, VoteNotification.VoteType voteType);

	/**
	 * Finds a comment notification by its ID
	 *
	 * @param id The ID of the comment notification
	 * @return The comment notification if found, otherwise throws an error
	 */
	CommentNotification findCommentNotificationByID(Long id);

	/**
	 * Finds a vote notification by its ID
	 *
	 * @param id The ID of the vote notification
	 * @return The vote notification if found, otherwise throws an error
	 */
	VoteNotification findVoteNotificationByID(Long id);

	/**
	 * Lists all comment notifications
	 *
	 * @return A list of all comment notifications
	 */
	List<? extends Notification> listCommentNotifications();

	/**
	 * Lists all vote notifications
	 *
	 * @return A list of all vote notifications
	 */
	List<? extends Notification> listVoteNotifications();

}
