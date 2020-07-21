package org.github.bobobot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
public class Reply {
	@Id
	int ID;

	String content;

	LocalDateTime date;

	int votes;

	Image image;

	@ManyToOne
	Thread thread;

	@ManyToOne
	User user;

	public int upvote() {
		return ++votes;
	}

	public int downvote() {
		return --votes;
	}

}
