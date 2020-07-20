package org.github.bobobot.services;

import org.github.bobobot.entities.CommentNotification;
import org.github.bobobot.entities.User;
import org.github.bobobot.entities.VoteNotification;

import java.util.List;

public interface INotificationService {

	CommentNotification create(CommentNotification notification);

	CommentNotification create(boolean read, User user, String replyContent);

	VoteNotification create(VoteNotification notification);

	VoteNotification create(boolean read, User user, VoteNotification.VoteType voteType);

	CommentNotification update(CommentNotification notification);

	CommentNotification update(int ID, boolean read, User user, String replyContent);

	VoteNotification update(VoteNotification notification);

	VoteNotification update(int ID, boolean read, User user, VoteNotification.VoteType voteType);

	CommentNotification findCommentNotificationByID(int ID);

	VoteNotification findVoteNotificationByID(int ID);

	List<CommentNotification> listCommentNotifications();

	List<VoteNotification> listVoteNotifications();

}
