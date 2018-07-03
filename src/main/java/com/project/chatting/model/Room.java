package com.project.chatting.model;

import com.project.chatting.core.ParseName;

public class Room {
    @ParseName("room_id")
    private int roomId;
    @ParseName("room_name")
    private String roomName;
    @ParseName("active")
    private int active;

    public Room(int roomId, String roomName, int active) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.active = active;
    }

    public Room() {
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
