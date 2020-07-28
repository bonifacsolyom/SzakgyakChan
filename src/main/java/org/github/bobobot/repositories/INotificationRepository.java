package org.github.bobobot.repositories;

import org.github.bobobot.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface INotificationRepository<GenericNotification extends Notification> extends JpaRepository<Notification, Long> {

	//TODO: ez még tuti nem jó
	@Query(value = "SELECT * FROM #{#entityName} n WHERE n.ORIGINAL_REPLY_ID in (SELECT ID from User u WHERE id = ?1)", nativeQuery = true)
	List<GenericNotification> getByUserId(Long id);

}
