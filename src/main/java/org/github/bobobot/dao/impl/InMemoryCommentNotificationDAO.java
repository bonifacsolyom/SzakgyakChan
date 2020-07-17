package org.github.bobobot.dao.impl;

import org.github.bobobot.dao.INotificationDAO;
import org.github.bobobot.entities.CommentNotification;
import org.github.bobobot.entities.User;
import org.github.bobobot.entities.VoteNotification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryCommentNotificationDAO extends InMemoryNotificationDAO<CommentNotification> {
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
}
