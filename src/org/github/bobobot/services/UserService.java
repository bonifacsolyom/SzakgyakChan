package org.github.bobobot.services;

import org.github.bobobot.entities.User;

import java.util.ArrayList;

public interface UserService {
    //TODO: Jelszóhash helyett ez még csak jelszót kapjon
    User createOrUpdate(boolean isAdmin, String name, String email, String passwordHash);
    ArrayList<User> list();
    User findById(int ID);
    User findByUsername(String name);
    User findByEmail(String email);
    void delete(int ID);
}
