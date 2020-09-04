package org.github.bobobot.services.impl;

import org.github.bobobot.entities.CommentNotification;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.User;
import org.github.bobobot.services.INotificationService;
import org.github.bobobot.services.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.github.bobobot.services.impl.TestHelperUtils.*;

@ActiveProfiles("test")
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ComponentScan("org.github.bobobot")
public class NotificationServiceTest {

	@Autowired
	private TestEntityManager em;

	@Autowired
	private INotificationService notificationService;
	@Autowired
	private IUserService userService;

	@Test
	void createCommentNotification() {
		Reply reply = createDummyReply();
		CommentNotification originalNotification = createDummyCommentNotification(reply);

		em.persist(reply);
		em.persist(originalNotification);

		CommentNotification notification = notificationService.findCommentNotificationByID(1L);

		assertThat(originalNotification.getOtherUsersReply().getContent()).isEqualTo(notification.getOtherUsersReply().getContent());
	}

	@Test
	void updateCommentNotification() {
		Thread thread = createDummyThread();
		thread = em.persist(thread);
		Reply reply = createDummyReply(thread);
		CommentNotification originalNotification = createDummyCommentNotification(reply);

		em.persist(reply);
		em.persist(originalNotification);
		Reply newReply = originalNotification.getOtherUsersReply();
		newReply.setContent("teszt1");
		newReply.setThread(thread);
		notificationService.update(1L, false, originalNotification.getOriginalReply(), newReply);
		CommentNotification notification = notificationService.findCommentNotificationByID(1L);

		assertThat(notification.getOtherUsersReply().getContent()).isEqualTo("teszt1");

	}

	@Test
	void checkIfUserReceivedNotification() {
		Thread thread = createDummyThread();
		thread = em.persist(thread);
		User user = userService.register(createDummyUser());
		em.persist(user);
		Reply reply = createDummyReply(thread, user);
		em.persist(reply);
		CommentNotification originalNotification = createDummyCommentNotification(reply);
		em.persist(originalNotification);
		notificationService.create(originalNotification);


		user = userService.findById(1L);

		assertThat(user.getNotifications().size()).isEqualTo(1);
	}
}
