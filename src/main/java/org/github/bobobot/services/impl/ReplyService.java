package org.github.bobobot.services.impl;

import org.github.bobobot.entities.CommentNotification;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.User;
import org.github.bobobot.entities.VoteNotification.VoteType;
import org.github.bobobot.repositories.ICommentNotificationRepository;
import org.github.bobobot.repositories.IReplyRepository;
import org.github.bobobot.services.INotificationService;
import org.github.bobobot.services.IReplyService;
import org.github.bobobot.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ReplyService implements IReplyService {

	@Autowired
	private IReplyRepository replyRepository;

	@Autowired
	private INotificationService notificationService;

	@Autowired
	private IUserService userService;


	private Reply getReplyIfPresent(Optional<Reply> reply) {
		if (!reply.isPresent()) {
			throw new IllegalArgumentException("Reply was not found!");
		}
		return reply.get();
	}

	private void notifyUsersAboutReplies(Reply newReply) {
		for (Reply threadReply : newReply.getThread().getReplies()) {
			//A NotificationService#create hasonló dolgot csinál, csak előtte van valamiyen validáció, itt ez nincs meg, nem tudom lehet-e ez probléma. Ezért
			// célszerű, hogy a service-ek egymást hivogassák ne közvetlenül a db réteget, mert üzleti logika kimaradhat.

			CommentNotification notification = notificationService.create(false, threadReply, newReply);
		}
	}

	@Override
	public Reply post(Reply tempReply) {
		//Értesítjük minden reply userét, hogy egy új reply érkezett a threadbe
		Reply reply = replyRepository.save(tempReply);
		notifyUsersAboutReplies(reply);
		reply.getThread().addReply(reply);
		return reply;
	}

	@Override
	public Reply update(Reply tempReply) {
		getReplyIfPresent(replyRepository.findById(tempReply.getId())); //dobjunk errort ha nem létezik
		return replyRepository.save(tempReply);
	}

	@Override
	public List<Reply> list() {
		return replyRepository.findAll();
	}

	@Override
	public Reply findById(Long id) {
		Optional<Reply> reply = replyRepository.findById(id);
		return getReplyIfPresent(reply);
	}

	@Override
	public List<Reply> listByThread(Thread thread) {
		return replyRepository.findAllByThread(thread);
	}

	@Override
	public Reply vote(Long id, VoteType voteType) {
		Reply reply = findById(id);
		reply = changeVote(reply, voteType);
		return update(reply);
	}

	private Reply changeVote(Reply reply, VoteType voteType) {
		switch (voteType) {
			case UPVOTE:
				reply.upvote();
				break;
			case DOWNVOTE:
				reply.downvote();
				break;
		}

		return reply;
	}

	@Override
	public void delete(Long id) {
		getReplyIfPresent(replyRepository.findById(id)); //dobjunk errort ha nem létezik
		replyRepository.deleteById(id);
	}
}
