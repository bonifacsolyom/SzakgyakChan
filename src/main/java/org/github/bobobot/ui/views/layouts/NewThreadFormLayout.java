package org.github.bobobot.ui.views.layouts;

import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.access.PermissionHandler;
import org.github.bobobot.entities.Board;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.User;
import org.github.bobobot.services.IReplyService;
import org.github.bobobot.services.IThreadService;
import org.github.bobobot.services.IUserService;
import org.github.bobobot.ui.views.BoardView;
import org.github.bobobot.ui.views.misc.ImageReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.NonNull;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicBoolean;

@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class NewThreadFormLayout extends FormLayout implements View {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private IThreadService threadService;

	@Autowired
	private IReplyService replyService;

	@Autowired
	private IUserService userService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Data
	private class ThreadFormContent {
		private String title;
		private String content;
		private String imagePath;
	}

	@PostConstruct
	private void init() {
		Binder<ThreadFormContent> binder = new Binder<>(ThreadFormContent.class);
		AtomicBoolean titleNotEmpty = new AtomicBoolean(false);
		AtomicBoolean contentNotEmpty = new AtomicBoolean(false);

		binder.setBean(new ThreadFormContent());

		TextField threadTitleField = new TextField();
		binder.bind(threadTitleField, ThreadFormContent::getTitle, ThreadFormContent::setTitle);
		TextArea threadContentField = new TextArea();
		binder.bind(threadContentField, ThreadFormContent::getContent, ThreadFormContent::setContent);

		ImageReceiver imageReceiver = new ImageReceiver();

		Upload uploadButton = new Upload("Select an image", imageReceiver);
		uploadButton.setImmediateMode(false);
//		uploadButton.addSucceededListener(imageReceiver);
//		uploadButton.addFailedListener(imageReceiver);
//		uploadButton.addProgressListener(imageReceiver);
		uploadButton.setButtonCaption(null);
		uploadButton.setAcceptMimeTypes("image/jpeg");
		uploadButton.setAcceptMimeTypes("image/png"); //TODO: test this, lehet csak az egyik jó

		Button postButton = new Button("Post Thread");

		threadTitleField.addValueChangeListener(event -> {
			titleNotEmpty.set(!event.getValue().isEmpty());
			if (titleNotEmpty.get()) threadTitleField.removeStyleName("incorrect-input");
			else threadTitleField.addStyleName("incorrect-input");
		});

		threadContentField.addValueChangeListener(event -> {
			contentNotEmpty.set(!event.getValue().isEmpty());
			if (contentNotEmpty.get()) threadContentField.removeStyleName("incorrect-input");
			else threadContentField.addStyleName("incorrect-input");
		});

		uploadButton.addSucceededListener(event -> {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(@NonNull TransactionStatus transactionStatus) {
					log.info("upload succeeded!");

					ThreadFormContent threadContentBean = binder.getBean();
					threadContentBean.setImagePath(imageReceiver.getLastFileName());

					Board board = ((BoardView) getParent()).getBoard();
					User currentUser = userService.findById(PermissionHandler.getCurrentUser().getId());

					Thread thread = threadService.create(threadContentBean.getTitle(), board, currentUser);
					replyService.post(threadContentBean.getContent(), thread, currentUser, threadContentBean.getImagePath());
				}
			});
		});

		postButton.addClickListener(event -> {
			log.info("button clicked!");
			if (titleNotEmpty.get() && contentNotEmpty.get()) uploadButton.submitUpload();
			else {
				log.info("One or more posting requirements are not met");
				//TODO: írj ki valamit
			}
		});

		addComponents(threadTitleField, threadContentField, uploadButton, postButton);

	}

}
