package org.github.bobobot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Notification {

	@Id
	int ID;

	boolean read = false;

	@ManyToOne
	User user;
}
