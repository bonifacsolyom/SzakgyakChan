package org.github.bobobot.dao;

import org.github.bobobot.entities.Board;

import java.util.ArrayList;
import java.util.Optional;

public interface BoardDAO {
    Board create(String shortName, String longName);
    Optional<Board> update(int ID, String shortName, String longName);
    Optional<Board> select(int ID);
    ArrayList<Board> list();
    Optional<Board> delete(int ID);
}
