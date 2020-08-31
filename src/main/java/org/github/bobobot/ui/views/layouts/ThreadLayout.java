package org.github.bobobot.ui.views.layouts;

import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.VerticalLayout;
import org.github.bobobot.access.PermissionHandler;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.services.impl.ThreadService;
import org.github.bobobot.ui.views.BoardView;
import org.github.bobobot.ui.views.misc.OrderBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.NonNull;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;
import java.util.stream.Collectors;

@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ThreadLayout extends VerticalLayout implements View {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private ThreadService threadService;

	@Autowired
	protected TransactionTemplate transactionTemplate;

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

		//Order selection
		List<String> stringOrders = new ArrayList<>(Arrays.asList("Date", "Vote"));
		NativeSelect<String> orderSelector = new NativeSelect<>("Order by:", stringOrders);
		orderSelector.setEmptySelectionAllowed(false);
		orderSelector.setSelectedItem(orderBy == OrderBy.DATE ? "Date" : "Vote");
		orderSelector.addValueChangeListener(valueChangeEvent -> {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(@NonNull TransactionStatus transactionStatus) {
					removeAllComponents();
					OrderBy order = valueChangeEvent.getValue().equals("Date") ? OrderBy.DATE : OrderBy.VOTE;
					init(threadService.findById(thread.getId()), order);
				}
			});
		});
		headerLayout.addOrderSelector(orderSelector);
		orderSelector.addStyleName("order-selector");

		for (Reply reply : getOrderedListOfReplies()) {

			ReplyLayout replyLayout = appContext.getBean(ReplyLayout.class).init(reply);
			replyLayout.addStyleName("reply-div");
			addComponent(replyLayout);
		}

		Label newReplyButton = new Label("<a class=\"btn btn-primary create-new-button\" data-toggle=\"collapse\" href=\"#collapseNewThread" + thread.getId() +" \" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseExample\">" +
				"Create reply</a>");
		newReplyButton.setContentMode(ContentMode.HTML);
		addComponent(newReplyButton);

		NewCommentFormLayout newCommentFormLayout = appContext.getBean("NewCommentFormLayout", NewCommentFormLayout.class);
		newCommentFormLayout.setCurrentThread(thread);
		addComponent(newCommentFormLayout);
		newCommentFormLayout.setId("collapseNewThread" + thread.getId());
		newCommentFormLayout.addStyleName("collapse");

		PermissionHandler.restrictComponentToLoggedInUsers(newReplyButton, newReplyButton::setVisible);
		PermissionHandler.restrictComponentToLoggedInUsers(newCommentFormLayout, newCommentFormLayout::setVisible);

		addStyleName("thread-div card col-6");

		return this;
	}

	private Reply getFirstReply() {
		return thread.getReplies().get(0);
	}

	private List<Reply> getOrderedListOfReplies() {
		//we always skip the first reply since that's the content of the thread itself
		switch (orderBy) {
			case DATE:
				return thread.getReplies().stream().skip(1).sorted(Comparator.comparing(Reply::getDate)).collect(Collectors.toList());
			case VOTE:
				return thread.getReplies().stream().skip(1).sorted(Comparator.comparing(Reply::getVoteCount).reversed()).collect(Collectors.toList());
			default:
				throw new EnumConstantNotPresentException(OrderBy.class, "Can't order by " + orderBy + "!");
		}
	}
}
