package org.github.bobobot.dao.impl;

import org.github.bobobot.dao.INotificationDAO;
import org.github.bobobot.entities.CommentNotification;
import org.github.bobobot.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryCommentNotificationDAO implements INotificationDAO<CommentNotification> {
	List<CommentNotification> memory = new ArrayList<>();

	@Override
	public CommentNotification create(CommentNotification notification) {
		notification.setID(memory.size());
		memory.add(notification);
		return notification;
	}

	@Override
	public Optional<CommentNotification> update(CommentNotification notification) {
		Optional<CommentNotification> memoryNotification = memory.stream()
				.filter(n -> n.getID() == notification.getID())
				.findFirst();

		if (memoryNotification.isPresent()) {
			memoryNotification.get().setRead(notification.isRead());
			memoryNotification.get().setReplyContent(notification.getReplyContent());
		}

		return memoryNotification;
	}

	@Override
	public Optional<CommentNotification> selectByID(int ID) {
		Optional<CommentNotification> notification = memory.stream()
				.filter(n -> n.getID() == ID)
				.findFirst();
		return notification;
	}

	@Override
	public List<CommentNotification> selectByUser(User user) {
		return null;
	}

	@Override
	public List<CommentNotification> list() {
		return memory;
	}

	@Override
	public Optional<CommentNotification> delete(int ID) {
		Optional<CommentNotification> notification = memory.stream()
				.filter(n -> n.getID() == ID)
				.findFirst();
		if (notification.isPresent()) {
			memory.remove(notification.get());
		}

		return notification;
	}
}
