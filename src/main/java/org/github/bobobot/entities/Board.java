package org.github.bobobot.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ", allocationSize = 1)
	Long id;

	@NonNull
	String shortName;

	@NonNull
	String longName;

	@OneToMany(cascade = CascadeType.ALL)
	List<Thread> threads = new ArrayList<>();

	public Board(@NonNull String shortName, @NonNull String longName) {
		this.shortName = shortName;
		this.longName = longName;
	}

	public Board(Long id, @NonNull String shortName, @NonNull String longName) {
		this.id = id;
		this.shortName = shortName;
		this.longName = longName;
	}

	public void addThread(Thread thread) {
		threads.add(thread);
	}
}
