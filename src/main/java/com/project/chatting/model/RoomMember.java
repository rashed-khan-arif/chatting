package com.project.chatting.model;

import com.project.chatting.core.ParseName;

import java.util.Date;

public class RoomMember {
    @ParseName("room_member_id")
    private int roomMemberId;
    @ParseName("room_id")
    private int roomId;
    @ParseName("user_id")
    private int userId;
    @ParseName("join_date")
    private Date joinDate;

    public int getRoomMemberId() {
        return roomMemberId;
    }

    public void setRoomMemberId(int roomMemberId) {
        this.roomMemberId = roomMemberId;
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

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }
}

