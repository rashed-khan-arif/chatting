package com.project.chatting.model;

import com.project.chatting.core.ParseName;

public class RoomConnection {
    @ParseName("connection_id")
    private int connectionId;
    @ParseName("room_id")
    private int roomId;
    @ParseName("user_id")
    private int userId;
    @ParseName("connection_status")
    private int connectionStatus;

    public int getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(int connectionStatus) {
        this.connectionStatus = connectionStatus;
    }
}