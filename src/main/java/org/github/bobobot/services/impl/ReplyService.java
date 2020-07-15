package org.github.bobobot.services.impl;

import org.github.bobobot.dao.IReplyDAO;
import org.github.bobobot.dao.IUserDAO;
import org.github.bobobot.dao.impl.InMemoryReplyDAO;
import org.github.bobobot.dao.impl.InMemoryUserDAO;
import org.github.bobobot.entities.*;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.services.IReplyService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class ReplyService implements IReplyService {

	IReplyDAO dao = new InMemoryReplyDAO();

	@Override
	public Reply create(String content, LocalDateTime date, int votes, Image image, Thread thread) {
		Reply reply = new Reply(-1, content, date, votes, image, thread);
		return dao.create(reply);
	}

	@Override
	public Reply update(int ID, String content, LocalDateTime date, int votes, Image image, Thread thread) {
		Optional<Reply> reply = dao.update(new Reply(ID, content, date, votes, image, thread));
		if (!reply.isPresent()) { throw new IllegalArgumentException("Reply was not found!"); }
		return reply.get();
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
	public void delete(int ID) {
		Optional<Reply> reply = dao.delete(ID);
		if (!reply.isPresent()) { throw new IllegalArgumentException("Reply was not found!"); }
	}
}
