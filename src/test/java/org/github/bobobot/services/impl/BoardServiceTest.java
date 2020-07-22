package org.github.bobobot.services.impl;

import org.github.bobobot.config.ApplicationConfig;
import org.github.bobobot.entities.Board;
import org.github.bobobot.services.IBoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.github.bobobot.services.impl.TestHelperUtils.createDummyBoard;

@DataJpaTest
@DirtiesContext
@ComponentScan("org.github.bobobot")
@ContextConfiguration(classes = ApplicationConfig.class)
public class BoardServiceTest {

	@Autowired
	private TestEntityManager em;

	@Autowired
	private IBoardService service;

	@Test
	void createBoard() {
		Board originalBoard = new Board("b", "random");

		em.persist(originalBoard);
		Board board = service.findById(0);

		assertThat(board).isEqualTo(originalBoard);
	}

	@Test
	void updateBoard() {
		Board originalBoard = createDummyBoard();

		em.persist(originalBoard);
		service.update(0, "t1", "teszt board 1");
		Board board = service.findById(0);

		assertThat(board.getShortName()).isEqualTo("t1");
	}


	@Test
	void updateBoardButDoesntExist() {

		assertThatIllegalArgumentException().isThrownBy(() -> service.update(createDummyBoard()));
	}

	@Test
	void listAllBoards() {
		service.create(createDummyBoard());
		service.create(createDummyBoard());

		assertThat(service.list().size()).isEqualTo(2);
	}
}
