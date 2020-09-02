package org.github.bobobot.ui.views;

import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.entities.Board;
import org.github.bobobot.services.IBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import static com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

@SpringView(name = MainView.name)
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class MainView extends CssLayout implements View {
	public static final String name = "";

	@Autowired
	IBoardService boardService;

	@PostConstruct
	void init() {

		Label logoText = new Label("<h1 class=\"animate__animated animate__zoomInDown animate__slow\">Szakgyak</h1><h1 class=\"animate__animated animate__zoomInUp animate__slow\">Chan</h1>", ContentMode.HTML);
		logoText.addStyleName("logo-text");
		addComponent(logoText);


		CssLayout boardLayout = new CssLayout();
		setWidthFull();
		boardLayout.addStyleNames("board-list", "col-6");

		List<VerticalLayout> boardDivs = new ArrayList<>();

		for (Board board : boardService.list()) {
			VerticalLayout div = new VerticalLayout();
			Label shortName = new Label("/" + board.getShortName() + "/");
			shortName.addStyleNames("font-weight-bold", "board-list__short-name");
			Label longName = new Label(board.getLongName());
			longName.addStyleName("board-list__long-name");
			div.addComponents(shortName, longName);
			div.addLayoutClickListener(layoutClickEvent -> {
				if (layoutClickEvent.getButton().equals(MouseEventDetails.MouseButton.LEFT)) //We make sure that the user only navigates to another board if they pressed left click
					getUI().getNavigator().navigateTo(BoardView.name + "/" + board.getId());
			});
			div.addStyleNames("board-div", "col-3", "invisible");
			boardLayout.addComponent(div);
			boardDivs.add(div);
		}

		//Presentation mode

		addShortcutListener(new ShortcutListener("presentation-shortcut-listener", ShortcutAction.KeyCode.SPACEBAR, null) {
			int divShown = 0;

			@Override
			public void handleAction(Object sender, Object target) {
				if (divShown < boardDivs.size()) {
					VerticalLayout boardLayout = boardDivs.get(divShown++);
					boardLayout.removeStyleName("invisible");
					boardLayout.addStyleNames("animate__animated", "animate__fadeInDown");


				}
			}
		});

		addComponent(boardLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		log.info("Entered main view");
	}
}
