package org.github.bobobot.dao;

import org.github.bobobot.entities.Notification;
import org.github.bobobot.entities.VoteNotification;
import org.github.bobobot.entities.VoteNotification.VoteType;

import java.util.ArrayList;
import java.util.Optional;

public interface VoteNotificationDAO {

	/**
	 * Creates a notification.
	 * @param read Whether the notification was read by the user or not
	 * @param voteType The type of the vote (up or downvote)
	 * @return The created notification.
	 */
	VoteNotification create(boolean read, VoteType voteType);

	/**
	 * Updates a notification.
	 * @param ID The ID of the notification.
	 * @param read Whether the notification was read by the user or not
	 * @param voteType The type of the vote (up or downvote)
	 * @return The updated notification.
	 */
	Optional<VoteNotification> update(int ID, boolean read, VoteType voteType);

	/**
	 * Selects a notiication by its ID.
	 * @param ID The ID of the notification to be selected.
	 * @return The selected notification, wrapped in an optional.
	 */
	Optional<VoteNotification> select(int ID);

	/**
	 * Lists all existing notifications.
	 * @return A list of all existing notifications.
	 */
	ArrayList<VoteNotification> list();

	/**
	 * Deletes a notification.
	 * @param ID The ID of the notification to be deleted.
	 * @return The deleted norification, wrapped in an optional.
	 */
	Optional<VoteNotification> delete(int ID);
}
