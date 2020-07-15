package org.github.bobobot.dao;

import org.github.bobobot.entities.Image;

import java.util.ArrayList;
import java.util.Optional;

/**
 * This interface defines a DAO of an image.
 */
public interface ImageDAO {

    /**
     * Creates an image object.
     * @param image The image object to be created.
     * @return The created image object.
     */
    Image create(Image image);

    /**
     * Updates an image object.
     * @param image The image object ot be updated.
     * @return The updated image object.
     */
    Optional<Image> update(Image image);

    /**
     * Selects an image object by its ID.
     * @param ID The ID of the image object to be selected.
     * @return The selected image object wrapped in an optional.
     */
    Optional<Image> select(int ID);

    /**
     * Deletes an image object.
     * @param ID The ID of the image object to be deleted.
     * @return The deleted image object, wrapped in an optional.
     */
    Optional<Image> delete(int ID);
}
