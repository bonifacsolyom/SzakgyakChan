package org.github.bobobot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Data
@AllArgsConstructor
public class User {

	@Id
	int ID;

	boolean isAdmin;

	String name;

	String email;

	String passwordHash;

	@OneToMany
	List<Thread> threads = new ArrayList<>();

	@OneToMany
	List<Reply> replies = new ArrayList<>();

	@OneToMany
	List<CommentNotification> commentNotifications = new ArrayList<>();

	@OneToMany
	List<VoteNotification> voteNotifications = new ArrayList<>();


	public User(int ID, boolean isAdmin, String name, String email, String passwordHash) {
		this.ID = ID;
		this.isAdmin = isAdmin;
		this.name = name;
		this.email = email;
		this.passwordHash = passwordHash;
	}

	public User(User user) {
		this.ID = user.ID;
		this.isAdmin = user.isAdmin;
		this.name = user.name;
		this.email = user.email;
		this.passwordHash = user.passwordHash;
		this.threads = user.threads;
		this.replies = user.replies;
		this.commentNotifications = user.commentNotifications;
		this.voteNotifications = user.voteNotifications;
	}


	public List<Notification> getNotifications() {
		return Stream.of(commentNotifications, voteNotifications)
				.flatMap(x -> x.stream())
				.collect(Collectors.toList());
	}

	public void addThread(Thread thread) {
		this.threads.add(thread);
	}


	public void addReply(Reply reply) {
		this.replies.add(reply);
	}

	public void addCommentNotification(CommentNotification notification) {
		this.commentNotifications.add(notification);
	}

	public void addVoteNotification(VoteNotification notification) {
		this.voteNotifications.add(notification);
	}
}
