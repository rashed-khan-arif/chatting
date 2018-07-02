package com.project.chatting.model;

import com.project.chatting.core.ParseName;

public class UserFriend {
    @ParseName("user_friend_id")
    private int userFriendId;
    @ParseName("user_id")
    private int userId;
    @ParseName("friend_id")
    private int friendId;

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
}
