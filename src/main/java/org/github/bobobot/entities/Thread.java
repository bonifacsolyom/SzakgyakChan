package org.github.bobobot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Thread {

	@Id
	@GeneratedValue
	int ID;

	String title;

	@ManyToOne
	Board board;

	@ManyToOne
	User user;

	@OneToMany
	List<Reply> replies = new ArrayList<>();

	public Thread(int ID, String title, Board board, User user) {
		this.ID = ID;
		this.title = title;
		this.board = board;
		this.user = user;
	}

	public void addReply(Reply reply) {
		this.replies.add(reply);
	}
}
