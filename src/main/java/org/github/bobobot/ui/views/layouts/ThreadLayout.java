package org.github.bobobot.ui.views.layouts;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.Reply;

import java.time.format.DateTimeFormatter;

public class ThreadLayout extends VerticalLayout implements View {

	Thread thread;

	public ThreadLayout(Thread thread) {
		this.thread = thread;
		setSpacing(false);

		//imagine using vaadin unironically

		add

		//mivel a threadet mindig az indítja, aki az első reply-t küldte bele, így annak az adatait írjuk ki a headerbe
		HorizontalLayout headerLayout = new HorizontalLayout();
		Label usernameLabel = new Label(getFirstReply().getUser().getName());
		Label dateLabel = new Label(getFirstReply().getDate().format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm:ss")));
		//TODO: only delete if logged in or admin
		//TODO: icon instead of text
		Button deleteButton = new Button("delete");
		headerLayout.addComponents(usernameLabel, dateLabel, deleteButton);
		headerLayout.addStyleName("reply-div__header card-header col-12");

		addStyleName("card col-6");
	}

	private Reply getFirstReply() {
		return thread.getReplies().get(0);
	}
}
