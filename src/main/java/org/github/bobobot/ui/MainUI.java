package org.github.bobobot.ui;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.PreserveOnRefresh;
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
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.access.PermissionHandler;
import org.github.bobobot.services.IUserService;
import org.github.bobobot.ui.views.ErrorView;
import org.github.bobobot.ui.views.LoginView;
import org.github.bobobot.ui.views.MainView;
import org.github.bobobot.ui.views.RegisterView;
import org.github.bobobot.ui.views.layouts.NavbarLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@SpringUI
@SpringViewDisplay
@Theme("mytheme")
@StyleSheet("https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css")
@StyleSheet("https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css")
@JavaScript("https://code.jquery.com/jquery-3.5.1.slim.min.js")
@JavaScript("https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js")
@JavaScript("https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js")
@Slf4j
public class MainUI extends UI implements ViewDisplay {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private SpringNavigator navigator;

	//TODO: debug shit, töröld
	@Autowired
	private IUserService userService;

	@Getter
	private Panel springViewDisplay; //Ebben jelenítjük meg a view-ot

	private VerticalLayout layout; //Ez az egész oldal layoutja

	private NavbarLayout navigationBar;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		PermissionHandler.setCurrentUserToNotLoggedIn();

		layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setStyleName("layout-padding");

		setNavigator(navigator);
		getNavigator().setErrorView(ErrorView.class);

		navigationBar = appContext.getBean(NavbarLayout.class);
		layout.addComponent(navigationBar);

		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();
		springViewDisplay.setStyleName("background-gradient");
		layout.addComponent(springViewDisplay);
		layout.setExpandRatio(springViewDisplay, 1.0f);

		setContent(layout);
	}

	@Override
	public void showView(View view) {
		springViewDisplay.setContent((Component) view);
	}

 	public void reRenderNavbar() {
		navigationBar.render();
	}
}
