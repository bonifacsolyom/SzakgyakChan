package org.github.bobobot.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;

@SpringView(name = ReplyView.name)
public class ReplyView extends HorizontalLayout implements View {
	public static final String name = "replyView";
}
