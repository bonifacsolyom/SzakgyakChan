package org.github.bobobot.repositories;

import org.github.bobobot.entities.Board;
import org.github.bobobot.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IImageRepository extends JpaRepository<Image, Integer> {

}
