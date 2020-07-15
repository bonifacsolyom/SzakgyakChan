package org.github.bobobot.entities;

import java.util.ArrayList;
import java.util.Objects;

public class User {
	int ID;
	boolean isAdmin;
	String name;
	String email;
	String passwordHash;
	ArrayList<Thread> threads = new ArrayList<>();
	ArrayList<Reply> replies = new ArrayList<>();
	ArrayList<Notification> notifications = new ArrayList<>();

	public User(int ID, boolean isAdmin, String name, String email, String passwordHash, ArrayList<Thread> threads, ArrayList<Reply> replies, ArrayList<Notification> notifications) {
		this.ID = ID;
		this.isAdmin = isAdmin;
		this.name = name;
		this.email = email;
		this.passwordHash = passwordHash;
		this.threads = threads;
		this.replies = replies;
		this.notifications = notifications;
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
		return Objects.hash(ID, isAdmin, name, email, passwordHash, threads, replies, notifications);
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public ArrayList<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(ArrayList<Notification> notifications) {
		this.notifications = notifications;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean admin) {
		isAdmin = admin;
	}

	public ArrayList<Thread> getThreads() {
		return threads;
	}

	public void setThreads(ArrayList<Thread> threads) {
		this.threads = threads;
	}

	public ArrayList<Reply> getReplies() {
		return replies;
	}

	public void setReplies(ArrayList<Reply> replies) {
		this.replies = replies;
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
