package org.github.bobobot.repositories;

import org.github.bobobot.entities.MetaInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IMetaRepository extends JpaRepository<MetaInfo, Long> {

	Optional<MetaInfo> findByName(String name);
}
