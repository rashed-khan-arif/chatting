package com.project.chatting.model;

import com.project.chatting.core.ParseName;

import java.util.List;

public class UserFriend {
    @ParseName("user_friend_id")
    private int userFriendId;
    @ParseName("user_id")
    private int userId;
    @ParseName("friend_id")
    private int friendId;
    private User user;
    private User friend;
    @ParseName("request_status")
    private int requestStatus;

    public int getUserFriendId() {
        return userFriendId;
    }

    public void setUserFriendId(int userFriendId) {
        this.userFriendId = userFriendId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }


    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(int requestStatus) {
        this.requestStatus = requestStatus;
    }

    public FriendRequestStatus getRequestStatus(int value) {
        return FriendRequestStatus.intToEnum(value);
    }
}
