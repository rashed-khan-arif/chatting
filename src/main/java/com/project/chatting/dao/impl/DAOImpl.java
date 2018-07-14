package com.project.chatting.dao.impl;

import com.project.chatting.dao.*;

public class DAOImpl implements Dao {
    @Override
    public AccountDAO getAccountDao() {
        return new AccountDAOImpl();
    }

    @Override
    public UserDao getUserDao() {
        return new UserDaoImpl();
    }

    @Override
    public IFriendDao getFriendDao() {
        return new FriendDaoImpl();
    }

    @Override
    public NotificationDao getNotificationDao() {
        return new NotificationDaoImpl();
    }

    @Override
    public MessageDao getMessageDao() {
        return new MessageDaoImpl();
    }
}
