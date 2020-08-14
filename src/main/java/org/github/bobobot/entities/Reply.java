package org.github.bobobot.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "reply_Sequence")
	@SequenceGenerator(name = "reply_Sequence", sequenceName = "REPLY_SEQ", allocationSize = 1)
	Long id;

	String content;

	@Column(name = "postDate")
	LocalDateTime date;

	int votes;

	@ToString.Exclude
	@ManyToOne
	Thread thread;

	@ToString.Exclude
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

	public Optional<String> getImage() {
		return Optional.ofNullable(image);
	}

	public boolean hasImage() {
		return image != null;
	}
}
