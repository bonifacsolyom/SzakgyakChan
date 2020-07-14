package org.github.bobobot.services;

import org.github.bobobot.entities.Board;

import java.util.ArrayList;

public interface BoardService {
    Board createOrUpdate(String shortName, String longName);
    ArrayList<Board> list();
    Board findById(int ID);
    Board findByShortName(String shortName);
    void delete(int ID);
}
