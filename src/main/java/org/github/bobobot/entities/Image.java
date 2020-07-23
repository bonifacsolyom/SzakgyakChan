package org.github.bobobot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long ID;

	boolean existing = false;

	String path;

	public Image(boolean existing, String path) {
		this.existing = existing;
		this.path = path;
	}
}
