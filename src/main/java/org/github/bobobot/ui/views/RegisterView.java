package org.github.bobobot.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AliasFor;

import javax.annotation.PostConstruct;


@SpringView(name = RegisterView.name)
@SpringComponent
@Slf4j
public class RegisterView extends HorizontalLayout implements View {
	public static final String name = "registerView";

	@PostConstruct
	void init() {
		setStyleName("siteColor");
		FormLayout registerForm = new FormLayout();

		TextField usernameField = new TextField();
		usernameField.setCaption("Username");
		TextField emailField = new TextField();
		emailField.setCaption("Email");
		TextField passwordField = new TextField();
		passwordField.setCaption("Password");
		Button registerButton = new Button("Register");

		registerForm.addComponents(usernameField, emailField, passwordField, registerButton);

		usernameField.setRequiredIndicatorVisible(true);
		emailField.setRequiredIndicatorVisible(true);
		passwordField.setRequiredIndicatorVisible(true);


		addComponent(registerForm);
		addComponent(new Label("Test label"));
	}
}
