package org.github.bobobot.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Reply {
	int ID;
	String content;
	LocalDateTime date;
	int votes;
	Image image;
	Thread thread;

	public Reply(int ID, String content, LocalDateTime date, int votes, Image image, Thread thread) {
		this.ID = ID;
		this.content = content;
		this.date = date;
		this.votes = votes;
		this.image = image;
		this.thread = thread;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Reply reply = (Reply) o;
		return ID == reply.ID &&
				votes == reply.votes &&
				content.equals(reply.content) &&
				date.equals(reply.date) &&
				image.equals(reply.image) &&
				thread.equals(reply.thread);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID, content, date, votes, image, thread);
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}
}
