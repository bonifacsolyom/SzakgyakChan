package org.github.bobobot.entities;

import java.util.Objects;

public class VoteNotification extends Notification {
	VoteType voteType;

	public VoteNotification(VoteType voteType, boolean read) {
		this.voteType = voteType;
		this.read = read;
	}

	public VoteType getVoteType() {
		return voteType;
	}

	public void setVoteType(VoteType voteType) {
		this.voteType = voteType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		VoteNotification that = (VoteNotification) o;
		return voteType == that.voteType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(voteType);
	}

	public enum VoteType {
		UPVOTE, DOWNVOTE
	}
}
