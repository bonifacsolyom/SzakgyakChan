package org.github.bobobot.ui.views.layouts;

import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Upload;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.access.PermissionHandler;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.User;
import org.github.bobobot.services.IReplyService;
import org.github.bobobot.services.IUserService;
import org.github.bobobot.ui.views.misc.ImageReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.NonNull;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicBoolean;

@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class NewCommentFormLayout extends FormLayout implements View {

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	protected IReplyService replyService;

	@Autowired
	protected IUserService userService;

	@Autowired
	protected TransactionTemplate transactionTemplate;

	@Setter
	private Thread currentThread;

	private AtomicBoolean contentNotEmpty = new AtomicBoolean(false);
	private AtomicBoolean imageUploaded = new AtomicBoolean(false);
	protected Binder<ThreadFormContent> binder = new Binder<>(ThreadFormContent.class);;
	protected Upload uploadButton;
	protected ImageReceiver imageReceiver;


	@Data
	protected class ThreadFormContent {
		private String title; //only used for threads
		private String content;
		private String imagePath;
	}

	@PostConstruct
	protected void init() {
		binder.setBean(new ThreadFormContent());
		TextArea threadContentField = new TextArea();
		binder.bind(threadContentField, ThreadFormContent::getContent, ThreadFormContent::setContent);

		imageReceiver = new ImageReceiver();

		uploadButton = new Upload("Select an image", imageReceiver);
		uploadButton.setImmediateMode(false);
		uploadButton.setButtonCaption(null);
		uploadButton.setAcceptMimeTypes("image/jpeg");
		uploadButton.setAcceptMimeTypes("image/png"); //TODO: test this, lehet csak az egyik jó

		Button postButton = new Button("Post");


		threadContentField.addValueChangeListener(event -> {
			contentNotEmpty.set(!event.getValue().isEmpty());
			if (contentNotEmpty.get()) threadContentField.removeStyleName("incorrect-input");
			else threadContentField.addStyleName("incorrect-input");
		});

		uploadButton.addSucceededListener(event -> {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(@NonNull TransactionStatus transactionStatus) {
					post();
				}
			});
		});

		uploadButton.addContextClickListener(event -> {
			log.info("image upload button clicked");
			imageUploaded.set(true);
		});

		postButton.addClickListener(event -> {
			buttonClickListener();
		});

		addComponents(threadContentField, uploadButton, postButton);
	}

	protected boolean buttonRequirements() {
		return contentNotEmpty.get();
	}

	@Transactional
	protected void buttonClickListener() {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(@NonNull TransactionStatus transactionStatus) {
				log.info("button clicked!");
				if (buttonRequirements()) {
					//If the user selected an image, we only post the comment after the image has been uploaded
					if (imageUploaded.get()) uploadButton.submitUpload();
					else post();
				} else {
					log.info("One or more posting requirements are not met");
					//TODO: írj ki valamit
				}
			}
		});
	}

	@Transactional
	protected void post() {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(@NonNull TransactionStatus transactionStatus) {
				log.info("upload succeeded!");

				ThreadFormContent threadContentBean = binder.getBean();
				if (imageReceiver.getLastFileName() != null)
					threadContentBean.setImagePath(imageReceiver.getLastFileName());

				User currentUser = userService.findById(PermissionHandler.getCurrentUser().getId());

				replyService.post(threadContentBean.getContent(), currentThread, currentUser, threadContentBean.getImagePath());
			}
		});
	}
}
