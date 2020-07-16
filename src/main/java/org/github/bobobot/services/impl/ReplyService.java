package org.github.bobobot.services.impl;

import org.github.bobobot.dao.IReplyDAO;
import org.github.bobobot.entities.*;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.VoteNotification.VoteType;
import org.github.bobobot.services.IReplyService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class ReplyService implements IReplyService {

	private IReplyDAO dao;

	public ReplyService(IReplyDAO dao) {
		this.dao = dao;
	}

	@Override
	public Reply post(Reply tempReply) {
		//Értesítjük minden reply userét, hogy egy új reply érkezett a threadbe
		for (Reply r : tempReply.getThread().getReplies()) {
			//TODO
		}
		tempReply.getThread().addReply(tempReply);
		return dao.create(tempReply);
	}

	@Override
	public Reply post(String content, LocalDateTime date, int votes, Image image, Thread thread, User user) {
		return post(new Reply(-1, content, date, votes, image, thread, user));
	}

	@Override
	public Reply update(Reply tempReply) {
		Optional<Reply> reply = dao.update(tempReply);
		if (!reply.isPresent()) { throw new IllegalArgumentException("Reply was not found!"); }
		return reply.get();
	}

	@Override
	public Reply update(int ID, String content, LocalDateTime date, int votes, Image image, Thread thread, User user) {
		return update(new Reply(ID, content, date, votes, image, thread, user));
	}

	@Override
	public ArrayList<Reply> list() {
		return dao.list();
	}

	@Override
	public Reply findById(int ID) {
		Optional<Reply> reply = dao.select(ID);
		if (!reply.isPresent()) { throw new IllegalArgumentException("Reply was not found!"); }
		return reply.get();
	}

	@Override
	public ArrayList<Reply> listByThread(Thread thread) {
		return dao.selectByThread(thread);
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
		Optional<Reply> reply = dao.delete(ID);
		if (!reply.isPresent()) { throw new IllegalArgumentException("Reply was not found!"); }
	}
}
