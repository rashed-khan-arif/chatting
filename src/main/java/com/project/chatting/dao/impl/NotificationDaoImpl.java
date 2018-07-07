package com.project.chatting.dao.impl;

import com.project.chatting.dao.NotificationDao;
import com.project.chatting.model.Notification;

import java.util.List;

public class NotificationDaoImpl implements NotificationDao {
    @Override
    public List<Notification> getNotificationsByUser(int userId) {
        return null;
    }

    @Override
    public Notification getNotification(int id) {
        return null;
    }

    @Override
    public boolean sendNotification(Notification notification) {
        return false;
    }
}
