package org.github.bobobot.dao.impl;

import org.github.bobobot.dao.VoteNotificationDAO;
import org.github.bobobot.entities.Notification;
import org.github.bobobot.entities.VoteNotification;
import org.github.bobobot.entities.VoteNotification.VoteType;

import java.util.ArrayList;
import java.util.Optional;

public class InMemoryVoteNotification implements VoteNotificationDAO {
	ArrayList<VoteNotification> memory = new ArrayList<>();

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

	@Override
	public Optional<VoteNotification> select(int ID) {
		Optional<VoteNotification> notification = memory.stream()
				.filter(n -> n.getID() == ID)
				.findFirst();
		return notification;
	}

	@Override
	public ArrayList<VoteNotification> list() {
		return memory;
	}

	@Override
	public Optional<VoteNotification> delete(int ID) {
		Optional<VoteNotification> notification = memory.stream()
				.filter(n -> n.getID() == ID)
				.findFirst();
		if (notification.isPresent()) {
			memory.remove(notification.get());
		}

		return notification;
	}
}
