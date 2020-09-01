package org.github.bobobot.ui.views.layouts;

import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.services.IReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;

import java.time.format.DateTimeFormatter;

@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReplyLayout extends CssLayout implements View {

	@Autowired
	private ApplicationContext appContext;

	Reply reply;

	public ReplyLayout init(Reply reply) {
		this.reply = reply;

		ReplyHeaderLayout headerLayout = appContext.getBean(ReplyHeaderLayout.class).init(reply);
		ReplyContentLayout contentLayout = appContext.getBean(ReplyContentLayout.class).init(reply);

		addComponents(headerLayout, contentLayout);

		addStyleName("reply-div card col-9");

		return this;
	}
}
