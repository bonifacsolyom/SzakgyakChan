package org.github.bobobot.services.impl;

import org.github.bobobot.config.ApplicationConfig;
import org.github.bobobot.entities.CommentNotification;
import org.github.bobobot.entities.User;
import org.github.bobobot.services.INotificationService;
import org.github.bobobot.services.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.github.bobobot.services.impl.TestHelperUtils.createDummyCommentNotification;
import static org.github.bobobot.services.impl.TestHelperUtils.createDummyUser;

@ActiveProfiles("test")
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ContextConfiguration(classes = ApplicationConfig.class)
public class NotificationServiceTest {

	@Autowired
	private TestEntityManager em;

	@Autowired
	private INotificationService notificationService;
	@Autowired
	private IUserService userService;

	@Test
	void createCommentNotification() {
		User user = createDummyUser();
		CommentNotification originalNotification = createDummyCommentNotification(user);

		em.persist(user);
		em.persist(originalNotification);

		CommentNotification notification = notificationService.findCommentNotificationByID(1L);

		assertThat(originalNotification.getReplyContent()).isEqualTo(notification.getReplyContent());
	}

	@Test
	void updateCommentNotification() {
		User user = createDummyUser();
		CommentNotification originalNotification = createDummyCommentNotification(user);

		em.persist(user);
		em.persist(originalNotification);
		notificationService.update(1L, false, user, "teszt1");
		CommentNotification notification = notificationService.findCommentNotificationByID(1L);

		assertThat(notification.getReplyContent()).isEqualTo("teszt1");

	}

	@Test
	void checkIfUserReceivedNotification() {
		User user = userService.register(createDummyUser());
		CommentNotification originalNotification = createDummyCommentNotification(user);

		em.persist(user);
		em.persist(originalNotification);
		notificationService.create(originalNotification);

		user = userService.findById(1L);

		assertThat(user.getNotifications().size()).isEqualTo(1);
	}
}
