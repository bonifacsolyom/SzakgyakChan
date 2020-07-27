package org.github.bobobot.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

@Entity
@Data
@NoArgsConstructor
public class VoteNotification extends Notification {
	@Enumerated
	VoteType voteType;

	public VoteNotification(Long id, boolean read, User user, VoteType voteType) {
		super(id, read, user);
		this.voteType = voteType;
	}

	public VoteNotification(@NonNull boolean read, @NonNull User user, VoteType voteType) {
		super(read, user);
		this.voteType = voteType;
	}

	public enum VoteType {
		UPVOTE, DOWNVOTE
	}
}
