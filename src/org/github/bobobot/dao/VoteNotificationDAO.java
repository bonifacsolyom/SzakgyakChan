package org.github.bobobot.dao;

import org.github.bobobot.entities.Notification;
import org.github.bobobot.entities.VoteNotification;
import org.github.bobobot.entities.VoteNotification.VoteType;

import java.util.Optional;

public interface VoteNotificationDAO extends NotificationDAO {

	/**
	 * Creates a notification.
	 * @param read Whether the notification was read by the user or not
	 * @param voteType The type of the vote (up or downvote)
	 * @return The created notification.
	 */
	Notification create(boolean read, VoteType voteType);

	/**
	 * Updates a notification.
	 * @param ID The ID of the notification.
	 * @param read Whether the notification was read by the user or not
	 * @param voteType The type of the vote (up or downvote)
	 * @return The updated notification.
	 */
	Optional<Notification> update(int ID, boolean read, VoteType voteType);
}
