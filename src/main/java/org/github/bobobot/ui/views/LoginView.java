package org.github.bobobot.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.LoginForm;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

@SpringView(name = LoginView.name)
@SpringComponent
@Slf4j
public class LoginView extends HorizontalLayout implements View {
	public static final String name = "loginView";

	@PostConstruct
	void init() {
		setStyleName("siteColor");
		LoginForm loginForm = new LoginForm();
		loginForm.setUsernameCaption("Email");
		loginForm.addLoginListener(loginEvent -> {
			log.info("Login attempt with " + loginEvent.getLoginParameter("username") + ", " + loginEvent.getLoginParameter("password"));
		});

		addComponent(loginForm);
		addComponent(new Label("Test label"));
	}
}
