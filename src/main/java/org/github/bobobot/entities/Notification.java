package org.github.bobobot.entities;

import java.util.Objects;

public abstract class Notification {
	int ID;
	boolean read = false;
	User user;

	public Notification(int ID, boolean read, User user) {
		this.ID = ID;
		this.read = read;
		this.user = user;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Notification that = (Notification) o;
		return ID == that.ID &&
				read == that.read &&
				user.equals(that.user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID, read, user);
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
