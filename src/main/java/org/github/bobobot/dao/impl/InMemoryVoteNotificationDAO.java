package org.github.bobobot.dao.impl;

import org.github.bobobot.entities.VoteNotification;

import java.util.Optional;

public class InMemoryVoteNotificationDAO extends InMemoryNotificationDAO<VoteNotification> {

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
