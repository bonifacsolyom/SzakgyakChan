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
     * @param exists Whether the post this object belongs to has an image attached to it or not.
     * @param path The path of the image, provided that it exists in the first place.
     * @return The created image object.
     */
    Image create(boolean exists, String path);

    /**
     * Updates an image object.
     * @param ID The ID of the image object.
     * @param exists Whether the post this object belongs to has an image attached to it or not.
     * @param path The path of the image, provided that it exists in the first place.
     * @return The updated image object.
     */
    Optional<Image> update(int ID, boolean exists, String path);

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
