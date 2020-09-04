package org.github.bobobot.services;

import org.github.bobobot.entities.MetaInfo;

public interface IMetaService {

	MetaInfo get(String name);

	MetaInfo update(String name, String value);

	default MetaInfo update(String name, boolean value) {
		return update(name, Boolean.toString(value));
	}

	MetaInfo getOrDefault(String name, String value);

	default MetaInfo getOrDefault(String name, boolean value) {
		return getOrDefault(name, Boolean.toString(value));
	}
}
