package org.github.bobobot.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.github.bobobot.entities.Reply;

import java.time.format.DateTimeFormatter;

@SpringView(name = ReplyLayout.name)
public class ReplyLayout extends VerticalLayout implements View {
	public static final String name = "replyLayout";

	Reply reply;

	public ReplyLayout(Reply reply) {
		this.reply = reply;
		setSpacing(false);
		/*
		The header contains:
			-The poster's name
			-The date of the reply
			-A delete button
		The post contains:
			-Vote buttons and number
			-The image (if applicable)
			-The text content of the reply
		 */

		// @formatter:off


		//The header
		HorizontalLayout headerLayout = new HorizontalLayout();
			Label usernameLabel = new Label(reply.getUser().getName());
			Label dateLabel = new Label(reply.getDate().format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm:ss")));
			//TODO: only delete if logged in or admin
			//TODO: icon instead of text
			Button deleteButton = new Button("delete");
		headerLayout.addComponents(usernameLabel, dateLabel, deleteButton);
		headerLayout.addStyleName("reply-div__header card-header");

		//The content of the reply
		//Don't even try to comprehend this
		HorizontalLayout contentLayout = new HorizontalLayout();
			VerticalLayout voteLayout = new VerticalLayout();
				Button upvoteButton = new Button();
				Label voteNumberLabel = new Label(String.valueOf(reply.getVotes()));
				Button downvoteButton = new Button();
			voteLayout.addComponents(upvoteButton, voteNumberLabel, downvoteButton);
		contentLayout.addComponent(voteLayout);
			if (reply.hasImage()) {
				//TODO: maybe not ThemeResource?
				Image replyImage = new Image("", new ThemeResource(reply.getImage().get()));
				contentLayout.addComponent(replyImage);
			}
			Label replyContentLabel = new Label(reply.getContent());
		contentLayout.addComponent(replyContentLabel);
		addComponents(headerLayout, contentLayout);
		contentLayout.addStyleName("reply-div__content card-body");

		addStyleName("card");

		// @formatter:on
	}
}
