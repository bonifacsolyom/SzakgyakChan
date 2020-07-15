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
	public VoteNotification create(boolean read, VoteType voteType) {
		VoteNotification notification = new VoteNotification(voteType, read);
		memory.add(notification);
		return notification;
	}

	@Override
	public Optional<VoteNotification> update(int ID, boolean read, VoteType voteType) {
		Optional<VoteNotification> notification = memory.stream()
				.filter(n -> n.getID() == ID)
				.findFirst();

		if (notification.isPresent()) {
			notification.get().setRead(read);
			notification.get().setVoteType(voteType);
		}

		return notification;
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
