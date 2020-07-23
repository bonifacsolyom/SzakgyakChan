package org.github.bobobot.repositories;

import org.github.bobobot.entities.Notification;
import org.github.bobobot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface INotificationRepository<GenericNotification extends Notification> extends JpaRepository<Notification, Long> {

	Optional<GenericNotification> findByUser(User user);

}
