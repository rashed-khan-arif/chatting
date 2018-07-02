package com.project.chatting.dao.impl;

import com.project.chatting.dao.AccountDAO;
import com.project.chatting.dao.Dao;
import com.project.chatting.dao.UserDao;

public class DAOImpl implements Dao {
    @Override
    public AccountDAO getAccountDao() {
        return new AccountDAOImpl();
    }

    @Override
    public UserDao getUserDao() {
        return new UserDaoImpl();
    }
}
