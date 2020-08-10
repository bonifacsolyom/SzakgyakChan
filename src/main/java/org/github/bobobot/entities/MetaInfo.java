package org.github.bobobot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetaInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "meta_Sequence")
	@SequenceGenerator(name = "meta_Sequence", sequenceName = "META_SEQ", allocationSize = 1)
	Long id;

	String name;
	String value;

	public MetaInfo(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public boolean getValueAsBoolean() {
		return Boolean.parseBoolean(value);
	}
}
