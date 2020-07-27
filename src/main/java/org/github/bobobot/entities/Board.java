package org.github.bobobot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@NonNull
	String shortName;

	@NonNull
	String longName;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "board")
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
}
