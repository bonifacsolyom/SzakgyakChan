package org.github.bobobot.dao.impl;

import org.github.bobobot.dao.BoardDAO;
import org.github.bobobot.entities.Board;

import java.util.ArrayList;
import java.util.Optional;

public class InMemoryBoardDAO implements BoardDAO {
	ArrayList<Board> memory = new ArrayList<>();

	@Override
	public Board create(String shortName, String longName) {
		Board board = new Board(memory.size(), shortName, longName);
		memory.add(board);
		return board;
	}

	@Override
	public Optional<Board> update(int ID, String shortName, String longName) {
		Optional<Board> board = memory.stream()
				.filter(b -> b.getID() == ID)
				.findFirst();

		if (board.isPresent()) {
			board.get().setShortName(shortName);
			board.get().setLongName(longName);
		}

		return board;
	}

	@Override
	public Optional<Board> select(int ID) {
		Optional<Board> board = memory.stream()
				.filter(b -> b.getID() == ID)
				.findFirst();
		return board;
	}

	@Override
	public ArrayList<Board> list() {
		return memory;
	}

	@Override
	public Optional<Board> delete(int ID) {
		Optional<Board> board = memory.stream()
				.filter(b -> b.getID() == ID)
				.findFirst();
		if (board.isPresent()) {
			memory.remove(board.get());
		}

		return board;
	}
}
