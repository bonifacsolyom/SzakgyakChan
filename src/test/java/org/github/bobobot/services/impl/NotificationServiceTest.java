package org.github.bobobot.services.impl;

import org.github.bobobot.dao.IUserDAO;
import org.github.bobobot.dao.impl.InMemoryUserDAO;
import org.github.bobobot.entities.CommentNotification;
import org.github.bobobot.entities.User;
import org.github.bobobot.services.INotificationService;
import org.github.bobobot.services.IUserService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.github.bobobot.services.impl.TestHelperUtils.*;

public class NotificationServiceTest {

	@Test
	void createCommentNotification() {
		IUserDAO userDAO = new InMemoryUserDAO();
		INotificationService notificationService = createNotificationService(userDAO);
		IUserService userService = createUserService(userDAO);
		User user = createDummyUser();
		CommentNotification originalNotification = createDummyCommentNotification(user);

		userService.register(user);
		notificationService.create(originalNotification);

		CommentNotification notification = notificationService.findCommentNotificationByID(0);

		assertThat(originalNotification).isEqualTo(notification);
	}

	@Test
	void updateCommentNotification() {
		IUserDAO userDAO = new InMemoryUserDAO();
		INotificationService notificationService = createNotificationService(userDAO);
		IUserService userService = createUserService(userDAO);
		User user = createDummyUser();
		CommentNotification originalNotification = createDummyCommentNotification(user);

		userService.register(user);
		notificationService.create(originalNotification);
		notificationService.update(0, false, user, "teszt1");
		CommentNotification notification = notificationService.findCommentNotificationByID(0);

		assertThat(notification.getReplyContent()).isEqualTo("teszt1");

	}

	@Test
	void checkIfUserReceivedNotification() {
		IUserDAO userDAO = new InMemoryUserDAO();
		INotificationService notificationService = createNotificationService(userDAO);
		IUserService userService = createUserService(userDAO);
		User user = userService.register(createDummyUser());

		CommentNotification originalNotification = createDummyCommentNotification(user);

		notificationService.create(originalNotification);

		user = userService.findById(0);

		assertThat(user.getNotifications().size()).isEqualTo(1);
	}

}
