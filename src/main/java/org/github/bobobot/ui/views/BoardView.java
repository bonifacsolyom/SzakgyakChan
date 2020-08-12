package org.github.bobobot.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

import static com.vaadin.navigator.ViewChangeListener.*;

@SpringView(name = BoardView.name)
@SpringComponent
@Slf4j
public class BoardView extends HorizontalLayout implements View {
	public static final String name = "boardView";

	@PostConstruct
	void init() {
		addComponent(new Label("Test label #2"));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		log.info("gay");
	}
}
