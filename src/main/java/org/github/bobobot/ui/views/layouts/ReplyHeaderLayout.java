package org.github.bobobot.ui.views.layouts;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.access.PermissionHandler;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.services.IReplyService;
import org.github.bobobot.services.IThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Represents the header of a reply.
 * This includes the poster's name, the date of the rely, and a delete button.
 */
@Slf4j
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReplyHeaderLayout extends HorizontalLayout implements View {

	Optional<Thread> thread;
	Reply reply;

	@Autowired
	private IReplyService replyService;

	public ReplyHeaderLayout init(Reply reply) {
		return init(Optional.empty(), reply);
	}

	public ReplyHeaderLayout init(Optional<Thread> thread, Reply reply) {
		this.reply = reply;
		this.thread = thread;

		if (thread.isPresent()) {
			Label threadLabel = new Label(thread.get().getTitle());
			addComponent(threadLabel);
		}

		Label usernameLabel = new Label(reply.getUser().getName());
		Label dateLabel = new Label(reply.getDate().format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm:ss")));
		//TODO: only delete if logged in or admin
		//TODO: icon instead of text
		Button deleteButton = new Button("delete");
		PermissionHandler.restrictComponentToLoggedInUser(deleteButton, reply.getUser().getId(), true);

//		deleteButton.addClickListener(event -> {
//			replyService.delete(reply.getId());
//		});

		addComponents(usernameLabel, dateLabel, deleteButton);
		addStyleName("reply-div__header card-header col-12");

		return this;
	}
}
