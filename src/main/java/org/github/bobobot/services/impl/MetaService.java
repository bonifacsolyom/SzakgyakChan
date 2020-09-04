package org.github.bobobot.services.impl;

import org.github.bobobot.entities.MetaInfo;
import org.github.bobobot.repositories.IMetaRepository;
import org.github.bobobot.services.IMetaService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class MetaService implements IMetaService {

	@Autowired
	IMetaRepository repository;

	private MetaInfo getMetaIfPresent(Optional<MetaInfo> meta) {
		if (!meta.isPresent()) {
			throw new IllegalArgumentException("MetaInfo was not found!");
		}
		return meta.get();
	}

	@Override
	public MetaInfo get(String name) {
		return getMetaIfPresent(repository.findByName(name));
	}

	@Override
	public MetaInfo update(String name, String value) {
		MetaInfo metaInfo = get(name);
		metaInfo.setValue(value);
		return repository.save(metaInfo);
	}

	@Override
	public MetaInfo getOrDefault(String name, String value) {
		Optional<MetaInfo> optionalMeta = repository.findByName(name);
		if (!optionalMeta.isPresent()) {
			repository.save(new MetaInfo(name, value));
		}
		return get(name);
	}
}
