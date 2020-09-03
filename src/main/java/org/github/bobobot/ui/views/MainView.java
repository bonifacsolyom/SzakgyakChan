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
import java.util.ArrayList;
import java.util.Arrays;
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

	private static boolean animate = true;

	@PostConstruct
	void init() {

		Label logoText;
		if (animate) {
			logoText = new Label("<h1 class=\"animate__animated animate__zoomInDown animate__slow logo-text__szakgyak\">Szakgyak</h1><h1 class=\"animate__animated animate__zoomInUp animate__slow logo-text__chan\">Chan</h1>", ContentMode.HTML);
		} else {
			logoText = new Label("<h1 class=\"logo-text__szakgyak\">Szakgyak</h1><h1 class=\"logo-text__chan\">Chan</h1>", ContentMode.HTML);
		}
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
			if (animate) longName.addStyleName("invisible");
			div.addComponents(shortName, longName);
			div.addLayoutClickListener(layoutClickEvent -> {
				if (layoutClickEvent.getButton().equals(MouseEventDetails.MouseButton.LEFT)) //We make sure that the user only navigates to another board if they pressed left click
					getUI().getNavigator().navigateTo(BoardView.name + "/" + board.getId());
			});
			div.addStyleNames("board-div", "col-3");
			if (animate) div.addStyleName("invisible");
			boardLayout.addComponent(div);
			boardDivs.add(div);
		}

		//Presentation mode, pls don't even try to process this it's not even part of the real program
		if (animate) {
			addShortcutListener(new ShortcutListener("presentation-shortcut-listener", ShortcutAction.KeyCode.SPACEBAR, null) {
				int spacePressed = 0;
				int divShown = 0;

				int[] showDivsSteps = new int[]{1, 3, 5, 7};
				int[] showDescriptionSteps = new int[]{2, 4, 6, 8};
				int changeLongToIntStep = 7;
				int changeIntToLongAndShowDescriptionStep = 8;

				@Override
				public void handleAction(Object sender, Object target) {
					if (spacePressed++ < 9) {
						Label longName = (Label) boardDivs.get(divShown).getComponent(1);
						Label shortName = (Label) boardDivs.get(divShown).getComponent(0);

						if (spacePressed == changeLongToIntStep) { //cheeky breeky
							shortName.setValue("/int/");
						}
						if (contains(showDivsSteps, spacePressed)) { //If we want to show a new board div
							VerticalLayout boardLayout = boardDivs.get(divShown);
							boardLayout.removeStyleName("invisible");
							boardLayout.addStyleNames("animate__animated", "animate__fadeInDown");
						}
						if (spacePressed == changeIntToLongAndShowDescriptionStep) { //undo cheeky breeky
							shortName.setValue("/long/");
						}
						if (contains(showDescriptionSteps, spacePressed)) { //if we want to show the board div's description
							longName.removeStyleName("invisible");
							longName.addStyleNames("animate__animated", "animate__fadeInDown");
							divShown++;
						}
					}
				}

				private boolean contains(int[] array, int number) {
					return Arrays.stream(array).anyMatch(i -> i == number);
				}

			});
		}

		addComponent(boardLayout);
		animate = false;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		log.info("Entered main view");
	}
}
