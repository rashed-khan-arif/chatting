package com.project.chatting.model;

import com.project.chatting.core.ParseName;

import java.util.Date;

public class Message {
    @ParseName("message_id")
    private int messageId;
    @ParseName("room_id")
    private int roomId;
    @ParseName("message_content")
    private String messageContent;
    @ParseName("msg_date")
    private Date msgDate;
    private Room room;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }


    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Date getMsgDate() {
        return msgDate;
    }

    public void setMsgDate(Date msgDate) {
        this.msgDate = msgDate;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

}
