package org.github.bobobot.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

@SpringView
@SpringComponent
@Scope(scopeName = "vaadin-ui")
public class ErrorView extends HorizontalLayout implements View {

	@PostConstruct
	void init() {
		addStyleName("siteColor");
		Label errorLabel = new Label("oh frick error");
		addComponent(errorLabel);
	}

}
