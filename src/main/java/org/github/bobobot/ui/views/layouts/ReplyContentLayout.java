package org.github.bobobot.ui.views.layouts;

import com.vaadin.navigator.View;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
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

import java.io.File;
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
		Image upvoteButton = new Image(null, new ThemeResource("images/upvote.png"));
		upvoteButton.addStyleName("upvote-button");
		Label voteNumberLabel = new Label(String.valueOf(reply.getVoteCount()));
		voteNumberLabel.addStyleName("not-voted");
		Image downvoteButton = new Image(null, new ThemeResource("images/downvote.png"));
		downvoteButton.addStyleName("downvote-button");
		voteLayout.addComponents(upvoteButton, voteNumberLabel, downvoteButton);

		voteLayout.setComponentAlignment(upvoteButton, Alignment.MIDDLE_CENTER);
		voteLayout.setComponentAlignment(voteNumberLabel, Alignment.MIDDLE_CENTER);
		voteLayout.setComponentAlignment(downvoteButton, Alignment.MIDDLE_CENTER);

		setVoteColor(voteLayout);

		PermissionHandler.restrictComponentToLoggedInUsers(upvoteButton, allow -> {
			if (!allow) upvoteButton.addStyleName("disabled-image-button");
			else upvoteButton.removeStyleName("disabled-image-button");
			upvoteButton.setEnabled(allow);
		});
		PermissionHandler.restrictComponentToLoggedInUsers(downvoteButton, allow -> {
			if (!allow) downvoteButton.addStyleName("disabled-image-button");
			else downvoteButton.removeStyleName("disabled-image-button");
			downvoteButton.setEnabled(allow);
		});

		//disgusting but needed for the lambda method to be Transactional
		upvoteButton.addClickListener(clickEvent -> {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(@NonNull TransactionStatus transactionStatus) {
					log.info("Trying to upvote");
					replyService.vote(userId, reply.getId(), VoteType.UPVOTE);
					voteNumberLabel.setValue(String.valueOf(replyService.findById(reply.getId()).getVoteCount()));
					setVoteColor(voteLayout);
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
					setVoteColor(voteLayout);
				}
			});
		});

		addComponent(voteLayout);
		if (reply.hasImage()) {
			Image replyImage = new Image(null, new FileResource(new File(reply.getImage().get())));
			addComponent(replyImage);
			replyImage.addStyleName("reply-image");
		}
		Label replyContentLabel = new Label(reply.getContent());
		addComponent(replyContentLabel);
		addStyleName("reply-div__content card-body col-12");

		return this;
	}

	private void setVoteColor(VerticalLayout voteLayout) {
		Optional<VoteType> userVoteType = replyService.getUserVote(userId, reply.getId());
		setVoteColor(voteLayout, userVoteType);
	}

	//kicsit hosszú, kicsit csúnya, de az enyém
	private void setVoteColor(VerticalLayout voteLayout, Optional<VoteType> voteType) {
		Image upvote = (Image) voteLayout.getComponent(0);
		Label voteNumberLabel = (Label)voteLayout.getComponent(1);
		Image downvote = (Image) voteLayout.getComponent(2);
		if (!voteType.isPresent()) {
			voteNumberLabel.addStyleName("not-voted");
			voteNumberLabel.removeStyleName("upvoted");
			voteNumberLabel.removeStyleName("downvoted");
			upvote.removeStyleNames("voted-image-button");
			downvote.removeStyleNames("voted-image-button");
		} else {
			if (voteType.get() == VoteType.UPVOTE) {
				upvote.addStyleName("voted-image-button");
				downvote.removeStyleName("voted-image-button");
				voteNumberLabel.addStyleName("upvoted");
				voteNumberLabel.removeStyleName("downvoted");
				voteNumberLabel.removeStyleName("not-voted");
			} else if (voteType.get() == VoteType.DOWNVOTE) {
				downvote.addStyleName("voted-image-button");
				upvote.removeStyleName("voted-image-button");
				voteNumberLabel.addStyleName("downvoted");
				voteNumberLabel.removeStyleName("upvoted");
				voteNumberLabel.removeStyleName("not-voted");
			}
		}
	}
}
