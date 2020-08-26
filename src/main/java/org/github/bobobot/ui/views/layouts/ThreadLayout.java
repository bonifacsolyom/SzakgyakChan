package org.github.bobobot.ui.views.layouts;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.VerticalLayout;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.ui.views.BoardView;
import org.github.bobobot.ui.views.misc.OrderBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ThreadLayout extends VerticalLayout implements View {

	@Autowired
	private ApplicationContext appContext;

	BoardView parentBoardView;
	Thread thread;
	OrderBy orderBy;

	ThreadLayout(BoardView parentBoardView) {
		this.parentBoardView = parentBoardView;
	}

	public ThreadLayout init(Thread thread) {
		return init(thread, OrderBy.DATE);
	}

	public ThreadLayout init(Thread thread, OrderBy orderBy) {
		this.thread = thread;
		this.orderBy = orderBy;
		setSpacing(false);

		//imagine using vaadin unironically

		//since the thread is always started by the user who the first reply belongs to, we use that reply's data
		//for the thread's header and content
		ReplyHeaderLayout headerLayout = appContext.getBean(ReplyHeaderLayout.class).init(Optional.of(thread), getFirstReply());
		ReplyContentLayout contentLayout = appContext.getBean(ReplyContentLayout.class).init(getFirstReply());
		addComponents(headerLayout, contentLayout);

		for (Reply reply : getOrderedListOfReplies()) {

			ReplyLayout replyLayout = appContext.getBean(ReplyLayout.class).init(reply);
			replyLayout.addStyleName("reply-div");
			addComponent(replyLayout);
		}

		NewCommentFormLayout newCommentFormLayout = appContext.getBean("NewCommentFormLayout", NewCommentFormLayout.class);
		newCommentFormLayout.setCurrentThread(thread);
		addComponent(newCommentFormLayout);

		addStyleName("thread-div card col-6");

		return this;
	}

	private Reply getFirstReply() {
		return thread.getReplies().get(0);
	}

	private List<Reply> getOrderedListOfReplies() {
		switch (orderBy) {
			case DATE:
				//we skip the first reply since that's the content of the thread itself
				return thread.getReplies().stream().skip(1).sorted(Comparator.comparing(Reply::getDate)).collect(Collectors.toList());
			case VOTE:
				//we skip the first reply since that's the content of the thread itself
				return thread.getReplies().stream().skip(1).sorted(Comparator.comparing(Reply::getVoteCount)).collect(Collectors.toList());
			default:
				throw new EnumConstantNotPresentException(OrderBy.class, "Can't order by " + orderBy + "!");
		}
	}
}
