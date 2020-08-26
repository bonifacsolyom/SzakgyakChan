package org.github.bobobot.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Thread {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "thread_Sequence")
	@SequenceGenerator(name = "thread_Sequence", sequenceName = "THREAD_SEQ", allocationSize = 1)
	Long id;

	String title;

	@ToString.Exclude
	@ManyToOne
	Board board;

	@ToString.Exclude
	@ManyToOne
	User user;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("id")
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
