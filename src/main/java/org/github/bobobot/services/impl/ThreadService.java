package org.github.bobobot.services.impl;

import org.github.bobobot.entities.Board;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.User;
import org.github.bobobot.repositories.IThreadRepository;
import org.github.bobobot.services.IThreadService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ThreadService implements IThreadService {

	@Autowired
	private IThreadRepository repository;

	private Thread getThreadIfPresent(Optional<Thread> thread) {
		if (!thread.isPresent()) {
			throw new IllegalArgumentException("Thread was not found!");
		}
		return thread.get();
	}

	@Override
	public Thread create(Thread tempThread) {
		tempThread.getUser().addThread(tempThread);
		tempThread.getBoard().addThread(tempThread);
		return repository.save(tempThread);
	}

	@Override
	public Thread create(String title, Board board, User user, List<Reply> replies) {
		return create(new Thread(title, board, user, replies));
	}

	@Override
	public Thread create(String title, Board board, User user, Reply firstPost) {
		List<Reply> replies = new ArrayList<>();
		replies.add(firstPost);
		return create(new Thread(title, board, user, replies));
	}

	@Override
	public Thread create(String title, Board board, User user) {
		return create(new Thread(title, board, user));
	}

	@Override
	public Thread update(Thread tempThread) {
		getThreadIfPresent(repository.findById(tempThread.getId())); //dobjunk errort ha nem létezik
		return repository.save(tempThread);
	}

	@Override
	public Thread update(Long id, String title, Board board, User user) {
		return update(new Thread(id, title, board, user));
	}

	@Override
	public Thread update(Long id, String title, Board board, User user, List<Reply> replies) {
		return update(new Thread(id, title, board, user, replies));
	}

	@Override
	public List<Thread> list() {
		return repository.findAll();
	}

	@Override
	public Thread findById(Long id) {
		Optional<Thread> thread = repository.findById(id);
		return getThreadIfPresent(thread);
	}

	@Override
	public void delete(Long id) {
		getThreadIfPresent(repository.findById(id)); //dobjunk errort ha nem létezik
		repository.deleteById(id);
	}
}
