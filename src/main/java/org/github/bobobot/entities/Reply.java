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
	@GeneratedValue
	int ID;

	String content;

	LocalDateTime date;

	int votes;

	@OneToOne
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
