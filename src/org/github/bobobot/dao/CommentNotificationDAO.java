package org.github.bobobot.dao;

import org.github.bobobot.entities.CommentNotification;

import java.util.ArrayList;
import java.util.Optional;

public interface CommentNotificationDAO {
	/**
	 * Creates a notification.
	 * @return The created notification.
	 */
	CommentNotification create(boolean read, String replyContent);

	/**
	 * Updates a notification.
	 * @param ID The ID of the notification.
	 * @return The updated notification.
	 */
	Optional<CommentNotification> update(int ID, boolean read, String replyContent);

	/**
	 * Selects a notiication by its ID.
	 * @param ID The ID of the notification to be selected.
	 * @return The selected notification, wrapped in an optional.
	 */
	Optional<CommentNotification> select(int ID);

	/**
	 * Lists all existing notifications.
	 * @return A list of all existing notifications.
	 */
	ArrayList<CommentNotification> list();

	/**
	 * Deletes a notification.
	 * @param ID The ID of the notification to be deleted.
	 * @return The deleted norification, wrapped in an optional.
	 */
	Optional<CommentNotification> delete(int ID);
}
