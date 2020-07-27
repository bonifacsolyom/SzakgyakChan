package org.github.bobobot.dao;

import org.github.bobobot.entities.Notification;
import org.github.bobobot.entities.User;

import java.util.List;
import java.util.Optional;

public interface INotificationDAO<GenericNotification extends Notification> {
	/**
	 * Creates a notification.
	 *
	 * @param notification The notification to be created.
	 * @return The created notification.
	 */
	GenericNotification create(GenericNotification notification);

	/**
	 * Updates a notification.
	 *
	 * @param notification The notification to be updated.
	 * @return The updated notification.
	 */
	Optional<GenericNotification> update(GenericNotification notification);

	/**
	 * Selects a notification by its ID.
	 *
	 * @param id The ID of the notification to be selected.
	 * @return The selected notification, wrapped in an optional.
	 */
	Optional<GenericNotification> selectByID(int id);

	/**
	 * Selects all of a user's notifications.
	 *
	 * @param user The user of whom we want to get the notifications of.
	 * @return A list of all the user's notifications.
	 */
	List<GenericNotification> selectByUser(User user);

	/**
	 * Lists all existing notifications.
	 *
	 * @return A list of all existing notifications.
	 */
	List<GenericNotification> list();

	/**
	 * Deletes a notification.
	 *
	 * @param id The ID of the notification to be deleted.
	 * @return The deleted norification, wrapped in an optional.
	 */
	Optional<GenericNotification> delete(int id);
}
