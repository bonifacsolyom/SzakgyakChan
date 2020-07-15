package org.github.bobobot.services.impl;

import org.github.bobobot.dao.IThreadDAO;
import org.github.bobobot.dao.impl.InMemoryThreadDAO;
import org.github.bobobot.entities.Board;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.User;
import org.github.bobobot.services.IThreadService;

import java.util.ArrayList;
import java.util.Optional;

public class ThreadService implements IThreadService {

	IThreadDAO dao = new InMemoryThreadDAO();

	@Override
	public Thread create(String title, Board board, ArrayList<Reply> replies, User user) {
		Thread thread = new Thread(-1, title, board, replies, user);
		return dao.create(thread);
	}

	@Override
	public Thread update(int ID, String title, Board board, ArrayList<Reply> replies, User user) {
		Optional<Thread> thread = dao.update(new Thread(ID, title, board, replies, user));
		if (!thread.isPresent()) { throw new IllegalArgumentException("Thread was not found!"); }
		return thread.get();
	}

	@Override
	public ArrayList<Thread> list() {
		return dao.list();
	}

	@Override
	public Thread findById(int ID) {
		Optional<Thread> thread = dao.select(ID);
		if (!thread.isPresent()) { throw new IllegalArgumentException("Thread was not found!"); }
		return thread.get();
	}

	@Override
	public void delete(int ID) {
		Optional<Thread> thread = dao.delete(ID);
		if (!thread.isPresent()) { throw new IllegalArgumentException("Thread was not found!"); }
	}
}
