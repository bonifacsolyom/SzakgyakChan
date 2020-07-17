package org.github.bobobot.services.impl;

import org.github.bobobot.dao.impl.InMemoryCommentNotificationDAO;
import org.github.bobobot.dao.impl.InMemoryThreadDAO;
import org.github.bobobot.dao.impl.InMemoryVoteNotificationDAO;
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
		INotificationService service = createNotificationService();
		CommentNotification originalNotification = createDummyCommentNotification();

		service.create(originalNotification);

		CommentNotification notification = service.findCommentNotificationByID(0);

		assertThat(originalNotification).isEqualTo(notification);
	}

	@Test
	void updateCommentNotification() {
		INotificationService service = createNotificationService();
		User user = createDummyUser();
		CommentNotification originalNotification = createDummyCommentNotification(user);

		service.create(originalNotification);
		service.update(0, false, user, "teszt1");
		CommentNotification notification = service.findCommentNotificationByID(0);

		assertThat(notification.getReplyContent()).isEqualTo("teszt1");

	}

	@Test
	void checkIfUserReceivedNotification() {
		INotificationService notificationService = createNotificationService();
		IUserService userService = createUserService();
		User user = userService.register(createDummyUser());

		CommentNotification originalNotification = createDummyCommentNotification(user);

		notificationService.create(originalNotification);

		user = userService.findById(0);

		assertThat(user.getNotifications().size()).isEqualTo(1);
	}

}
