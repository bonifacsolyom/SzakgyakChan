package org.github.bobobot.ui;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.access.PermissionHandler;
import org.github.bobobot.ui.views.ErrorView;
import org.github.bobobot.ui.views.layouts.NavbarLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@SpringUI
@SpringViewDisplay
@Theme("mytheme")
@StyleSheet("https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css")
@StyleSheet("https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css")
@StyleSheet("https://raw.githubusercontent.com/anater/tachyons-animate/master/css/tachyons-animate.min.css")
@StyleSheet("https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.0.0/animate.min.css")
@JavaScript("https://code.jquery.com/jquery-3.5.1.slim.min.js")
@JavaScript("https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js")
@JavaScript("https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js")
@Slf4j
public class MainUI extends UI implements ViewDisplay {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private SpringNavigator navigator;

	private Panel viewDisplay;

	private NavbarLayout navigationBar;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		PermissionHandler.setCurrentUserToNotLoggedIn();

		//This is the layout of the entire site, inculding both the navbar and the view display
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.addStyleName("layout-padding");

		setNavigator(navigator);
		getNavigator().setErrorView(ErrorView.class);

		navigationBar = appContext.getBean(NavbarLayout.class);
		layout.addComponent(navigationBar);

		viewDisplay = new Panel();
		viewDisplay.setSizeFull();
		viewDisplay.addStyleName("background-gradient");
		layout.addComponent(viewDisplay);
		layout.setExpandRatio(viewDisplay, 1.0f);

		setContent(layout);
	}

	@Override
	public void showView(View view) {
		viewDisplay.setContent((Component) view);
	}

	public void reRenderNavbar() {
		navigationBar.render();
	}
}
