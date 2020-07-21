package org.github.bobobot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
public class Board {

	@Id
	@NonNull
	int ID;

	@NonNull
	String shortName;

	@NonNull
	String longName;

	@OneToMany
	List<Thread> threads = new ArrayList<>();
}
