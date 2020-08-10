package org.github.bobobot.seed;

import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.*;
import org.github.bobobot.services.*;
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
	private IUserService userService;

	@Autowired
	private IBoardService boardService;

	@Autowired
	private IThreadService threadService;

	@Autowired
	private IReplyService replyService;

	@Autowired
	private INotificationService notificationService;

	@Autowired
	private IMetaService metaService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		MetaInfo seeded = metaService.getOrDefault("seeded", false);
		if (seeded.getValueAsBoolean()) return;
		seedUsers();
		seedBoards();
		seedThreads();
		seedReplies();
		seedNotifications();
		metaService.update("seeded", true);
	}

	private void seedUsers() {
		userService.register(true, "admin", "admin@chan.com", "admin");
		userService.register(false, "user1", "user1@chan.com", "user1");
		userService.register(false, "user2", "user2@chan.com", "user2");
	}

	private void seedBoards() {
		boardService.create("b", "random");
		boardService.create("l", "longrend");
		boardService.create("i", "Imagine Dragons");
	}

	private void seedThreads() {
		List<Board> boardList = boardService.list();
		List<User> userList = userService.list();

		threadService.create("First thread of random", boardList.get(0), userList.get(0));
		threadService.create("First thread of longrend", boardList.get(1), userList.get(0));
		threadService.create("First thread of Imagine Dragons", boardList.get(2), userList.get(0));
		threadService.create("Spring boot", boardList.get(0), userList.get(1));
	}

	private void seedReplies() {
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

	private void seedNotifications() {
		List<Reply> replyList = replyService.list();

		notificationService.create(true, replyList.get(0), VoteNotification.VoteType.UPVOTE);
	}
}
