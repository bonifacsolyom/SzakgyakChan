package org.github.bobobot.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
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

	public enum VoteType {
		UPVOTE, DOWNVOTE
	}
}
