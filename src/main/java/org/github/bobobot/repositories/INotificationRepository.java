package org.github.bobobot.repositories;

import org.github.bobobot.entities.Notification;
import org.github.bobobot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface INotificationRepository<GenericNotification extends Notification> extends JpaRepository<Notification, Integer> {

	Optional<GenericNotification> findByUser(User user);

}
