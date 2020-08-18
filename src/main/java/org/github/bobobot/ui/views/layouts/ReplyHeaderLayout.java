package org.github.bobobot.ui.views.layouts;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.access.PermissionHandler;
import org.github.bobobot.access.UserRole;
import org.github.bobobot.entities.Reply;

import javax.annotation.PostConstruct;
import java.time.format.DateTimeFormatter;

/**
 * Represents the header of a reply.
 * This includes the poster's name, the date of the rely, and a delete button.
 */
@Slf4j
public class ReplyHeaderLayout extends HorizontalLayout implements View {

	Reply reply;

	public ReplyHeaderLayout(Reply reply) {
		this.reply = reply;

		Label usernameLabel = new Label(reply.getUser().getName());
		Label dateLabel = new Label(reply.getDate().format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm:ss")));
		//TODO: only delete if logged in or admin
		//TODO: icon instead of text
		Button deleteButton = new Button("delete");
		PermissionHandler.restrictComponentToLoggedInUsers(deleteButton, reply.getUser().getId(), true);
		addComponents(usernameLabel, dateLabel, deleteButton);
		addStyleName("reply-div__header card-header col-12");
	}
}
