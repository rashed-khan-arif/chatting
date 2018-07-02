package com.project.chatting.dao;

import com.project.chatting.model.User;

import java.util.List;

public interface UserDao {
    List<User> getUsers();

    User getUser(int userId);

    User addUser(User user);

    List<User> getFriendsByUserId(int userId);

}
