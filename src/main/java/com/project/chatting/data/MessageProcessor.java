package com.project.chatting.data;

import com.project.chatting.dao.Dao;
import com.project.chatting.dao.impl.DAOImpl;
import com.project.chatting.model.*;

import java.util.Date;
import java.util.List;

public class MessageProcessor implements IMessageProcessor {
    private Dao dao;
    private static IMessageProcessor msgMessageProcessor = null;

    public static synchronized IMessageProcessor getInstance() {
        if (msgMessageProcessor == null) {
            msgMessageProcessor = new MessageProcessor();
        }
        return msgMessageProcessor;
    }

    private MessageProcessor() {
        dao = new DAOImpl();
    }

    @Override
    public Message processMessage(MessageItem messageItem) {
        Message message = new Message();
        Message sentMessage = null;
        if (messageItem.getRoomId() == 0) {
            Room isP2P = dao.getMessageDao().isItP2P(messageItem.getFromUserId(), messageItem.getToUserId());
            if (isP2P != null) {
                message.setRoomId(isP2P.getRoomId());
                message.setMessageContent(messageItem.getContent());
                message.setMsgDate(new Date());
                sentMessage = send(message);
            } else {
                User to = dao.getUserDao().getUser(messageItem.getToUserId());
                Room room = new Room();
                room.setRoomName(to.getFullName());
                Room newRoom = dao.getMessageDao().createRoom(room);
                Member m1, m2;
                m1 = new Member();
                m1.setUserId(messageItem.getFromUserId());
                m1.setRoomId(newRoom.getRoomId());
                m1.setConnectionStatus(ConnectionStatus.Connected.value);
                m2 = new Member();
                m2.setRoomId(newRoom.getRoomId());
                m2.setUserId(messageItem.getToUserId());
                m2.setConnectionStatus(ConnectionStatus.Disconnected.value);
                dao.getMessageDao().addMemberToRoom(m1);
                dao.getMessageDao().addMemberToRoom(m2);
                message.setRoomId(newRoom.getRoomId());
                message.setMsgDate(new Date());
                message.setMessageContent(messageItem.getContent());
                sentMessage = send(message);
            }
        } else {
            message.setRoomId(messageItem.getRoomId());
            message.setMessageContent(messageItem.getContent());
            message.setMsgDate(new Date());
            sentMessage = send(message);
        }
        return sentMessage;
    }

    @Override
    public Message send(Message message) {

        //do message related task
        return dao.getMessageDao().addMessage(message);
    }

    @Override
    public List<Message> getMessages(int userId) {
        return dao.getMessageDao().getMessageList(userId);
    }

    @Override
    public List<Message> getMessagesByRoom(int roomId) {
        return dao.getMessageDao().getMessageList(roomId);
    }
}
