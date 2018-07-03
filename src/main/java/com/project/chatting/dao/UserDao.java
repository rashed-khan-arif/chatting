package com.project.chatting.dao;

import com.project.chatting.model.User;
import com.project.chatting.model.UserFriend;

import java.util.List;

public interface UserDao {
    List<User> getUsers();

    User getUser(int userId);

    User addUser(User user);

    boolean isEmailUnique(String email);

    List<UserFriend> getFriendsByUserId(int userId);

}
