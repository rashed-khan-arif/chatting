package com.project.chatting.data;

import com.project.chatting.model.Message;
import com.project.chatting.model.MessageItem;
import com.project.chatting.model.Room;

import java.util.List;

public interface IMessageProcessor {
    Message processMessage(MessageItem messageItem);

    Message send(Message message);

    List<Message> getMessages(int userId);

    List<Message> getMessagesByRoom(int roomId);


}
