package org.github.bobobot.repositories;

import org.github.bobobot.entities.Board;
import org.github.bobobot.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IImageRepository extends JpaRepository<Image, Integer> {

}
