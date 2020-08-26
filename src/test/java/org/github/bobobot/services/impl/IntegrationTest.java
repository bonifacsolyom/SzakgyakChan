package org.github.bobobot.services.impl;

import org.github.bobobot.entities.Board;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.User;
import org.github.bobobot.services.IBoardService;
import org.github.bobobot.services.IReplyService;
import org.github.bobobot.services.IThreadService;
import org.github.bobobot.services.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author aklemanovits
 */
@SpringBootTest
@ActiveProfiles("test")
public class IntegrationTest {

	@Autowired
	private IReplyService replyService;

	@Autowired
	private IThreadService threadService;

	@Autowired
	private IBoardService boardService;

	@Autowired
	private IUserService userService;

	@Test
	void name() {
		//GIVEN
		User user = new User();
		user.setEmail("test@test.hu");
		user.setPasswordHash("password");
		User register = userService.register(user);

		Board board = new Board();

		Thread thread = new Thread();
		thread.setBoard(board);
		thread.setUser(register);

//		Thread savedThread = threadService.create(thread);

		Reply reply = new Reply();
		reply.setThread(thread);

//		Reply reply2 = new Reply();
//		reply2.setThread(thread);


//		Reply savedReply = replyService.post(reply);

		thread.addReply(reply);

		board.addThread(thread);
		Board savedBoard = boardService.create(board);

		//WHEN
		replyService.delete(1L);

		//THEN
		System.err.println(replyService.list());
		System.err.println(threadService.list());
		System.err.println(boardService.list());
	}
}
