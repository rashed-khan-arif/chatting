package com.project.chatting.dao.impl;

import com.project.chatting.core.Database;
import com.project.chatting.dao.MessageDao;
import com.project.chatting.model.Message;
import com.project.chatting.model.Room;
import com.project.chatting.model.Member;

import java.sql.Connection;
import java.util.List;

public class MessageDaoImpl implements MessageDao {
    Connection connection;

    public MessageDaoImpl() {
        this.connection = Database.getConnection();
    }

    @Override
    public Room isItP2P(int from, int to) {
        return null;
    }

    @Override
    public Room createRoom(Room room) {

    }

    @Override
    public void addMemberToRoom(Member member) {

    }

    @Override
    public Room getRoom(int roomId) {
        return null;
    }

    @Override
    public Message addMessage(Message msg) {
        return null;
    }

    @Override
    public List<Message> getMessageList(int userId) {
        return null;
    }

    @Override
    public List<Message> getMessageByRoom(int roomId) {
        return null;
    }
}
