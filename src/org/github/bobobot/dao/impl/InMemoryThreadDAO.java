package org.github.bobobot.dao.impl;

import org.github.bobobot.dao.ThreadDAO;
import org.github.bobobot.entities.Board;
import org.github.bobobot.entities.Thread;

import java.util.ArrayList;
import java.util.Optional;

public class InMemoryThreadDAO implements ThreadDAO {
	ArrayList<Thread> memory = new ArrayList<>();

	@Override
	public Thread create(String title, Board board) {
		Thread thread = new Thread(memory.size(), title, board);
		memory.add(thread);
		return thread;
	}

	@Override
	public Optional<Thread> update(int ID, String title, Board board) {
		Optional<Thread> thread = memory.stream()
				.filter(t -> t.getID() == ID)
				.findFirst();

		if (thread.isPresent()) {
			thread.get().setTitle(title);
			thread.get().setBoard(board);
		}

		return thread;
	}

	@Override
	public Optional<Thread> select(int ID) {
		Optional<Thread> thread = memory.stream()
				.filter(t -> t.getID() == ID)
				.findFirst();
		return thread;
	}

	@Override
	public ArrayList<Thread> list() {
		return memory;
	}

	@Override
	public Optional<Thread> delete(int ID) {
		Optional<Thread> thread = memory.stream()
				.filter(b -> b.getID() == ID)
				.findFirst();
		if (thread.isPresent()) {
			memory.remove(thread.get());
		}

		return thread;
	}
}
