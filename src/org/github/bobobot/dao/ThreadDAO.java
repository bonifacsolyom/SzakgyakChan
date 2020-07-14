package org.github.bobobot.dao;

import org.github.bobobot.entities.Board;
import org.github.bobobot.entities.Thread;

import java.util.ArrayList;
import java.util.Optional;

public interface ThreadDAO {
    Thread create(String title, Board board);
    Optional<Thread> update(int ID, String title, Board board);
    Optional<Thread> select(int ID);
    ArrayList<Thread> list();
    Optional<Thread> delete(int ID);
}
