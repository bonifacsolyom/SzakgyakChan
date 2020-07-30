package org.github.bobobot.services.impl;

import org.github.bobobot.entities.*;
import org.github.bobobot.repositories.ICommentNotificationRepository;
import org.github.bobobot.repositories.IUserRepository;
import org.github.bobobot.repositories.IVoteNotificationRepository;
import org.github.bobobot.services.INotificationService;
import org.github.bobobot.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

//TODO: Mi lesz a célja a Notification-nek? A felhasználó értesítve lesz valahogy? Akkor szükséges ezt menteni?
public class NotificationService implements INotificationService {

	IUserService userService;
	@Autowired
	private ICommentNotificationRepository commentRepository;
	@Autowired
	private IVoteNotificationRepository voteRepository;
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

	private void checkIfRepliesInTheSameThread(Reply originalReply, Reply newReply) {
		if (originalReply.getThread().getId().equals(newReply.getThread().getId())) {
			throw new IllegalArgumentException("Replies not in the same thread!");
		}
	}

	@Override
	public void create(CommentNotification notification) {
		checkIfRepliesInTheSameThread(notification.getOriginalReply(), notification.getOtherUsersReply());
		User user = notification.getUser();
		userService.addCommentNotification(user.getId(), notification);
	}

	@Override
	public void create(boolean read, Reply originalReply, Reply otherUsersReply) {
		create(new CommentNotification(read, originalReply, otherUsersReply));
	}

	@Override
	public void create(VoteNotification notification) {
		User user = notification.getUser();
		userService.addVoteNotification(user.getId(), notification);
	}

	@Override
	public void create(boolean read, Reply originalReply, VoteNotification.VoteType voteType) {
		create(new VoteNotification(read, originalReply, voteType));
	}

	@Override
	public CommentNotification update(CommentNotification tempNotification) {
		checkIfRepliesInTheSameThread(tempNotification.getOriginalReply(), tempNotification.getOtherUsersReply());
		getNotificationIfPresent(commentRepository.findById(tempNotification.getId())); //dobjunk errort ha nem létezik
		return commentRepository.save(tempNotification);
	}

	@Override
	public CommentNotification update(Long id, boolean read, Reply originalReply, Reply otherUsersReply) {
		return update(new CommentNotification(id, read, originalReply, otherUsersReply));
	}

	@Override
	public VoteNotification update(VoteNotification tempNotification) {
		getNotificationIfPresent(voteRepository.findById(tempNotification.getId())); //dobjunk errort ha nem létezik
		return voteRepository.save(tempNotification);
	}

	@Override
	public VoteNotification update(Long id, boolean read, Reply originalReply, VoteNotification.VoteType voteType) {
		return update(new VoteNotification(id, read, originalReply, voteType));
	}

	@Override
	public CommentNotification findCommentNotificationByID(Long id) {
		Optional<CommentNotification> notification = commentRepository.findById(id);
		return (CommentNotification) getNotificationIfPresent(notification);
	}

	@Override
	public VoteNotification findVoteNotificationByID(Long id) {
		Optional<VoteNotification> notification = voteRepository.findById(id);
		return (VoteNotification) getNotificationIfPresent(notification);
	}

	@Override
	public List<CommentNotification> getCommentNotificationsByUserId(Long id) {
		return commentRepository.getByUserId(id);
	}

	@Override
	public List<VoteNotification> getVoteNotificationsByUserId(Long id) {
		return voteRepository.getByUserId(id);
	}

	@Override
	public List<CommentNotification> listCommentNotifications() {
		//fúj
		return (List<CommentNotification>) (List<? extends Notification>) commentRepository.findAll();
	}

	@Override
	public List<VoteNotification> listVoteNotifications() {
		//fúj 2.0
		return (List<VoteNotification>) (List<? extends Notification>) voteRepository.findAll();
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
