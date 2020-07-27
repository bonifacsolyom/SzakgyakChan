package org.github.bobobot.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class CommentNotification extends Notification {

	@NonNull
	String replyContent;

	public CommentNotification(Long id, boolean read, User user, String replyContent) {
		super(id, read, user);
		this.replyContent = replyContent;
	}

	public CommentNotification(@NonNull boolean read, @NonNull User user, @NonNull String replyContent) {
		super(read, user);
		this.replyContent = replyContent;
	}
}
