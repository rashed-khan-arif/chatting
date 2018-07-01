package com.project.chatting.dao.impl;

import com.project.chatting.dao.AccountDAO;
import com.project.chatting.dao.Dao;

public class DAOImpl implements Dao {
    @Override
    public AccountDAO getAccountDao() {
        return new AccountDAOImpl();
    }
}
