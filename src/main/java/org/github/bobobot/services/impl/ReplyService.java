package org.github.bobobot.services.impl;

import org.github.bobobot.dao.ICommentNotificationDAO;
import org.github.bobobot.dao.IReplyDAO;
import org.github.bobobot.dao.IVoteNotificationDAO;
import org.github.bobobot.entities.*;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.VoteNotification.VoteType;
import org.github.bobobot.services.IReplyService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ReplyService implements IReplyService {

	private final IReplyDAO replyDAO;
	private final ICommentNotificationDAO commentDAO;
	private final IVoteNotificationDAO voteDAO;

	public ReplyService(IReplyDAO replyDAO, ICommentNotificationDAO commentDAO, IVoteNotificationDAO voteDAO) {
		this.replyDAO = replyDAO;
		this.commentDAO = commentDAO;
		this.voteDAO = voteDAO;
	}

	@Override
	public Reply post(Reply tempReply) {
		//Értesítjük minden reply userét, hogy egy új reply érkezett a threadbe
		notifyReplies(tempReply);
		tempReply.getThread().addReply(tempReply);
		return replyDAO.create(tempReply);
	}

	private void notifyReplies(Reply reply) {
		for (Reply r : reply.getThread().getReplies()) {
			CommentNotification notification = commentDAO.create(new CommentNotification(-1, reply.getContent(), false));
			r.getUser().addCommentNotification(notification);
		}

	}

	@Override
	public Reply post(String content, int votes, Image image, Thread thread, User user) {
		return post(new Reply(-1, content, LocalDateTime.now(), votes, image, thread, user));
	}

	@Override
	public Reply update(Reply tempReply) {
		Optional<Reply> reply = replyDAO.update(tempReply);
		if (!reply.isPresent()) { throw new IllegalArgumentException("Reply was not found!"); }
		return reply.get();
	}

	@Override
	public Reply update(int ID, String content, int votes, Image image, Thread thread, User user) {
		return update(new Reply(ID, content, LocalDateTime.now(), votes, image, thread, user));
	}

	@Override
	public List<Reply> list() {
		return replyDAO.list();
	}

	@Override
	public Reply findById(int ID) {
		Optional<Reply> reply = replyDAO.select(ID);
		if (!reply.isPresent()) { throw new IllegalArgumentException("Reply was not found!"); }
		return reply.get();
	}

	@Override
	public List<Reply> listByThread(Thread thread) {
		return replyDAO.selectByThread(thread);
	}

	@Override
	public Reply vote(int ID, VoteType voteType) {
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
	public void delete(int ID) {
		Optional<Reply> reply = replyDAO.delete(ID);
		if (!reply.isPresent()) { throw new IllegalArgumentException("Reply was not found!"); }
	}
}
