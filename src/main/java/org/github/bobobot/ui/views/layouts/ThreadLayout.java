package org.github.bobobot.ui.views.layouts;

import com.google.common.collect.Iterables;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.Reply;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class ThreadLayout extends VerticalLayout implements View {

	Thread thread;

	public ThreadLayout(Thread thread) {
		this.thread = thread;
		setSpacing(false);

		//imagine using vaadin unironically

		//since the thread is always started by the user who the first reply belongs to, we use that reply's data
		//for the thread's header and content
		//TODO: display thread name in header
		ReplyHeaderLayout headerLayout = new ReplyHeaderLayout(getFirstReply());
		ReplyContentLayout contentLayout = new ReplyContentLayout(getFirstReply());
		addComponents(headerLayout, contentLayout);

		//we skip the first reply since that's the content of the thread itself
		for (Reply reply : Iterables.skip(thread.getReplies(), 1)) {

			ReplyLayout replyLayout = new ReplyLayout(reply);
			replyLayout.addStyleName("reply-div");
			addComponent(replyLayout);
		}

		addStyleName("thread-div card col-6");
	}

	private Reply getFirstReply() {
		return thread.getReplies().get(0);
	}
}
