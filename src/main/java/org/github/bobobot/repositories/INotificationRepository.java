package org.github.bobobot.repositories;

import org.github.bobobot.entities.Notification;
import org.github.bobobot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface INotificationRepository extends JpaRepository<Notification, Integer> {

	@Override
	Notification save(Notification notification);

	@Override
	Optional<Notification> findById(Integer id);

	Optional<Notification> findByUser(User user);

	@Override
	List<Notification> findAll();

	@Override
	void delete(Notification notification);
}
