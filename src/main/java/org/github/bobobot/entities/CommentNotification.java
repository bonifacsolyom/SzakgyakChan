package org.github.bobobot.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class CommentNotification extends Notification {

	@NonNull
	@ManyToOne
	Reply otherUsersReply;

	public CommentNotification(Long id, boolean read, Reply originalReply, Reply otherUsersReply) {
		super(id, read, originalReply);
		this.otherUsersReply = otherUsersReply;
	}

	public CommentNotification(boolean read, @NonNull Reply originalReply, @NonNull Reply otherUsersReply) {
		super(read, originalReply);
		this.otherUsersReply = otherUsersReply;
	}

	/**
	 * This method returns the notification in a string format.
	 * It also makes sure that the text doesn't exceed 60 characters. (Vaadin is inflexible)
	 */
	@Override
	public String getAsText() {
		int textLimit = 50;
		String restOfTheText = otherUsersReply.getUser().getName() + " replied to your comment: \"";
		return restOfTheText + shortenText(otherUsersReply.getContent(), textLimit - restOfTheText.length()) + "\"";
	}
}
