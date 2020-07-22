package org.github.bobobot.services.impl;

import org.github.bobobot.dao.IUserDAO;
import org.github.bobobot.dao.impl.InMemoryUserDAO;
import org.github.bobobot.entities.CommentNotification;
import org.github.bobobot.entities.User;
import org.github.bobobot.services.INotificationService;
import org.github.bobobot.services.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.github.bobobot.services.impl.TestHelperUtils.*;

@DataJpaTest
@DirtiesContext
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

		CommentNotification notification = notificationService.findCommentNotificationByID(0);

		assertThat(originalNotification).isEqualTo(notification);
	}

	@Test
	void updateCommentNotification() {
		User user = createDummyUser();
		CommentNotification originalNotification = createDummyCommentNotification(user);

		em.persist(user);
		em.persist(originalNotification);
		notificationService.update(0, false, user, "teszt1");
		CommentNotification notification = notificationService.findCommentNotificationByID(0);

		assertThat(notification.getReplyContent()).isEqualTo("teszt1");

	}

	@Test
	void checkIfUserReceivedNotification() {
		User user = userService.register(createDummyUser());

		CommentNotification originalNotification = createDummyCommentNotification(user);

		em.persist(originalNotification);

		user = userService.findById(0);

		assertThat(user.getNotifications().size()).isEqualTo(1);
	}
}
