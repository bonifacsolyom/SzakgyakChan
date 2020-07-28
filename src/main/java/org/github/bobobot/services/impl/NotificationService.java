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

	IUserService userService;
	@Autowired
	private INotificationRepository<CommentNotification> commentRepository;
	@Autowired
	private INotificationRepository<VoteNotification> voteRepository;
	@Autowired
	private IUserRepository userRepository;

	public NotificationService(IUserService userService) {
		this.userService = userService;
	}

	private Notification getNotificationIfPresent(Optional<? extends Notification> notification) {
		if (!notification.isPresent()) {
			throw new IllegalArgumentException("Notification was not found!");
		}
		return notification.get();
	}

	@Override
	public CommentNotification create(CommentNotification notification) {
		User user = notification.getUser();
		userService.addCommentNotification(user.getId(), notification);
		return commentRepository.save(notification);
	}

	@Override
	public CommentNotification create(boolean read, User user, String replyContent) {
		return create(new CommentNotification(read, user, replyContent));
	}

	@Override
	public VoteNotification create(VoteNotification notification) {
		User user = notification.getUser();
		userService.addVoteNotification(user.getId(), notification);
		return voteRepository.save(notification);
	}

	@Override
	public VoteNotification create(boolean read, User user, VoteNotification.VoteType voteType) {
		return create(new VoteNotification(read, user, voteType));
	}

	@Override
	public CommentNotification update(CommentNotification tempNotification) {
		getNotificationIfPresent(commentRepository.findById(tempNotification.getId())); //dobjunk errort ha nem létezik
		return commentRepository.save(tempNotification);
	}

	@Override
	public CommentNotification update(Long id, boolean read, User user, String replyContent) {
		return update(new CommentNotification(id, read, user, replyContent));
	}

	@Override
	public VoteNotification update(VoteNotification tempNotification) {
		getNotificationIfPresent(voteRepository.findById(tempNotification.getId())); //dobjunk errort ha nem létezik
		return voteRepository.save(tempNotification);
	}

	@Override
	public VoteNotification update(Long id, boolean read, User user, VoteNotification.VoteType voteType) {
		return update(new VoteNotification(id, read, user, voteType));
	}

	@Override
	public CommentNotification findCommentNotificationByID(Long id) {
		Optional<Notification> notification = commentRepository.findById(id);
		return (CommentNotification) getNotificationIfPresent(notification);
	}

	@Override
	public VoteNotification findVoteNotificationByID(Long id) {
		Optional<Notification> notification = voteRepository.findById(id);
		return (VoteNotification) getNotificationIfPresent(notification);
	}

	@Override
	public List<CommentNotification> listCommentNotifications() {
		//fúj
		return (List<CommentNotification>)(List<? extends Notification>)commentRepository.findAll();
	}

	@Override
	public List<VoteNotification> listVoteNotifications() {
		//fúj 2.0
		return (List<VoteNotification>)(List<? extends Notification>)voteRepository.findAll();
	}

	@Override
	public void deleteCommentNotification(Long id) {
		getNotificationIfPresent(commentRepository.findById(id));
		commentRepository.deleteById(id);
	}

	@Override
	public void deleteVoteNotification(Long id) {
		getNotificationIfPresent(voteRepository.findById(id));
		voteRepository.deleteById(id);
	}
}
