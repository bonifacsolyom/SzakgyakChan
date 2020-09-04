package org.github.bobobot.ui.views.layouts;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.CssLayout;
import org.github.bobobot.entities.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReplyLayout extends CssLayout implements View {

	Reply reply;
	@Autowired
	private ApplicationContext appContext;

	public ReplyLayout init(Reply reply) {
		this.reply = reply;

		ReplyHeaderLayout headerLayout = appContext.getBean(ReplyHeaderLayout.class).init(reply);
		ReplyContentLayout contentLayout = appContext.getBean(ReplyContentLayout.class).init(reply);

		addComponents(headerLayout, contentLayout);

		addStyleName("reply-div card col-9");

		return this;
	}
}
