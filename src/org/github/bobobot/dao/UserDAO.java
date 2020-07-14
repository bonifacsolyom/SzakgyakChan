package org.github.bobobot.dao;

import org.github.bobobot.entities.Notification;
import org.github.bobobot.entities.Reply;
import org.github.bobobot.entities.Thread;
import org.github.bobobot.entities.User;

import java.util.ArrayList;
import java.util.Optional;

public interface UserDAO {
	User create(boolean isAdmin, String name, String email, String passwordHash);
	Optional<User> update(int ID, boolean isAdmin, String name, String email, String passwordHash, ArrayList<Thread> threads, ArrayList<Reply> replies, ArrayList<Notification> notifications);
	Optional<User> select(int ID);
	Optional<User> delete(int ID);
}
