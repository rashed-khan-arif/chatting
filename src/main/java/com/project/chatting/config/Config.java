package com.project.chatting.config;

import com.project.chatting.model.FriendRequestStatus;

public class Config {
    private static String hostUrl = "http://localhost:8080/";
    public static String chatUrl = "ws://localhost:8080/".concat("chat/");
    public static String addContactUrl = hostUrl.concat("addContact");
    public static String getContactsUrl = hostUrl.concat("getContactList?userId=");
    public static String getUserUrl = hostUrl.concat("user?userId=");
    public static String friendRequestUrl = hostUrl.concat("getFriendRequest?userId=");
    public static String updateFriendReq = hostUrl.concat("updateFriendRequest");
    public static String getNotificationsUrl = hostUrl.concat("getNotifications?userId=");


}
