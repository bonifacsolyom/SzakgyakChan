package org.github.bobobot.dao.impl;

import org.github.bobobot.dao.IBoardDAO;
import org.github.bobobot.entities.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryBoardDAO implements IBoardDAO {
	List<Board> memory = new ArrayList<>();

	@Override
	public Board create(Board board) {
		board.setID(memory.size());
		memory.add(board);
		return board;
	}

	@Override
	public Optional<Board> update(Board board) {
		Optional<Board> memoryBoard = memory.stream()
				.filter(b -> b.getID() == board.getID())
				.findFirst();

		if (memoryBoard.isPresent()) {
			//TODO: nincs erre szebb megoldás?
			memoryBoard.get().setShortName(board.getShortName());
			memoryBoard.get().setLongName(board.getLongName());
		}

		return memoryBoard;
	}

	@Override
	public Optional<Board> selectByID(int ID) {
		Optional<Board> board = memory.stream()
				.filter(b -> b.getID() == ID)
				.findFirst();
		return board;
	}

	@Override
	public Optional<Board> selectByShortName(String name) {
		Optional<Board> board = memory.stream()
				.filter(b -> b.getShortName().equals(name))
				.findFirst();
		return board;
	}

	@Override
	public List<Board> list() {
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
