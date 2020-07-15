package org.github.bobobot.services.impl;

import org.github.bobobot.entities.Board;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ThreadServiceTest {


	@Test
	void postThread() {
		ThreadService service = new ThreadService();
		Board board = new Board(0, "t", "teszt board");

		User user = new User(0, true, "tesztNev", "tesztEmail", "tesztJelszo");
		Thread originalThread = new Thread(0, "tesztTitle", board, user);
		service.create("tesztTitle", board, user);
		Thread thread = service.findById(0);

		assertEquals(originalThread, thread);
	}

	@Test
	void checkIfUserStoredTheThreadAfterPost() {
		
	}

	@Test
	void checkIfThreadStoredTheUserAfterPost() {

	}
}