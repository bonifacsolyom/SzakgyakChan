package org.github.bobobot.services;

import org.github.bobobot.entities.Thread;

import java.util.ArrayList;

public interface ThreadService {

    /**
     * Creates or updates a thread.
     * @param title The title of the thread.
     * @return The created/updated thread.
     */
    Thread createOrUpdate(int ID, String title);

    /**
     * Lists all threads.
     * @return A list of all threads.
     */
    ArrayList<Thread> list();

    /**
     * Finds a thread by its ID.
     * @param ID The ID of the thread to be found
     * @return The found thread, wrapped in an optional.
     */
    Thread findById(int ID);

    /**
     * Deletes a thread.
     * @param ID The ID of the thread to be deleted.
     */
    void delete(int ID);
}
