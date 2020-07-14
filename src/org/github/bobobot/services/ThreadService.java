package org.github.bobobot.services;

import org.github.bobobot.entities.Thread;

import java.util.ArrayList;

public interface ThreadService {
    Thread createOrUpdate(String title);
    ArrayList<Thread> list();
    Thread findById(int ID);
    void delete(int ID);
}
