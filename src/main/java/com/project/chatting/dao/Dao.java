package com.project.chatting.dao;

public interface Dao {
    AccountDAO getAccountDao();

    UserDao getUserDao();

    IFriendDao getFriendDao();

    NotificationDao getNotificationDao();
}
