package org.github.bobobot.services.impl;

import org.github.bobobot.dao.INotificationDAO;
import org.github.bobobot.dao.impl.InMemoryCommentNotificationDAO;
import org.github.bobobot.dao.impl.InMemoryVoteNotificationDAO;
import org.github.bobobot.entities.*;
import org.github.bobobot.services.INotificationService;

import java.util.List;
import java.util.Optional;

public class NotificationService implements INotificationService {

	INotificationDAO<CommentNotification> commentDAO = new InMemoryCommentNotificationDAO();
	INotificationDAO<VoteNotification> voteDAO = new InMemoryVoteNotificationDAO();

	@Override
	public CommentNotification create(CommentNotification notification) {
		return commentDAO.create(notification);
	}

	@Override
	public CommentNotification create(boolean read, User user, String replyContent) {
		return create(new CommentNotification(-1, read, user, replyContent));
	}

	@Override
	public VoteNotification create(VoteNotification notification) {
		return voteDAO.create(notification);
	}

	@Override
	public VoteNotification create(boolean read, User user, VoteNotification.VoteType voteType) {
		return create(new VoteNotification(-1, read, user, voteType));
	}

	@Override
	public CommentNotification update(CommentNotification tempNotification) {
		Optional<CommentNotification> notification = commentDAO.update(tempNotification);
		if (!notification.isPresent()) { throw new IllegalArgumentException("Comment notification was not found!"); }
		return notification.get();
	}

	@Override
	public CommentNotification update(int ID, boolean read, User user, String replyContent) {
		return update(new CommentNotification(ID, read, user, replyContent));
	}

	@Override
	public VoteNotification update(VoteNotification tempNotification) {
		Optional<VoteNotification> notification = voteDAO.update(tempNotification);
		if (!notification.isPresent()) { throw new IllegalArgumentException("Vote notification was not found!"); }
		return notification.get();
	}

	@Override
	public VoteNotification update(int ID, boolean read, User user, VoteNotification.VoteType voteType) {
		return update(new VoteNotification(ID, read, user, voteType));
	}

	@Override
	public CommentNotification findCommentNotificationByID(int ID) {
		Optional<CommentNotification> notification = commentDAO.selectByID(ID);
		if (!notification.isPresent()) { throw new IllegalArgumentException("Comment Notification was not found!"); }
		return notification.get();
	}

	@Override
	public VoteNotification findVoteNotificationByID(int ID) {
			Optional<VoteNotification> notification = voteDAO.selectByID(ID);
			if (!notification.isPresent()) { throw new IllegalArgumentException("Comment Notification was not found!"); }
			return notification.get();
	}

	@Override
	public List<Notification> getUsersNotifications(User user) {
		return null;
	}

	@Override
	public List<Notification> getUsersActiveNotifications(User user) {
		return null;
	}

	@Override
	public int getUsersNotificationCount(User user) {
		return 0;
	}

	@Override
	public int getUsersActiveNotificationCount(User user) {
		return 0;
	}
}
