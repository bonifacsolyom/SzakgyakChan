package org.github.bobobot.services.impl;

import org.github.bobobot.entities.Board;
import org.github.bobobot.services.IBoardService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.github.bobobot.services.impl.TestHelperUtils.createBoardService;
import static org.github.bobobot.services.impl.TestHelperUtils.createDummyBoard;

public class BoardServiceTest {

	@Test
	void createBoard() {
		IBoardService service = createBoardService();
		Board originalBoard = createDummyBoard();

		service.create(originalBoard);
		Board board = service.findById(0);

		assertThat(board).isEqualTo(originalBoard);
	}

	@Test
	void updateBoard() {
		IBoardService service = createBoardService();
		Board originalBoard = createDummyBoard();

		service.create(originalBoard);
		service.update(0, "t1", "teszt board 1");
		Board board = service.findById(0);

		assertThat(board.getShortName()).isEqualTo("t1");
	}


	@Test
	void updateBoardButDoesntExist() {
		IBoardService service = createBoardService();

		assertThatIllegalArgumentException().isThrownBy(() -> service.update(createDummyBoard()));
	}

	@Test
	void listAllBoards() {
		IBoardService service = createBoardService();
		service.create(createDummyBoard());
		service.create(createDummyBoard());

		assertThat(service.list().size()).isEqualTo(2);
	}
}
