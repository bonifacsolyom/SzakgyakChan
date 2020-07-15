package org.github.bobobot.dao.impl;

import org.github.bobobot.dao.CommentNotificationDAO;
import org.github.bobobot.entities.CommentNotification;
import org.github.bobobot.entities.Notification;
import org.github.bobobot.entities.VoteNotification;

import java.util.ArrayList;
import java.util.Optional;

public class InMemoryCommentNotification implements CommentNotificationDAO {
	ArrayList<CommentNotification> memory = new ArrayList<>();

	@Override
	public CommentNotification create(boolean read, String replyContent) {
		CommentNotification notification = new CommentNotification(replyContent, read);
		memory.add(notification);
		return notification;
	}

	@Override
	public Optional<CommentNotification> update(int ID, boolean read, String replyContent) {
		Optional<CommentNotification> notification = memory.stream()
				.filter(n -> n.getID() == ID)
				.findFirst();

		if (notification.isPresent()) {
			notification.get().setRead(read);
			notification.get().setReplyContent(replyContent);
		}

		return notification;
	}

	@Override
	public Optional<CommentNotification> select(int ID) {
		Optional<CommentNotification> notification = memory.stream()
				.filter(n -> n.getID() == ID)
				.findFirst();
		return notification;
	}

	@Override
	public ArrayList<CommentNotification> list() {
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
