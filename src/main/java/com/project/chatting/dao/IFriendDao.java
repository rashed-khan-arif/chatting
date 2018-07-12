package com.project.chatting.dao;

import com.project.chatting.model.FriendRequestStatus;
import com.project.chatting.model.UserFriend;

import java.util.List;

public interface IFriendDao {
    boolean isRequestAlreadySent(int userId, int friendId);

    UserFriend sendRequest(UserFriend userFriend);

    UserFriend updateRequestStatus(int userFriendId, FriendRequestStatus status);

    List<UserFriend> getFriendsByUserId(int userId);

    UserFriend getUserFriendRequest(int frnRqId);

    List<UserFriend> getFriendsRequests(int userId, FriendRequestStatus friendRequestStatus);
}
