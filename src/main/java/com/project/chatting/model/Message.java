package com.project.chatting.model;

import com.project.chatting.core.ParseName;

import java.util.Date;

public class Message {
    @ParseName("message_id")
    private int messageId;
    @ParseName("room_id")
    private int roomId;
    @ParseName("user_id")
    private int userId;
    @ParseName("message_content")
    private String messageContent;
    @ParseName("msg_date")
    private Date msgDate;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
}
