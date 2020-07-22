package org.github.bobobot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public abstract class Notification {

	@Id
	@GeneratedValue
	int ID;

	boolean read = false;

	@ManyToOne
	User user;
}
