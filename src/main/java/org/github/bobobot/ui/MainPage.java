package org.github.bobobot.ui;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.*;
import org.github.bobobot.ui.views.BoardView;
import org.github.bobobot.ui.views.LoginView;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@SpringViewDisplay
public class MainPage extends UI {

	@Autowired
	private SpringNavigator navigator;

	private Panel springViewDisplay; //Ebben jelenítjük meg a view-ot

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		VerticalLayout layout = new VerticalLayout();

		setNavigator(navigator);

//		getNavigator().addView(BoardView.name, new BoardView());

		layout.addComponent(new Label("SzakgyakChan"));
		layout.addComponent(new Button("I am a button", event -> getUI().getNavigator().navigateTo(LoginView.name)));
		layout.setSizeFull();

		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();
		layout.addComponent(springViewDisplay);

		setContent(layout);
	}

}
