package org.github.bobobot.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
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

	public CommentNotification(@NonNull boolean read, @NonNull Reply originalReply, @NonNull Reply otherUsersReply) {
		super(read, originalReply);
		this.otherUsersReply = otherUsersReply;
	}
}
