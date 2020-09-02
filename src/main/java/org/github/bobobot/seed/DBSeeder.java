package org.github.bobobot.seed;

import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.*;
import org.github.bobobot.services.*;
import org.github.bobobot.ui.views.misc.ImageReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;

@Profile("!test")
@Component
@ComponentScan("org.github.bobobot")
@Transactional
@Slf4j
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
		log.info("Database seed not found. Seeding...");
		seedUsers();
		seedBoards();
		seedThreads();
		seedReplies();
		seedNotifications();
		metaService.update("seeded", true);
		log.info("Seeding successfully finished.");
	}

	private void seedUsers() {
		userService.register(true, "admin", "admin@chan.com", "admin");
		userService.register(false, "user1", "user1@chan.com", "user1");
		userService.register(false, "user2", "user2@chan.com", "user2");
		userService.register(false, "kornel", "kornel@chan.com", "kornel");
	}

	private void seedBoards() {
		boardService.create("v", "video games");
		boardService.create("pol", "poland");
		boardService.create("b", "random");
		boardService.create("long", "longrend");
	}

	private void seedThreads() {
		threadService.create("First thread of /v/", boardService.findByShortName("v"), userService.findByUsername("admin"));		//1
		threadService.create("First thread of /pol/", boardService.findByShortName("pol"), userService.findByUsername("admin"));	//2
		threadService.create("First thread of /b/", boardService.findByShortName("b"), userService.findByUsername("admin"));		//3
		threadService.create("First thread of /long/", boardService.findByShortName("long"), userService.findByUsername("admin"));	//4
		threadService.create("Spring boot", boardService.findByShortName("long"), userService.findByUsername("user1"));			//5
		threadService.create("Terrorist attack", boardService.findByShortName("b"), userService.findByUsername("kornel"));			//6
		threadService.create("Vaadin", boardService.findByShortName("long"), userService.findByUsername("user1"));					//7
		threadService.create("Duck song", boardService.findByShortName("b"), userService.findByUsername("user1"));					//8
	}

	private void seedReplies() {
		replyService.post("Everything about video games.", 68, toImagePath("videogames.png"), threadService.findById(1L), userService.findByUsername("admin"));
		replyService.post("Everything about poland.", 8999, toImagePath("feelsbadman.png"), threadService.findById(2L), userService.findByUsername("admin"));
		replyService.post("Everything random.", 1336, toImagePath("hedgehog.png"), threadService.findById(3L), userService.findByUsername("admin"));
		replyService.post("Everything about longrend.", 419, toImagePath("longrend.png"), threadService.findById(4L), userService.findByUsername("admin"));
		replyService.post("wow, cool", 13, threadService.findById(3L), userService.findByUsername("user1"));
		replyService.post("yeah I love being random", -2, threadService.findById(3L), userService.findByUsername("user2"));
		replyService.post("*holds up spork*", -132, toImagePath("penguinofdoom.png"), threadService.findById(3L), userService.findByUsername("user1"));
		replyService.post("Spring is my favorite framework!", -1, toImagePath("spring.png"), threadService.findById(5L), userService.findByUsername("user2"));
		replyService.post("I prefer C#", 103, threadService.findById(5L), userService.findByUsername("user1"));
		replyService.post("Have you heard the news about the two corrupt police officers in San Francisco? Horrible.", 103, toImagePath("gloden_gate.jpg"), threadService.findById(6L), userService.findByUsername("kornel"));
		replyService.post(">imagine unironically using vaadin", 706, toImagePath("vaadin.png"), threadService.findById(7L), userService.findByUsername("user1"));
		replyService.post("A duck walked up to a lemonade stand.", 7776, toImagePath("ducksong.png"), threadService.findById(8L), userService.findByUsername("user1"));
	}

	private void seedNotifications() {}

	private String toImagePath(String imageName) {
		String imagePath = ImageReceiver.getFolderPath() + File.separator + imageName;
		File file = new File(imagePath);
		if (!file.exists()) throw new IllegalArgumentException(imagePath + " does not exist!");
		return imagePath;
	}
}
