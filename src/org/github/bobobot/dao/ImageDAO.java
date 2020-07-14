package org.github.bobobot.dao;

import org.github.bobobot.entities.Image;

import java.util.ArrayList;
import java.util.Optional;

public interface ImageDAO {
    Image create(boolean exists, String path);
    Optional<Image> update(int ID, boolean exists, String path);
    Optional<Image> select(int ID);
    Optional<Image> delete(int ID);
}
