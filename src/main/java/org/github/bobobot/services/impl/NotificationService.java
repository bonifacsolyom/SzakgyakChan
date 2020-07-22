package org.github.bobobot.services.impl;

import org.github.bobobot.entities.CommentNotification;
import org.github.bobobot.entities.Notification;
import org.github.bobobot.entities.User;
import org.github.bobobot.entities.VoteNotification;
import org.github.bobobot.repositories.INotificationRepository;
import org.github.bobobot.repositories.IUserRepository;
import org.github.bobobot.services.INotificationService;
import org.github.bobobot.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class NotificationService implements INotificationService {

	@Autowired
	private INotificationRepository<CommentNotification> commentRepository;
	@Autowired
	private INotificationRepository<VoteNotification> voteRepository;
	@Autowired
	private IUserRepository userRepository;

	IUserService userService;

	public NotificationService(IUserService userService) {
		this.userService = userService;
	}

	private Notification getNotificationIfNotPresent(Optional<? extends Notification> notification) {
		if (!notification.isPresent()) {
			throw new IllegalArgumentException("Notification was not found!");
		}
		return notification.get();
	}

	@Override
	public CommentNotification create(CommentNotification notification) {
		User user = notification.getUser();
		userService.addCommentNotification(user.getID(), notification);
		return commentRepository.save(notification);
	}

	@Override
	public CommentNotification create(boolean read, User user, String replyContent) {
		return create(new CommentNotification(-1, read, user, replyContent));
	}

	@Override
	public VoteNotification create(VoteNotification notification) {
		User user = notification.getUser();
		userService.addVoteNotification(user.getID(), notification);
		return voteRepository.save(notification);
	}

	@Override
	public VoteNotification create(boolean read, User user, VoteNotification.VoteType voteType) {
		return create(new VoteNotification(-1, read, user, voteType));
	}

	@Override
	public CommentNotification update(CommentNotification tempNotification) {
		return commentRepository.save(tempNotification);
	}

	@Override
	public CommentNotification update(int ID, boolean read, User user, String replyContent) {
		return update(new CommentNotification(ID, read, user, replyContent));
	}

	@Override
	public VoteNotification update(VoteNotification tempNotification) {
		return voteRepository.save(tempNotification);
	}

	@Override
	public VoteNotification update(int ID, boolean read, User user, VoteNotification.VoteType voteType) {
		return update(new VoteNotification(ID, read, user, voteType));
	}

	@Override
	public CommentNotification findCommentNotificationByID(int ID) {
		Optional<Notification> notification = commentRepository.findById(ID);
		return (CommentNotification) getNotificationIfNotPresent(notification);
	}

	@Override
	public VoteNotification findVoteNotificationByID(int ID) {
		Optional<Notification> notification = voteRepository.findById(ID);
		return (VoteNotification) getNotificationIfNotPresent(notification);
	}

	@Override
	public List<? extends Notification> listCommentNotifications() {
		return commentRepository.findAll();
	}

	@Override
	public List<? extends Notification> listVoteNotifications() {
		return voteRepository.findAll();
	}
}
