package org.github.bobobot.seed;

import org.github.bobobot.entities.Board;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.User;
import org.github.bobobot.services.IBoardService;
import org.github.bobobot.services.IReplyService;
import org.github.bobobot.services.IThreadService;
import org.github.bobobot.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Profile("!test")
@Component
@ComponentScan("org.github.bobobot")
@Transactional
public class DBSeeder implements ApplicationRunner {

	@Autowired
	IUserService userService;

	@Autowired
	IBoardService boardService;

	@Autowired
	IThreadService threadService;

	@Autowired
	IReplyService replyService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		seedUsers();
		seedBoards();
		seedThreads();
		seedReplies();
	}

	public void seedUsers() {
		userService.register(true, "admin", "admin@chan.com", "admin");
		userService.register(false, "user1", "user1@chan.com", "user1");
		userService.register(false, "user2", "user2@chan.com", "user2");
	}

	public void seedBoards() {
		boardService.create("b", "random");
		boardService.create("l", "longrend");
		boardService.create("i", "Imagine Dragons");
	}

	public void seedThreads() {
		List<Board> boardList = boardService.list();
		List<User> userList = userService.list();

		threadService.create("First thread of random", boardList.get(0), userList.get(0));
		threadService.create("First thread of longrend", boardList.get(1), userList.get(0));
		threadService.create("First thread of Imagine Dragons", boardList.get(2), userList.get(0));
		threadService.create("Spring boot", boardList.get(0), userList.get(1));
	}

	public void seedReplies() {
		List<Thread> threadList = threadService.list();
		List<User> userList = userService.list();

		replyService.post("Everything random.", 68, threadList.get(0), userList.get(0));
		replyService.post("Everything about longrend.", 419, threadList.get(1), userList.get(0));
		replyService.post("Everything about Imagine Dragons.", 1336, threadList.get(2), userList.get(0));
		replyService.post("wow, cool", 13, threadList.get(0), userList.get(1));
		replyService.post("yeah I love being random", -2, threadList.get(0), userList.get(2));
		replyService.post("Spring is my favorite framework!", -1, threadList.get(3), userList.get(2));
		replyService.post("I prefer C#", 103, threadList.get(3), userList.get(1));
	}
}
