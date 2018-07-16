package com.project.chatting.dao;

import com.project.chatting.model.Member;
import com.project.chatting.model.Message;
import com.project.chatting.model.Room;

import java.util.List;

public interface MessageDao {
    Room isItP2P(int from, int to);

    Room createRoom(Room room);

    Member addMemberToRoom(Member member);

    Member getMember(int memberId);

    List<Member> getMembers(int roomId);

    Room getRoom(int roomId);

    Message addMessage(Message msg);

    List<Message> getMessageList(int userId);

    Message getMessage(int msgId);

    List<Message> getMessageByRoom(int roomId);

    List<Message> getRecentMessage(int userId);
}
