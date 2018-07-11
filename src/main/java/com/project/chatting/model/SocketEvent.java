package com.project.chatting.model;

public enum SocketEvent {

    FriendRequest(1),
    Notification(2),
    TextMessage(3);
    public int value;

    SocketEvent(int i) {
        this.value = i;
    }
}
