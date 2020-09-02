package org.github.bobobot.ui.views.layouts;

import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.access.PermissionHandler;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.services.IReplyService;
import org.github.bobobot.ui.views.BoardView;
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

	private final HorizontalLayout alignRightDiv = new HorizontalLayout();

	public ReplyHeaderLayout init(Reply reply) {
		return init(Optional.empty(), reply);
	}

	public ReplyHeaderLayout init(Optional<Thread> thread, Reply reply) {
		this.reply = reply;
		this.thread = thread;


		if (thread.isPresent()) {
			Label threadLabel = new Label(thread.get().getTitle());
			addComponent(threadLabel);
			setComponentAlignment(threadLabel, Alignment.MIDDLE_LEFT);
			threadLabel.addStyleName("font-weight-bold");
		}

		Label usernameLabel = new Label(reply.getUser().getName());
		Label dateLabel = new Label(reply.getDate().format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm:ss")));
		dateLabel.addStyleName("text-muted");
		Button deleteButton = new Button();
		deleteButton.addStyleNames("delete-button", "p-0");
		deleteButton.addStyleName(ValoTheme.BUTTON_LINK);
		deleteButton.setIcon(new ThemeResource("images/delete.png"));
		deleteButton.setWidth(40, Unit.PIXELS);
		deleteButton.setHeight(40, Unit.PIXELS);
		alignRightDiv.addComponent(deleteButton);
		PermissionHandler.restrictComponentToLoggedInUser(deleteButton, reply.getUser().getId(), true);

		deleteButton.addClickListener(event -> {
			replyService.delete(reply.getId());
			getUI().getNavigator().navigateTo(BoardView.name + "/" + reply.getThread().getBoard().getId());
		});

		addComponents(usernameLabel, dateLabel, alignRightDiv);

		setComponentAlignment(usernameLabel, Alignment.MIDDLE_LEFT);
		setComponentAlignment(dateLabel, Alignment.MIDDLE_LEFT);
		alignRightDiv.setComponentAlignment(deleteButton, Alignment.MIDDLE_LEFT);

		alignRightDiv.addStyleName("align-right");

		addStyleName("reply-div__header card-header col-12");

		return this;
	}

	public void addOrderSelector(NativeSelect<String> orderSelector) {
		alignRightDiv.addComponent(orderSelector);
	}
}
