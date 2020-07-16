package org.github.bobobot.services.impl;

import org.github.bobobot.dao.IBoardDAO;
import org.github.bobobot.dao.impl.InMemoryBoardDAO;
import org.github.bobobot.entities.Board;
import org.github.bobobot.services.IBoardService;

import java.util.ArrayList;
import java.util.Optional;

public class BoardService implements IBoardService {

	private IBoardDAO dao;

	public BoardService(IBoardDAO dao) {
		this.dao = dao;
	}

	@Override
	public Board create(String shortName, String longName) {
		Board board = new Board(-1, shortName, longName);
		return dao.create(board);
	}

	@Override
	public Board update(int ID, String shortName, String longName) {
		Optional<Board> board = dao.update(new Board(ID, shortName, longName));
		if (!board.isPresent()) { throw new IllegalArgumentException("Board was not found!"); }
		return board.get();
	}

	@Override
	public ArrayList<Board> list() {
		return dao.list();
	}

	@Override
	public Board findById(int ID) {
		Optional<Board> board = dao.selectByID(ID);
		if (!board.isPresent()) { throw new IllegalArgumentException("Board was not found!"); }
		return board.get();
	}

	@Override
	public Board findByShortName(String shortName) {
		Optional<Board> board = dao.selectByShortName(shortName);
		if (!board.isPresent()) { throw new IllegalArgumentException("Board was not found!"); }
		return board.get();
	}

	@Override
	public void delete(int ID) {
		Optional<Board> board = dao.delete(ID);
		if (!board.isPresent()) { throw new IllegalArgumentException("Board was not found!"); }
	}
}
