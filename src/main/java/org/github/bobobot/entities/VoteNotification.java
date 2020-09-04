package org.github.bobobot.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class VoteNotification extends Notification {
	@Enumerated
	VoteType voteType;

	public VoteNotification(Long id, boolean read, @NonNull Reply originalReply, @NonNull VoteType voteType) {
		super(id, read, originalReply);
		this.voteType = voteType;
	}

	public VoteNotification(boolean read, @NonNull Reply originalReply, @NonNull VoteType voteType) {
		super(read, originalReply);
		this.voteType = voteType;
	}

	public VoteNotification(@NonNull Reply originalReply, @NonNull VoteType voteType) {
		super(originalReply);
		this.voteType = voteType;
	}

	/**
	 * This method returns the notification in a string format.
	 * It also makes sure that the text doesn't exceed 60 characters. (Vaadin is inflexible)
	 */
	@Override
	public String getAsText() {
		int textLimit = 50;

		String voteTypeString;
		if (voteType == VoteType.UPVOTE) voteTypeString = "an upvote";
		else if (voteType == VoteType.DOWNVOTE) voteTypeString = "a downvote";
		else voteTypeString = "ERROR";

		String beginningOfText = "Your reply \"";
		String endOfText = "\" received " + voteTypeString + "!";
		return beginningOfText + shortenText(originalReply.getContent(), textLimit - beginningOfText.length() - endOfText.length()) + endOfText;
	}

	public enum VoteType {
		UPVOTE, DOWNVOTE
	}
}
