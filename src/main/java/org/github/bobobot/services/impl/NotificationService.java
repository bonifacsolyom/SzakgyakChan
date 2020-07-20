package org.github.bobobot.services.impl;

import org.github.bobobot.dao.INotificationDAO;
import org.github.bobobot.dao.IUserDAO;
import org.github.bobobot.entities.CommentNotification;
import org.github.bobobot.entities.Notification;
import org.github.bobobot.entities.User;
import org.github.bobobot.entities.VoteNotification;
import org.github.bobobot.services.INotificationService;

import java.util.List;
import java.util.Optional;

public class NotificationService implements INotificationService {

	INotificationDAO<CommentNotification> commentDAO;
	INotificationDAO<VoteNotification> voteDAO;
	IUserDAO userDAO;

	public NotificationService(INotificationDAO<CommentNotification> iCommentNotificationDAO, INotificationDAO<VoteNotification> iVoteNotificationDAO, IUserDAO userDAO) {
		this.commentDAO = iCommentNotificationDAO;
		this.voteDAO = iVoteNotificationDAO;
		this.userDAO = userDAO;
	}

	private Notification getNotificationIfNotPresent(Optional<? extends Notification> notification) {
		if (!notification.isPresent()) {
			throw new IllegalArgumentException("Notification was not found!");
		}
		return notification.get();
	}

	@Override
	public CommentNotification create(CommentNotification notification) {
		UserService userService = new UserService(userDAO);
		User user = notification.getUser();
		userService.addCommentNotification(user.getID(), notification);
		return commentDAO.create(notification);
	}

	@Override
	public CommentNotification create(boolean read, User user, String replyContent) {
		return create(new CommentNotification(-1, read, user, replyContent));
	}

	@Override
	public VoteNotification create(VoteNotification notification) {
		UserService userService = new UserService(userDAO);
		User user = notification.getUser();
		userService.addVoteNotification(user.getID(), notification);
		return voteDAO.create(notification);
	}

	@Override
	public VoteNotification create(boolean read, User user, VoteNotification.VoteType voteType) {
		return create(new VoteNotification(-1, read, user, voteType));
	}

	@Override
	public CommentNotification update(CommentNotification tempNotification) {
		Optional<CommentNotification> notification = commentDAO.update(tempNotification);
		return (CommentNotification) getNotificationIfNotPresent(notification);
	}

	@Override
	public CommentNotification update(int ID, boolean read, User user, String replyContent) {
		return update(new CommentNotification(ID, read, user, replyContent));
	}

	@Override
	public VoteNotification update(VoteNotification tempNotification) {
		Optional<VoteNotification> notification = voteDAO.update(tempNotification);
		return (VoteNotification) getNotificationIfNotPresent(notification);
	}

	@Override
	public VoteNotification update(int ID, boolean read, User user, VoteNotification.VoteType voteType) {
		return update(new VoteNotification(ID, read, user, voteType));
	}

	@Override
	public CommentNotification findCommentNotificationByID(int ID) {
		Optional<CommentNotification> notification = commentDAO.selectByID(ID);
		return (CommentNotification) getNotificationIfNotPresent(notification);
	}

	@Override
	public VoteNotification findVoteNotificationByID(int ID) {
		Optional<VoteNotification> notification = voteDAO.selectByID(ID);
		return (VoteNotification) getNotificationIfNotPresent(notification);
	}

	@Override
	public List<CommentNotification> listCommentNotifications() {
		return commentDAO.list();
	}

	@Override
	public List<VoteNotification> listVoteNotifications() {
		return voteDAO.list();
	}
}
