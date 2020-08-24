package org.github.bobobot.ui;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.*;
import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.access.PermissionHandler;
import org.github.bobobot.services.IUserService;
import org.github.bobobot.ui.views.ErrorView;
import org.github.bobobot.ui.views.LoginView;
import org.github.bobobot.ui.views.MainView;
import org.github.bobobot.ui.views.RegisterView;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@SpringViewDisplay
@Theme("mytheme")
@StyleSheet("https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css")
@JavaScript("https://code.jquery.com/jquery-3.5.1.slim.min.js")
@JavaScript("https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js")
@JavaScript("https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js")
@Slf4j
public class MainUI extends UI implements ViewDisplay {


	@Autowired
	private SpringNavigator navigator;

	Panel springViewDisplay; //Ebben jelenítjük meg a view-ot

	VerticalLayout layout; //Ez az egész oldal layoutja

	CssLayout navigationBar;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		PermissionHandler.setCurrentUserToNotLoggedIn();

		layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setStyleName("layout-padding");

		setNavigator(navigator);
		getNavigator().setErrorView(ErrorView.class);

		navigationBar = new CssLayout();
		initNavbar();
		layout.addComponent(navigationBar);

		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();
		springViewDisplay.setStyleName("backgroundGradient");
		layout.addComponent(springViewDisplay);
		layout.setExpandRatio(springViewDisplay, 1.0f);

		setContent(layout);
	}

	@Override
	public void showView(View view) {
		springViewDisplay.setContent((Component) view);
	}

	public void initNavbar() {
		log.info("rendered navbar");
		navigationBar.removeAllComponents();

		Image logo = new Image("", new ThemeResource("images/logo.png"));
		logo.setStyleName("logo");
		logo.addClickListener(clickEvent -> getUI().getNavigator().navigateTo(MainView.name));
		navigationBar.addComponent(logo);
		Button loginButton = new Button("Login", event -> getUI().getNavigator().navigateTo(LoginView.name));
		Button registerButton = new Button("Register", event -> getUI().getNavigator().navigateTo(RegisterView.name));
		Button logoutButton = new Button("Log out", event -> {
			PermissionHandler.setCurrentUserToNotLoggedIn();
			Page.getCurrent().reload();
		});
		navigationBar.addComponent(loginButton);
		navigationBar.addComponent(registerButton);
		navigationBar.addComponent(logoutButton);

		PermissionHandler.restrictComponentToLoggedOutUsers(loginButton, loginButton::setVisible);
		PermissionHandler.restrictComponentToLoggedOutUsers(registerButton, registerButton::setVisible);
		PermissionHandler.restrictComponentToLoggedInUsers(logoutButton, logoutButton::setVisible);

	}
}
