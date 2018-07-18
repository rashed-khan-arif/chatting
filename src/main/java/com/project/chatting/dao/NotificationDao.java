package com.project.chatting.dao;

import com.project.chatting.model.Notification;

import java.util.List;

public interface NotificationDao {
    List<Notification> getNotificationsByUser(int userId);

    Notification getNotification(int id);

    boolean sendNotification(Notification notification);
}
