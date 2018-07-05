package com.project.chatting.dao;

import com.project.chatting.model.FriendRequestStatus;
import com.project.chatting.model.UserFriend;

import java.util.List;

public interface IFriendDao {
    boolean isRequestAlreadySent(int userId, int friendId);

    boolean sendRequest(UserFriend userFriend);

    List<UserFriend> getFriendsByUserId(int userId);

    List<UserFriend> getFriendsRequests(int userId,FriendRequestStatus friendRequestStatus);
}
