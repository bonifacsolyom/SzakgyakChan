package org.github.bobobot.ui.views;

import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.github.bobobot.entities.User;
import org.github.bobobot.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AliasFor;

import javax.annotation.PostConstruct;


@SpringView(name = RegisterView.name)
@SpringComponent
@Slf4j
public class RegisterView extends HorizontalLayout implements View {
	public static final String name = "registerView";

	@Autowired
	private IUserService userService;

	@Autowired
	private EmailValidator emailValidator;

	@PostConstruct
	void init() {
		setStyleName("siteColor");
		FormLayout registerForm = new FormLayout();
		Binder<User> binder = new Binder<>(User.class);

		binder.setBean(new User());

		TextField usernameField = new TextField("Username");
		binder.bind(usernameField, User::getName, User::setName);
		TextField emailField = new TextField("Email");
		binder.bind(emailField, User::getEmail, User::setEmail);
		TextField passwordField = new PasswordField("Password");
		binder.bind(passwordField, User::getPasswordHash, User::setPasswordHash);
		TextField passwordAgainField = new PasswordField("Password (again)");
		Button registerButton = new Button("Register");
		passwordAgainField.addValueChangeListener(valueChangeEvent -> {
			if (!valueChangeEvent.getValue().equals(passwordField.getValue())) {
				passwordField.addStyleName("incorrect-input");
				passwordAgainField.addStyleName("incorrect-input");
				registerButton.setEnabled(false);
			} else {
				passwordField.removeStyleName("incorrect-input");
				passwordAgainField.removeStyleName("incorrect-input");
				registerButton.setEnabled(true);
			}
		});
		emailField.addValueChangeListener(valueChangeEvent -> {
			if (!emailValidator.isValid(valueChangeEvent.getValue())) {
				emailField.addStyleName("incorrect-input");
			} else {
				emailField.removeStyleName("incorrect-input");
			}
		});

		registerButton.addClickListener(clickEvent -> {
			userService.register(binder.getBean());
			getUI().getNavigator().navigateTo(MainView.name);
		});

		registerForm.addComponents(usernameField, emailField, passwordField, passwordAgainField, registerButton);

		usernameField.setRequiredIndicatorVisible(true);
		emailField.setRequiredIndicatorVisible(true);
		passwordField.setRequiredIndicatorVisible(true);


		addComponent(registerForm);
	}
}
