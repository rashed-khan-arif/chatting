package com.project.chatting.dao;

import com.project.chatting.model.User;

public interface AccountDAO {
    boolean createAccount(User user);

    boolean updateAccountInfo(User user);

    User getUser(int id);

    User checkLoginInfo(String email, String password);
}
