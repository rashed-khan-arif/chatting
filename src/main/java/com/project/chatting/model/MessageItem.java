package com.project.chatting.model;

public class MessageItem {
    private int roomId;
    private int fromUserId;
    private int toUserId;
    private String content;

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int userId) {
        this.fromUserId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }
}
