package org.github.bobobot.ui.views.layouts;

import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.access.PermissionHandler;
import org.github.bobobot.entities.CommentNotification;
import org.github.bobobot.entities.Notification;
import org.github.bobobot.entities.User;
import org.github.bobobot.entities.VoteNotification;
import org.github.bobobot.entities.VoteNotification.VoteType;
import org.github.bobobot.services.INotificationService;
import org.github.bobobot.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Transactional
public class NotificationLayout extends VerticalLayout implements View {

	@Autowired
	private IUserService userService;

	@Autowired
	private INotificationService notificationService;

	private final int NOTIFICATIONS_TO_SHOW = 6;

	@PostConstruct
	void init() {

		User currentUser = userService.findById(PermissionHandler.getCurrentUser().getId());
		//TODO: ha nem sorban vannak a notificationök akkor tedd sorba
		List<Notification> notifications = currentUser.getNotifications().stream()
				.sorted((n1, n2) -> n2.getId().compareTo(n1.getId()))
				.collect(Collectors.toList());

		for (int i = 0; i < NOTIFICATIONS_TO_SHOW; i++) {
			if (notifications.size() <= i) break;
			Notification notification = notifications.get(i);
			HorizontalLayout notifLayout = new HorizontalLayout();
			//TODO: reply és vote icon
			Image icon = new Image();


			//God please forgive me for I have sinned
			//Never thought I'd stoop so low but vaadin has forced me to
			if (notification instanceof CommentNotification) {
				icon = new Image(null, new ThemeResource("images/comment.png"));
				icon.addStyleName("comment-icon");
			} else if (notification instanceof VoteNotification) {
				VoteType voteType = ((VoteNotification) notification).getVoteType();
				if (voteType == VoteType.UPVOTE) {
					icon = new Image(null, new ThemeResource("images/upvote.png"));
					icon.addStyleName("upvote-icon");
				} else if (voteType == VoteType.DOWNVOTE) {
					icon = new Image(null, new ThemeResource("images/downvote.png"));
					icon.addStyleName("downvote-icon");
				}
			}
			Label notifLabel = new Label(notifications.get(i).getAsText());
			notifLabel.addStyleName("notification-label");

			if (!notification.isRead()) notifLayout.addStyleName("unread-notification");

			notifLayout.addLayoutClickListener(event -> {
				log.info("Notification clicked");
				notification.setRead(true);
				notifLayout.removeStyleName("unread-notification");
				if (notification instanceof CommentNotification)
					notificationService.update((CommentNotification) notification);
				else if (notification instanceof VoteNotification)
					notificationService.update((VoteNotification) notification);
			});

			notifLayout.addComponents(icon, notifLabel);
			notifLayout.setComponentAlignment(icon, Alignment.MIDDLE_CENTER); //hát persze hogy nem működik, miért is működne
			notifLayout.setComponentAlignment(notifLabel, Alignment.MIDDLE_CENTER); //DE AKKOR EZ MEG MIÉRT JÓ WTF

			addStyleName("notification");
			if (notification.isRead()) addStyleName("read");

			addComponent(notifLayout);

		}
	}
}
