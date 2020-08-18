package org.github.bobobot.ui.views.layouts;

import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import org.github.bobobot.entities.Reply;

/**
 * Represents the "content" part of a reply.
 * This includes the voting arrows/vote count, the image (if applicable) and the text.
 */
public class ReplyContentLayout extends HorizontalLayout implements View {

	Reply reply;

	public ReplyContentLayout(Reply reply) {
		this.reply = reply;

		VerticalLayout voteLayout = new VerticalLayout();
		Button upvoteButton = new Button();
		Label voteNumberLabel = new Label(String.valueOf(reply.getVotes()));
		Button downvoteButton = new Button();
		voteLayout.addComponents(upvoteButton, voteNumberLabel, downvoteButton);
		addComponent(voteLayout);
		if (reply.hasImage()) {
			//TODO: maybe not ThemeResource?
			Image replyImage = new Image("", new ThemeResource(reply.getImage().get()));
			addComponent(replyImage);
		}
		Label replyContentLabel = new Label(reply.getContent());
		addComponent(replyContentLabel);
		addStyleName("reply-div__content card-body col-12");
	}
}
