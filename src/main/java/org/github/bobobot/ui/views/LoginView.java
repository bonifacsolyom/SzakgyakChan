package org.github.bobobot.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.LoginForm;
import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.entities.User;
import org.github.bobobot.services.IUserService;
import org.github.bobobot.ui.MainUI;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Optional;

@SpringView(name = LoginView.name)
@SpringComponent
@Slf4j
public class LoginView extends HorizontalLayout implements View {
	public static final String name = "loginView";

	@Autowired
	private IUserService userService;

	@PostConstruct
	void init() {

		LoginForm loginForm = new LoginForm();
		loginForm.setUsernameCaption("Email");
		loginForm.addLoginListener(loginEvent -> {
			String email = loginEvent.getLoginParameter("username"); //yes, username
			String password = loginEvent.getLoginParameter("password");
			log.info("Login attempt with " + email + ", " + password + "...");
			Optional<User> loggedInUser = userService.login(email, password);
			if (loggedInUser.isPresent()) {
				log.info("Successful login!");
				((MainUI)getUI()).reRenderNavbar();
				getUI().getNavigator().navigateTo(MainView.name);
			} else {
				log.info("Login failed.");
			}
		});

		addComponent(loginForm);
	}
}
