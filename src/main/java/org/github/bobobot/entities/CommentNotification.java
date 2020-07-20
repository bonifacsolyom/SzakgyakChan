package org.github.bobobot.entities;

import java.util.Objects;

public class CommentNotification extends Notification {
	String replyContent;

	public CommentNotification(int ID, boolean read, User user, String replyContent) {
		super(ID, read, user);
		this.replyContent = replyContent;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CommentNotification that = (CommentNotification) o;
		return replyContent.equals(that.replyContent);
	}

	@Override
	public int hashCode() {
		return Objects.hash(replyContent);
	}
}
