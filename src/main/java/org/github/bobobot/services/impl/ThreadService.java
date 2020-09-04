package org.github.bobobot.services.impl;

import org.github.bobobot.entities.Board;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.User;
import org.github.bobobot.repositories.IReplyRepository;
import org.github.bobobot.repositories.IThreadRepository;
import org.github.bobobot.services.IBoardService;
import org.github.bobobot.services.IReplyService;
import org.github.bobobot.services.IThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ThreadService implements IThreadService {

	@Autowired
	private IThreadRepository repository;

	@Autowired
	private IBoardService boardService;

	@Autowired
	private IReplyService replyService;

	@Autowired
	private IReplyRepository replyRepository;

	private Thread getThreadIfPresent(Optional<Thread> thread) {
		if (!thread.isPresent()) {
			throw new IllegalArgumentException("Thread was not found!");
		}
		return thread.get();
	}

	@Override
	public Thread create(Thread tempThread) {
		tempThread.getUser().addThread(tempThread);
		boardService.findById(tempThread.getBoard().getId()).addThread(tempThread);
		return repository.save(tempThread);
	}

	@Override
	public Thread create(String title, Board board, User user, Reply firstPost) {
		List<Reply> replies = new ArrayList<>();
		replies.add(firstPost);
		return create(new Thread(title, board, user, replies));
	}

	@Override
	public Thread update(Thread tempThread) {
		getThreadIfPresent(repository.findById(tempThread.getId())); //throw an error if it doesn't exist
		return repository.save(tempThread);
	}

	@Override
	public List<Thread> list() {
		return repository.findAll();
	}

	@Override
	@Transactional
	public Thread findById(Long id) {
		Optional<Thread> thread = repository.findById(id);
		return getThreadIfPresent(thread);
	}

	@Override
	public void delete(Long id) {
		Thread thread = getThreadIfPresent(repository.findById(id)); //throw an error if it doesn't exist
		thread.getReplies().clear();
		repository.deleteById(id); //Finally we delete the thread
	}
}
