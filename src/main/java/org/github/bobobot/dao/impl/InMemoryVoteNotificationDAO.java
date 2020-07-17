package org.github.bobobot.dao.impl;

import org.github.bobobot.dao.INotificationDAO;
import org.github.bobobot.entities.User;
import org.github.bobobot.entities.VoteNotification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class InMemoryVoteNotificationDAO extends InMemoryNotificationDAO<VoteNotification> {
	List<VoteNotification> memory = new ArrayList<>();

	@Override
	public VoteNotification create(VoteNotification notification) {
		notification.setID(memory.size());
		memory.add(notification);
		return notification;
	}

	@Override
	public Optional<VoteNotification> update(VoteNotification notification) {
		Optional<VoteNotification> memoryNotification = memory.stream()
				.filter(n -> n.getID() == notification.getID())
				.findFirst();

		if (memoryNotification.isPresent()) {
			memoryNotification.get().setRead(notification.isRead());
			memoryNotification.get().setVoteType(notification.getVoteType());
		}

		return memoryNotification;
	}
}
