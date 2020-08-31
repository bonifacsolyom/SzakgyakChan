package org.github.bobobot.services.impl;

import org.github.bobobot.entities.CommentNotification;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.VoteNotification;
import org.github.bobobot.entities.VoteNotification.VoteType;
import org.github.bobobot.repositories.IReplyRepository;
import org.github.bobobot.services.INotificationService;
import org.github.bobobot.services.IReplyService;
import org.github.bobobot.services.IThreadService;
import org.github.bobobot.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReplyService implements IReplyService {

	@Autowired
	private IReplyRepository replyRepository;

	@Autowired
	private INotificationService notificationService;

	@Autowired
	private IThreadService threadService;

	@Autowired
	private IUserService userService;

	@Autowired
	private EntityManager entityManager;


	private Reply getReplyIfPresent(Optional<Reply> reply) {
		if (!reply.isPresent()) {
			throw new IllegalArgumentException("Reply was not found!");
		}
		return reply.get();
	}

	@Transactional
	void notifyUsersAboutReplies(Reply newReply) {
		List<Long> notifiedUserIds = new ArrayList<>();
		Long currentUserId = newReply.getUser().getId();
		for (Reply threadReply : newReply.getThread().getReplies()) {
			Long threadReplyUserId = threadReply.getUser().getId();
			if (!threadReplyUserId.equals(currentUserId) && !notifiedUserIds.contains(threadReplyUserId)) {
				CommentNotification notification = notificationService.create(false, threadReply, newReply);
				notifiedUserIds.add(threadReplyUserId);
			}
		}
	}

	@Override
	@Transactional
	public Reply post(Reply tempReply) {
		//Értesítjük minden reply userét, hogy egy új reply érkezett a threadbe
		Reply reply = replyRepository.save(tempReply);
		notifyUsersAboutReplies(reply);
		threadService.findById(reply.getThread().getId()).addReply(reply);
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
	public Reply vote(Long userId, Long id, VoteType voteType) {
		Reply reply = findById(id);
		reply = changeVote(userId, reply, voteType);
		userService.addVoteNotification(reply.getUser(), new VoteNotification(reply, voteType));
		return update(reply);
	}

	private Reply changeVote(Long userId, Reply reply, VoteType voteType) {
		switch (voteType) {
			case UPVOTE:
				reply.upvote(userId);
				break;
			case DOWNVOTE:
				reply.downvote(userId);
				break;
		}

		return reply;
	}

	public Optional<VoteType> getUserVote(Long userId, Long replyId) {
		Reply reply = findById(replyId);
		if (reply.checkIfUserDownvoted(userId)) return Optional.of(VoteType.DOWNVOTE);
		if (reply.checkIfUserUpvoted(userId)) return Optional.of(VoteType.UPVOTE);
		return Optional.empty();
	}

	@Override
	@Transactional
	public void delete(Long id) {
		Reply reply = getReplyIfPresent(replyRepository.findById(id)); //dobjunk errort ha nem létezik
		Thread replyThread = threadService.findById(reply.getThread().getId()); //fúj bazdmeg
//		If the reply we're deleting happens to be the first reply of a thread, delete the entire thread
		if (replyThread.getReplies().get(0).getId().equals(reply.getId())) threadService.delete(replyThread.getId());
		else {
			replyRepository.deleteById(id);
		}
//		entityManager.remove(reply);
	}
}
