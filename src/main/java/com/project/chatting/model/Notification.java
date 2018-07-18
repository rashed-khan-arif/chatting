package com.project.chatting.model;

import com.project.chatting.core.ParseName;

import java.util.Date;

public class Notification {
    @ParseName("notify_id")
    private int notifyId;
    @ParseName("to_user")
    private int toUser;
    @ParseName("title")
    private String title;
    @ParseName("content")
    private String content;
    @ParseName("notify_date")
    private Date notifyDate;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(int notifyId) {
        this.notifyId = notifyId;
    }

    public int getToUser() {
        return toUser;
    }

    public void setToUser(int toUser) {
        this.toUser = toUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getNotifyDate() {
        return notifyDate;
    }

    public void setNotifyDate(Date notifyDate) {
        this.notifyDate = notifyDate;
    }
}

