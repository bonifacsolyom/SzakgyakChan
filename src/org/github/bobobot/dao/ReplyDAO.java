package org.github.bobobot.dao;

import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public interface ReplyDAO {
    Reply create(String content, LocalDateTime date, int votes, Thread thread);
    Optional<Reply> update(int ID, String content, LocalDateTime date, int votes, Thread thread);
    Optional<Reply> select(int ID);
    ArrayList<Reply> list();
    Optional<Reply> delete(int ID);
}
