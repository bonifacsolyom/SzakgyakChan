package org.github.bobobot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long ID;

	String content;

	LocalDateTime date;

	int votes;

	@ManyToOne
	Thread thread;

	@ManyToOne
	User user;

	@OneToOne(cascade = CascadeType.ALL)
	Image image;

	public Reply(String content, LocalDateTime date, int votes, Thread thread, User user, Image image) {
		this.content = content;
		this.date = date;
		this.votes = votes;
		this.image = image;
		this.thread = thread;
		this.user = user;
	}

	public Reply(String content, LocalDateTime date, int votes, Thread thread, User user) {
		this.content = content;
		this.date = date;
		this.votes = votes;
		this.thread = thread;
		this.user = user;
	}

	public int upvote() {
		return ++votes;
	}

	public int downvote() {
		return --votes;
	}
}
