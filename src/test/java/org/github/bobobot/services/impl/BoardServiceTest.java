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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.github.bobobot.services.impl.TestHelperUtils.createDummyBoard;

@ActiveProfiles("test")
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ComponentScan("org.github.bobobot")
//TODO: Ha nem csak a jpa része kell a "test slice"-nak akkor lehet @SprinBootTest-et használni.

//Próbáltam, de ugye akkor nem injektel be entitymanagert, és a többi tesztben anélkül halál megoldani bármit is
//pl. a replytest-ben akkor ha létre akarok hozni egy reply-t, akkor minden egyes tesztben létre kéne hozni hozzá egy boardot,
//egy threadet, és egy usert, különben transient exceptionöket dobál
public class BoardServiceTest {

	@Autowired
	private TestEntityManager em;

	@Autowired
	private IBoardService service;

	@Test
	void createBoard() {
		Board originalBoard = createDummyBoard();

		em.persist(originalBoard);
		Board board = service.findById(1L);


		assertThat(board.getShortName()).isEqualTo(originalBoard.getShortName());
	}

	@Test
	void updateBoard() {
		Board originalBoard = createDummyBoard();

		em.persist(originalBoard);
		service.update(1L, "t1", "teszt board 1");
		Board board = service.findById(1L);

		assertThat(board.getShortName()).isEqualTo("t1");
	}


	@Test
	void updateBoardButDoesntExist() {
		Board board = createDummyBoard();
		board.setId(1L);

		assertThatIllegalArgumentException().isThrownBy(() -> service.update(board));
	}

	@Test
	void listAllBoards() {
		service.create(createDummyBoard());
		service.create(createDummyBoard());

		assertThat(service.list().size()).isEqualTo(2);
	}
}
