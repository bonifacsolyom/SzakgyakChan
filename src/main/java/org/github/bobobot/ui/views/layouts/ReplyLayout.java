package org.github.bobobot.ui.views.layouts;

import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.github.bobobot.entities.Reply;

import java.time.format.DateTimeFormatter;

public class ReplyLayout extends VerticalLayout implements View {

	Reply reply;

	public ReplyLayout(Reply reply) {
		this.reply = reply;
		setSpacing(false);

		ReplyHeaderLayout headerLayout = new ReplyHeaderLayout(reply);
		ReplyContentLayout contentLayout = new ReplyContentLayout(reply);

		addComponents(headerLayout, contentLayout);

		addStyleName("reply-div card col-6");
	}
}
