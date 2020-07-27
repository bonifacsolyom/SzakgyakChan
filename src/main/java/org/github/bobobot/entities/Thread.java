package org.github.bobobot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Thread {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	String title;

	@ManyToOne
	@JsonIgnore
	Board board;

	@ToString.Exclude
	@ManyToOne
	@JsonIgnore
	User user;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "thread", orphanRemoval = true)
	List<Reply> replies = new ArrayList<>();

	public Thread(Long id, String title, Board board, User user) {
		this.id = id;
		this.title = title;
		this.board = board;
		this.user = user;
	}

	public Thread(String title, Board board, User user) {
		this.title = title;
		this.board = board;
		this.user = user;
	}

	public Thread(String title, Board board, User user, List<Reply> replies) {
		this.title = title;
		this.board = board;
		this.user = user;
		this.replies = replies;
	}

	public void addReply(Reply reply) {
		this.replies.add(reply);
	}
}
