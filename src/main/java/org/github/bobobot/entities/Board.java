package org.github.bobobot.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {

	@Id
	@GeneratedValue
	int ID;

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

	public Board(int ID, @NonNull String shortName, @NonNull String longName) {
		this.ID = ID;
		this.shortName = shortName;
		this.longName = longName;
	}
}
