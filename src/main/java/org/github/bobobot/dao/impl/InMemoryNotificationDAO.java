package org.github.bobobot.dao.impl;

import org.github.bobobot.dao.INotificationDAO;
import org.github.bobobot.entities.Notification;
import org.github.bobobot.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class InMemoryNotificationDAO<GenericNotification extends Notification> implements INotificationDAO<GenericNotification> {
	List<GenericNotification> memory = new ArrayList<>();

	@Override
	public Optional<GenericNotification> selectByID(int ID) {
		Optional<GenericNotification> notification = memory.stream()
				.filter(n -> n.getID() == ID)
				.findFirst();
		return notification;
	}

	@Override
	public List<GenericNotification> selectByUser(User user) {
		List<GenericNotification> notifications = memory.stream()
				.filter(n -> n.getUser().equals(user))
				.collect(Collectors.toList());
		return notifications;
	}

	@Override
	public List<GenericNotification> list() {
		return memory;
	}

	@Override
	public Optional<GenericNotification> delete(int ID) {
		Optional<GenericNotification> notification = memory.stream()
				.filter(n -> n.getID() == ID)
				.findFirst();
		if (notification.isPresent()) {
			memory.remove(notification.get());
		}
		return notification;
	}
}
