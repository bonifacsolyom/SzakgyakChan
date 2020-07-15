package org.github.bobobot.dao;

import org.github.bobobot.entities.Board;
import org.github.bobobot.entities.Thread;

import java.util.ArrayList;
import java.util.Optional;

/**
 * This interface defines a DAO of a thread.
 */
public interface ThreadDAO {
    /**
     * Creates a thread.
     * @param title The title of the thread.
     * @param board The board this thread belongs to.
     * @return The created thread.
     */
    Thread create(String title, Board board);

    /**
     * Updates a thread.
     * @param ID The ID of the thread to be updated.
     * @param title The title of th thread.
     * @param board The board this thread belongs to.
     * @return The updated thread.
     */
    Optional<Thread> update(int ID, String title, Board board);

    /**
     * Selects a thread by its ID.
     * @param ID The ID of the thread to be selected.
     * @return The selected thread wrapped in an optional.
     */
    Optional<Thread> select(int ID);

    /**
     * Lists all threads.
     * @return A list of all existing threads.
     */
    ArrayList<Thread> list();

    /**
     * Deletes a thread.
     * @param ID The ID of the thread to be deleted.
     * @return The deleted thread, wrapped in an optional.
     */
    Optional<Thread> delete(int ID);
}
