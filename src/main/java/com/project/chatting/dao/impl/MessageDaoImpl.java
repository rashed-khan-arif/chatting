package com.project.chatting.dao.impl;

import com.project.chatting.core.Database;
import com.project.chatting.core.Parser;
import com.project.chatting.dao.MessageDao;
import com.project.chatting.dao.UserDao;
import com.project.chatting.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDaoImpl implements MessageDao {
    Connection connection;

    public MessageDaoImpl() {
        this.connection = Database.getConnection();
    }

    @Override
    public Room isItP2P(int from, int to) {
        PreparedStatement ps = null;
        Room room = null;
        List<Member> members = new ArrayList<>();
        try {
            String query = "select * from members where  user_id= ? or user_id= ? and connection_status = ? ";
            ps = (PreparedStatement) connection.prepareStatement(query);
            ps.setInt(1, from);
            ps.setInt(2, to);
            ps.setInt(3, ConnectionStatus.Connected.value);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Member member;
                member = Parser.parser(rs, Member.class);
                members.add(member);
            }
            if (members.size() == 2) {
                if (members.get(0).getRoomId() == members.get(1).getRoomId()) {
                    room = getRoom(members.get(0).getRoomId());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


        return room;
    }

    @Override
    public Room createRoom(Room room) {

        PreparedStatement ps = null;
        Room newRoom = null;
        try {
            String query = "INSERT INTO room(room_name,created_by,active) VALUES (?,?,?)";
            ps = connection.prepareStatement(query);
            ps.setString(1, room.getRoomName());
            ps.setInt(2, room.getCreatedBy());
            ps.setInt(3, room.getActive());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                newRoom = Parser.parser(rs, Room.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return newRoom;
    }

    @Override
    public Member addMemberToRoom(Member member) {
        PreparedStatement ps = null;
        Member newMember = null;
        try {
            String query = "INSERT INTO members(room_id,user_id,connection_status) VALUES (?,?,?)";
            ps = connection.prepareStatement(query);
            ps.setInt(1, member.getRoomId());
            ps.setInt(2, member.getUserId());
            ps.setInt(3, member.getConnectionStatus());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                newMember = Parser.parser(rs, Member.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return newMember;
    }

    @Override
    public Room getRoom(int roomId) {
        PreparedStatement ps = null;
        Room room = null;
        try {
            String query = "select * from room where room_id=" + roomId;
            ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                room = Parser.parser(rs, Room.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return room;
    }

    @Override
    public Message addMessage(Message msg) {
        PreparedStatement ps = null;
        Message newMsg = null;
        try {
            String query = "INSERT INTO message(room_id,user_id,message_content) VALUES (?,?,?)";
            ps = connection.prepareStatement(query);
            ps.setInt(1, msg.getRoomId());
            ps.setInt(2, msg.getUserId());
            ps.setString(3, msg.getMessageContent());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                newMsg = Parser.parser(rs, Message.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return newMsg;
    }

    @Override
    public List<Message> getMessageList(int userId) {
        PreparedStatement ps = null;
        List<Message> messages = new ArrayList<>();
        try {
            String query = "select * from message where user_id=" + userId + " ORDER by message_id DESC ";
            ;
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message msg;
                msg = Parser.parser(rs, Message.class);
                messages.add(msg);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return messages;
    }

    @Override
    public List<Message> getMessageByRoom(int roomId) {

        PreparedStatement ps = null;
        List<Message> messages = new ArrayList<>();
        try {
            String query = "select * from message where room_id=" + roomId + " ORDER by message_id DESC ";
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message msg;
                msg = Parser.parser(rs, Message.class);
                messages.add(msg);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return messages;
    }

    @Override
    public List<Message> getRecentMessage(int userId) {
        PreparedStatement ps = null;
        List<Message> messages = new ArrayList<>();
        try {
            String query = "select * from message as m left JOIN room as r on m.room_id=r.room_id LEFT JOIN members as mb on r.room_id=mb.room_id where mb.user_id=? ORDER by m.message_id DESC LIMIT 20 ";
            ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message msg;
                msg = Parser.parser(rs, Message.class);
                messages.add(msg);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        for (Message msg : messages) {
            msg.setRoom(getRoom(msg.getRoomId()));
        }
        return messages;
    }
}
