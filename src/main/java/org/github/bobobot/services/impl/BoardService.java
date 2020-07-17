package org.github.bobobot.services.impl;

import org.github.bobobot.dao.IBoardDAO;
import org.github.bobobot.entities.Board;
import org.github.bobobot.services.IBoardService;

import java.util.List;
import java.util.Optional;

public class BoardService implements IBoardService {

	private IBoardDAO dao;

	public BoardService(IBoardDAO dao) {
		this.dao = dao;
	}

	@Override
	public Board create(Board board) {
		return dao.create(board);
	}

	@Override
	public Board create(String shortName, String longName) {
		return create(new Board(-1, shortName, longName));
	}

	@Override
	public Board update(Board tempBoard) {
		Optional<Board> board = dao.update(tempBoard);
		if (!board.isPresent()) { throw new IllegalArgumentException("Board was not found!"); }
		return board.get();
	}

	@Override
	public Board update(int ID, String shortName, String longName) {
		return update(new Board(ID, shortName, longName));
	}

	@Override
	public List<Board> list() {
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
