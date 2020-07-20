package org.github.bobobot.services.impl;

import org.github.bobobot.dao.IThreadDAO;
import org.github.bobobot.entities.Board;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.User;
import org.github.bobobot.services.IThreadService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ThreadService implements IThreadService {

	private final IThreadDAO dao;

	public ThreadService(IThreadDAO dao) {
		this.dao = dao;
	}

	private Thread getThreadIfPresent(Optional<Thread> thread) {
		if (!thread.isPresent()) {
			throw new IllegalArgumentException("Thread was not found!");
		}
		return thread.get();
	}

	@Override
	public Thread create(Thread tempThread) {
		tempThread.getUser().addThread(tempThread);
		return dao.create(tempThread);
	}

	@Override
	public Thread create(String title, Board board, User user, List<Reply> replies) {
		return create(new Thread(-1, title, board, user, replies));
	}

	@Override
	public Thread create(String title, Board board, User user, Reply firstPost) {
		List<Reply> replies = new ArrayList<>();
		replies.add(firstPost);
		return create(new Thread(-1, title, board, user, replies));
	}

	@Override
	public Thread create(String title, Board board, User user) {
		return create(new Thread(-1, title, board, user));
	}

	@Override
	public Thread update(Thread tempThread) {
		Optional<Thread> thread = dao.update(tempThread);
		return getThreadIfPresent(thread);
	}

	@Override
	public Thread update(int ID, String title, Board board, User user) {
		return update(new Thread(ID, title, board, user));
	}

	@Override
	public Thread update(int ID, String title, Board board, User user, List<Reply> replies) {
		return update(new Thread(ID, title, board, user, replies));
	}

	@Override
	public List<Thread> list() {
		return dao.list();
	}

	@Override
	public Thread findById(int ID) {
		Optional<Thread> thread = dao.select(ID);
		return getThreadIfPresent(thread);
	}

	@Override
	public void delete(int ID) {
		Optional<Thread> thread = dao.delete(ID);
		getThreadIfPresent(thread); //throw error if not found
	}
}
