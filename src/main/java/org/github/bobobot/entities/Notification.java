package org.github.bobobot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public abstract class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long ID;

	boolean read = false;

	@ManyToOne
	@NonNull
	@JsonIgnore
	User user;

	public Notification(boolean read, @NonNull User user) {
		this.read = read;
		this.user = user;
	}
}
