package org.github.bobobot.services.impl;

import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.*;
import org.github.bobobot.entities.VoteNotification.VoteType;
import org.github.bobobot.repositories.INotificationRepository;
import org.github.bobobot.repositories.IReplyRepository;
import org.github.bobobot.services.IReplyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ReplyService implements IReplyService {

	@Autowired
	private IReplyRepository replyRepository;
	@Autowired
	private INotificationRepository<CommentNotification> commentRepository;


	private Reply getReplyIfPresent(Optional<Reply> reply) {
		if (!reply.isPresent()) {
			throw new IllegalArgumentException("Reply was not found!");
		}
		return reply.get();
	}

	private void notifyUsersAboutReplies(Reply reply) {
		for (Reply r : reply.getThread().getReplies()) {
			CommentNotification notification = commentRepository.save(new CommentNotification(false, r.getUser(), reply.getContent()));
			r.getUser().addCommentNotification(notification);
		}

	}

	@Override
	public Reply post(Reply tempReply) {
		//Értesítjük minden reply userét, hogy egy új reply érkezett a threadbe
		notifyUsersAboutReplies(tempReply);
		tempReply.getThread().addReply(tempReply);
//		imageRepository.save(tempReply.getImage());
		return replyRepository.save(tempReply);
	}

	@Override
	public Reply post(String content, int votes, Thread thread, User user) {
		return post(new Reply(content, LocalDateTime.now(), votes, thread, user, null));
	}

	@Override
	public Reply post(String content, int votes, String image, Thread thread, User user) {
		return post(new Reply(content, LocalDateTime.now(), votes, thread, user, image));
	}

	@Override
	public Reply update(Reply tempReply) {
		getReplyIfPresent(replyRepository.findById(tempReply.getID())); //dobjunk errort ha nem létezik
		return replyRepository.save(tempReply);
	}

	@Override
	public Reply update(Long ID, String content, int votes, Thread thread, User user) {
		return update(new Reply(ID, content, LocalDateTime.now(), votes, thread, user, null));
	}

	@Override
	public Reply update(Long ID, String content, int votes, String image, Thread thread, User user) {
		return update(new Reply(ID, content, LocalDateTime.now(), votes, thread, user, image));
	}

	@Override
	public List<Reply> list() {
		return replyRepository.findAll();
	}

	@Override
	public Reply findById(Long ID) {
		Optional<Reply> reply = replyRepository.findById(ID);
		return getReplyIfPresent(reply);
	}

	@Override
	public List<Reply> listByThread(Thread thread) {
		return replyRepository.findAllByThread(thread);
	}

	@Override
	public Reply vote(Long ID, VoteType voteType) {
		Reply reply = findById(ID);
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
	public void delete(Long ID) {
		getReplyIfPresent(replyRepository.findById(ID)); //dobjunk errort ha nem létezik
		replyRepository.deleteById(ID);
	}
}
