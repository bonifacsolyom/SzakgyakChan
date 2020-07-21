package org.github.bobobot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
public class Image {

	@Id
	int ID;

	boolean exists;

	String path;
}
