package org.github.bobobot.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "RUser")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	Long id;

	boolean isAdmin;

	@NonNull
	String name;

	@NonNull
	String email;

	@NonNull
	String passwordHash;

	@OneToMany(cascade = CascadeType.ALL)
	List<Thread> threads = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL)
	List<Reply> replies = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL)
	List<CommentNotification> commentNotifications = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL)
	List<VoteNotification> voteNotifications = new ArrayList<>();


	public User(Long id, boolean isAdmin, String name, String email, String passwordHash) {
		this.id = id;
		this.isAdmin = isAdmin;
		this.name = name;
		this.email = email;
		this.passwordHash = passwordHash;
	}

	public User(User user) {
		this.id = user.id;
		this.isAdmin = user.isAdmin;
		this.name = user.name;
		this.email = user.email;
		this.passwordHash = user.passwordHash;
		this.threads = user.threads;
		this.replies = user.replies;
		this.commentNotifications = user.commentNotifications;
		this.voteNotifications = user.voteNotifications;
	}

	public User(boolean isAdmin, @NonNull String name, @NonNull String email, @NonNull String passwordHash) {
		this.isAdmin = isAdmin;
		this.name = name;
		this.email = email;
		this.passwordHash = passwordHash;
	}

	public User(boolean isAdmin, @NonNull String name, @NonNull String email, @NonNull String passwordHash, List<Thread> threads, List<Reply> replies, List<CommentNotification> commentNotifications, List<VoteNotification> voteNotifications) {
		this.isAdmin = isAdmin;
		this.name = name;
		this.email = email;
		this.passwordHash = passwordHash;
		this.threads = threads;
		this.replies = replies;
		this.commentNotifications = commentNotifications;
		this.voteNotifications = voteNotifications;
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
