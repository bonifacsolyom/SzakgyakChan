package org.github.bobobot.entities;

import java.util.Objects;

public class VoteNotification extends Notification {
	VoteType voteType;

	public VoteNotification(int ID, boolean read, User user, VoteType voteType) {
		super(ID, read, user);
		this.voteType = voteType;
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
		if (!super.equals(o)) return false;
		VoteNotification that = (VoteNotification) o;
		return voteType == that.voteType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), voteType);
	}

	public enum VoteType {
		UPVOTE, DOWNVOTE
	}
}
