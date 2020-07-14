package org.github.bobobot.services;

import org.github.bobobot.entities.Reply;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface ReplyService {
    Reply create(String content, LocalDateTime date, int votes);
    Reply update(int ID, String content, LocalDateTime date, int votes);
    ArrayList<Reply> list();
    Reply findById(int ID);
    ArrayList<Reply> listByThreadId(int threadID);
    void delete(int ID);
}
