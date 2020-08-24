package org.github.bobobot.ui.views.layouts;

import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import lombok.extern.slf4j.Slf4j;
import org.github.bobobot.access.PermissionHandler;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.services.IReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.NonNull;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;

import static org.github.bobobot.entities.VoteNotification.VoteType;

/**
 * Represents the "content" part of a reply.
 * This includes the voting arrows/vote count, the image (if applicable) and the text.
 */
@Slf4j
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReplyContentLayout extends HorizontalLayout implements View {

	Reply reply;

	@Autowired
	IReplyService replyService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	Long userId;

	//Not elegant but spring do be like that sometimes
	ReplyContentLayout init(Reply reply) {
		this.reply = reply;
		userId = PermissionHandler.getCurrentUser().getId();

		VerticalLayout voteLayout = new VerticalLayout();
		Button upvoteButton = new Button();
		Label voteNumberLabel = new Label(String.valueOf(reply.getVoteCount()));
		Button downvoteButton = new Button();
		voteLayout.addComponents(upvoteButton, voteNumberLabel, downvoteButton);

		setVoteColor(voteNumberLabel);

		PermissionHandler.restrictComponentToLoggedInUsers(upvoteButton, upvoteButton::setEnabled);
		PermissionHandler.restrictComponentToLoggedInUsers(downvoteButton, downvoteButton::setEnabled);

		//disgusting but needed for the lambda method to be Transactional
		upvoteButton.addClickListener(clickEvent -> {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(@NonNull TransactionStatus transactionStatus) {
					log.info("Trying to upvote");
					replyService.vote(userId, reply.getId(), VoteType.UPVOTE);
					voteNumberLabel.setValue(String.valueOf(replyService.findById(reply.getId()).getVoteCount()));
					setVoteColor(voteNumberLabel);
				}
			});
		});

		downvoteButton.addClickListener(clickEvent -> {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(@NonNull TransactionStatus transactionStatus) {
					log.info("Trying to downvote");
					replyService.vote(userId, reply.getId(), VoteType.DOWNVOTE);
					voteNumberLabel.setValue(String.valueOf(replyService.findById(reply.getId()).getVoteCount()));
					setVoteColor(voteNumberLabel);
				}
			});
		});

		addComponent(voteLayout);
		if (reply.hasImage()) {
			//TODO: maybe not ThemeResource?
			Image replyImage = new Image("", new ThemeResource(reply.getImage().get()));
			addComponent(replyImage);
		}
		Label replyContentLabel = new Label(reply.getContent());
		addComponent(replyContentLabel);
		addStyleName("reply-div__content card-body col-12");

		return this;
	}

	private void setVoteColor(Label voteNumberLabel) {
		Optional<VoteType> userVoteType = replyService.getUserVote(userId, reply.getId());
		setVoteColor(voteNumberLabel, userVoteType);
	}

	private void setVoteColor(Label voteNumberLabel, Optional<VoteType> voteType) {
		if (!voteType.isPresent()) {
			voteNumberLabel.removeStyleName("upvoted");
			voteNumberLabel.removeStyleName("downvoted");
		} else {
			if (voteType.get() == VoteType.UPVOTE) {
				voteNumberLabel.addStyleName("upvoted");
				voteNumberLabel.removeStyleName("downvoted");
			} else if (voteType.get() == VoteType.DOWNVOTE) {
				voteNumberLabel.addStyleName("downvoted");
				voteNumberLabel.removeStyleName("upvoted");
			}
		}
	}
}
