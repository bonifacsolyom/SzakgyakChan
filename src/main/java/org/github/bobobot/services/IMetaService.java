package org.github.bobobot.services;

import org.github.bobobot.entities.MetaInfo;
import org.github.bobobot.repositories.IMetaRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface IMetaService {

	public MetaInfo get(String name);

	public MetaInfo update(String name, String value);

	public default MetaInfo update(String name, boolean value) {
		return update(name, Boolean.toString(value));
	}

	public MetaInfo getOrDefault(String name, String value);

	public default MetaInfo getOrDefault(String name, boolean value) {
		return getOrDefault(name, Boolean.toString(value));
	}
}
