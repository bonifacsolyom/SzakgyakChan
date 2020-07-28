package org.github.bobobot.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	String content;

	LocalDateTime date;

	int votes;

	@ManyToOne
	Thread thread;

	@ManyToOne
	User user;

	String image;

	public Reply(String content, LocalDateTime date, int votes, Thread thread, User user, String image) {
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

	Optional<String> getImage() {
		return Optional.ofNullable(image);
	}
}
