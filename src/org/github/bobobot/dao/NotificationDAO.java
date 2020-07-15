package org.github.bobobot.dao;

import org.github.bobobot.entities.Notification;

import java.util.ArrayList;
import java.util.Optional;

public interface NotificationDAO {

	/**
	 * Selects a notiication by its ID.
	 * @param ID The ID of the notification to be selected.
	 * @return The selected notification, wrapped in an optional.
	 */
	Optional<Notification> select(int ID);

	/**
	 * Lists all existing notifications.
	 * @return A list of all existing notifications.
	 */
	ArrayList<Notification> list();

	/**
	 * Deletes a notification.
	 * @param ID The ID of the notification to be deleted.
	 * @return The deleted norification, wrapped in an optional.
	 */
 	Optional<Notification> delete(int ID);
}
