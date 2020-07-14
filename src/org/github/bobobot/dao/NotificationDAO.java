package org.github.bobobot.dao;

import org.github.bobobot.entities.Notification;

import java.util.ArrayList;

public interface NotificationDAO {
    Notification create();
    Notification update();
    Notification select(int ID);
    ArrayList<Notification> list();
    Notification delete();
}
