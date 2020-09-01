package org.github.bobobot.ui.views;


import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.access.PermissionHandler;
import org.github.bobobot.entities.Board;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.services.IBoardService;
import org.github.bobobot.ui.views.layouts.NewThreadFormLayout;
import org.github.bobobot.ui.views.layouts.ThreadLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;

import static com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

@SpringView(name = BoardView.name)
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class BoardView extends VerticalLayout implements View {
	public static final String name = "boardView";

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	IBoardService boardService;

	@Getter
	Board board;

	/**
	 * Sets which board this view should display, given to us by the URL parameters.
	 * @param eventParameters The parameters of the enter event
	 */
	private void setViewBoard(String eventParameters) {
		Long boardId = -1L;
		try {
			boardId = Long.parseLong(eventParameters);
		} catch (NumberFormatException e) {
			log.error("Incorrect board parameter: " + eventParameters);
			e.printStackTrace();
		}

		board = boardService.findById(boardId);
	}

	@Override
	@Transactional
	public void enter(ViewChangeEvent event) {
		log.info("Entered board view");
		setViewBoard(event.getParameters());

		//vaadin te csak megnehezíted az életemet
		Label newThreadButton = new Label("<a class=\"btn btn-primary\" data-toggle=\"collapse\" href=\"#collapseNewThread\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseExample\">" +
				"Create new thread" +
				"</a>");
		newThreadButton.setContentMode(ContentMode.HTML);
		newThreadButton.addStyleName("create-new-button");
		addComponent(newThreadButton);

		NewThreadFormLayout newThreadFormLayout = appContext.getBean(NewThreadFormLayout.class);
		addComponent(newThreadFormLayout);
		newThreadFormLayout.setId("collapseNewThread");
		newThreadFormLayout.addStyleName("collapse");
		PermissionHandler.restrictComponentToLoggedInUsers(newThreadFormLayout, newThreadFormLayout::setVisible);
		PermissionHandler.restrictComponentToLoggedInUsers(newThreadButton, newThreadButton::setVisible);

		addStyleName("row");

		for (Thread thread : board.getThreads()) {
			ThreadLayout threadLayout = appContext.getBean(ThreadLayout.class, this).init(thread);
			addComponent(threadLayout);
		}
	}
}
