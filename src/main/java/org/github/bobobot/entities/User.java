package org.github.bobobot.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class User {
	int ID;
	boolean isAdmin;
	String name;
	String email;
	String passwordHash;
	List<Thread> threads = new ArrayList<>();
	List<Reply> replies = new ArrayList<>();
	List<CommentNotification> commentNotifications = new ArrayList<>();
	List<VoteNotification> voteNotifications = new ArrayList<>();

	public User(int ID, boolean isAdmin, String name, String email, String passwordHash, List<Thread> threads, List<Reply> replies, List<CommentNotification> commentNotifications, List<VoteNotification> voteNotifications) {
		this.ID = ID;
		this.isAdmin = isAdmin;
		this.name = name;
		this.email = email;
		this.passwordHash = passwordHash;
		this.threads = threads;
		this.replies = replies;
		this.commentNotifications = commentNotifications;
		this.voteNotifications = voteNotifications;
	}

	public User(int ID, boolean isAdmin, String name, String email, String passwordHash) {
		this.ID = ID;
		this.isAdmin = isAdmin;
		this.name = name;
		this.email = email;
		this.passwordHash = passwordHash;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return ID == user.ID &&
				isAdmin == user.isAdmin &&
				name.equals(user.name) &&
				email.equals(user.email) &&
				passwordHash.equals(user.passwordHash);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID, isAdmin, name, email, passwordHash, threads, replies);
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public List<CommentNotification> getCommentNotifications() {
		return commentNotifications;
	}

	public void setCommentNotifications(List<CommentNotification> commentNotifications) {
		this.commentNotifications = commentNotifications;
	}

	public void addCommentNotification(CommentNotification notification) {
		this.commentNotifications.add(notification);
	}

	public List<VoteNotification> getVoteNotifications() {
		return voteNotifications;
	}

	public void setVoteNotifications(List<VoteNotification> voteNotifications) {
		this.voteNotifications = voteNotifications;
	}

	public void addVoteNotification(VoteNotification notification) {
		this.voteNotifications.add(notification);
	}

	public List<Notification> getNotifications() {
		return Stream.of(commentNotifications, voteNotifications)
				.flatMap(x -> x.stream())
				.collect(Collectors.toList());
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean admin) {
		isAdmin = admin;
	}

	public List<Thread> getThreads() {
		return threads;
	}

	public void setThreads(List<Thread> threads) {
		this.threads = threads;
	}

	public void addThread(Thread thread) {
		this.threads.add(thread);
	}

	public List<Reply> getReplies() {
		return replies;
	}

	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}

	public void addReply(Reply reply) {
		this.replies.add(reply);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
}
