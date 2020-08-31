package org.github.bobobot.ui.views.layouts;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.SucceededEvent;
import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.access.PermissionHandler;
import org.github.bobobot.entities.Board;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.User;
import org.github.bobobot.services.IThreadService;
import org.github.bobobot.ui.views.BoardView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.NonNull;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicBoolean;

@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class NewThreadFormLayout extends NewCommentFormLayout {

	@Autowired
	private IThreadService threadService;

	AtomicBoolean titleNotEmpty = new AtomicBoolean(false);

	TextField threadTitleField;

	@Override
	@PostConstruct
	protected void init() {
		binder.setBean(new ThreadFormContent());
		threadTitleField = new TextField("Title");
		binder.bind(threadTitleField, ThreadFormContent::getTitle, ThreadFormContent::setTitle);

		threadTitleField.addValueChangeListener(event -> {
			titleNotEmpty.set(!event.getValue().isEmpty());
			if (titleNotEmpty.get()) threadTitleField.removeStyleName("incorrect-input");
			else threadTitleField.addStyleName("incorrect-input");
		});

		addComponent(threadTitleField);

		super.init();
	}

	@Override
	protected boolean buttonRequirements() {
		return super.buttonRequirements() && titleNotEmpty.get();
	}

	@Override
	protected void buttonClickListener() {
		log.info("button clicked!");
		if (buttonRequirements()) uploadButton.submitUpload();
		else {
			log.info("One or more posting requirements are not met");
			//TODO: írj ki valamit
		}
	}

	@Override
	protected void post() {
				log.info("upload succeeded!");

				ThreadFormContent threadContentBean = binder.getBean();
				threadContentBean.setImagePath(imageReceiver.getLastFileName());

				Board board = ((BoardView) getParent()).getBoard();
				User currentUser = userService.findById(PermissionHandler.getCurrentUser().getId());

				Thread thread = threadService.create(threadContentBean.getTitle(), board, currentUser);
				replyService.post(threadContentBean.getContent(), thread, currentUser, threadContentBean.getImagePath());

				//We refresh the page for the new thread to appear
				getUI().getNavigator().navigateTo(BoardView.name + "/" + board.getId());
	}

	@Override
	protected void addStyleNameToForm() {
		addStyleName("create-thread-form");
	}
}
