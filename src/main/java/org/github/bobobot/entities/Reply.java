package org.github.bobobot.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Reply {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "reply_Sequence")
	@SequenceGenerator(name = "reply_Sequence", sequenceName = "REPLY_SEQ", allocationSize = 1)
	Long id;

	String content;

	@Column(name = "postDate")
	LocalDateTime date;

	@ToString.Exclude
	@ManyToOne
	Thread thread;

	@ToString.Exclude
	@ManyToOne
	User user;

	String image;

	@ElementCollection(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "reply_id")
	private Set<Long> usersUpvoted = new HashSet<>();

	@ElementCollection(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "reply_id")
	private Set<Long> usersDownvoted = new HashSet<>();

	public Reply(String content, LocalDateTime date, Thread thread, User user, String image) {
		this.content = content;
		this.date = date;
		this.thread = thread;
		this.user = user;
		this.image = image;
	}

	public Reply(String content, LocalDateTime date, Thread thread, User user) {
		this.content = content;
		this.date = date;
		this.thread = thread;
		this.user = user;
	}

	public int upvote(Long userId) {
		usersDownvoted.remove(userId);
		if (usersUpvoted.contains(userId)) {
			log.warn("User already voted! Removing upvote...");
			usersUpvoted.remove(userId);
		} else {
			usersUpvoted.add(userId);
		}
		return getVoteCount();
	}

	public int downvote(Long userId) {
		usersUpvoted.remove(userId);
		if (usersDownvoted.contains(userId)) {
			log.warn("User already voted! Removing Downvote...");
			usersDownvoted.remove(userId);
		} else {
			usersDownvoted.add(userId);
		}
		return getVoteCount();
	}

	public Optional<String> getImage() {
		return Optional.ofNullable(image);
	}

	public boolean hasImage() {
		return image != null;
	}

	public int getVoteCount() {
		return usersUpvoted.size() - usersDownvoted.size();
	}

	public boolean checkIfUserUpvoted(Long userId) {
		return usersUpvoted.contains(userId);
	}

	public boolean checkIfUserDownvoted(Long userId) {
		return usersDownvoted.contains(userId);
	}

	public Reply setDebugVoteCount(int votes) {
		int voteDiff = votes - getVoteCount();
		if (voteDiff > 0) {
			Stream.iterate(0, i -> i + 1).limit(voteDiff).forEach(number -> usersUpvoted.add(Long.valueOf(number)));
		} else {
			Stream.iterate(0, i -> i + 1).limit(Math.abs(voteDiff)).forEach(number -> usersDownvoted.add(Long.valueOf(number)));
		}
		return this;
	}

	@PreRemove
	private void removeReplyFromThread() {
		log.info("trying to remove reply from thread");
		getThread().getReplies().remove(this);
		log.info("successfully removed reply from thread");
		log.info("trying to remove reply from user");
		user.getReplies().remove(this);
		log.info("successfully removed reply from user");
	}
}
