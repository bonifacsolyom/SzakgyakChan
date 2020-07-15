package org.github.bobobot.dao;

import org.github.bobobot.entities.Notification;

import java.util.Optional;

public interface CommentNotificationDAO extends NotificationDAO {
	/**
	 * Creates a notification.
	 * @return The created notification.
	 */
	Notification create(boolean read, String replyContent);

	/**
	 * Updates a notification.
	 * @param ID The ID of the notification.
	 * @return The updated notification.
	 */
	Optional<Notification> update(int ID, boolean read, String replyContent);
}
