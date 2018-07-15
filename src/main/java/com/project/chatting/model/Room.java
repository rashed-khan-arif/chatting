package com.project.chatting.model;

import com.project.chatting.core.ParseName;

import java.util.List;

public class Room {
    @ParseName("room_id")
    private int roomId;
    @ParseName("room_name")
    private String roomName;
    @ParseName("active")
    private int active;
    @ParseName("created_by")
    private int createdBy;
    private List<Member> members;

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

    public List<Member> getMembers() {
        return members;
    }

    public boolean isUserExitsInRoom(int userId) {
        for (Member rc : members) {
            if (rc.getUserId() == userId)
                return true;
        }
        return false;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
}
