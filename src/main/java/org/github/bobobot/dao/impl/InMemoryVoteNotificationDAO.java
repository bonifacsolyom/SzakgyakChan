package org.github.bobobot.dao.impl;

import org.github.bobobot.dao.INotificationDAO;
import org.github.bobobot.entities.User;
import org.github.bobobot.entities.VoteNotification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryVoteNotificationDAO implements INotificationDAO<VoteNotification> {
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

	@Override
	public Optional<VoteNotification> selectByID(int ID) {
		Optional<VoteNotification> notification = memory.stream()
				.filter(n -> n.getID() == ID)
				.findFirst();
		return notification;
	}

	@Override
	public List<VoteNotification> selectByUser(User user) {
		return null;
	}


	@Override
	public List<VoteNotification> list() {
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
