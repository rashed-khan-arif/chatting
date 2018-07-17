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
            String query = "select m1.room_id from members as m1 join members as m2 on m2.user_id = ? and m2.connection_status = ? and m1.room_id = m2.room_id where m1.user_id= ? and m1.connection_status = ?";
            ps = (PreparedStatement) connection.prepareStatement(query);
            ps.setInt(1, from);
            ps.setInt(2, ConnectionStatus.Connected.value);
            ps.setInt(3, to);
            ps.setInt(4, ConnectionStatus.Connected.value);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Member member;
                member = Parser.parser(rs, Member.class);
                members.add(member);
            }
            if (members.size() > 0)
                room = getRoom(members.get(0).getRoomId());
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
                newRoom = getRoom(rs.getInt(1));
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
    public void updateRoom(Room room) {

        PreparedStatement ps = null;
        try {
            String query = "UPDATE room SET room_name=?,created_by=?,active=? WHERE room_id=?";
            ps = connection.prepareStatement(query);
            ps.setString(1, room.getRoomName());
            ps.setInt(2, room.getCreatedBy());
            ps.setInt(3, room.getActive());
            ps.setInt(4, room.getRoomId());
            ps.executeUpdate();

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
                newMember = getMember(rs.getInt(1));
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
        if (newMember != null) {
            Room room = getRoom(newMember.getRoomId());
            room.setRoomName(room.getRoomName().concat(newMember.getUser().getFullName()));
            updateRoom(room);
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
        if (room != null) {
            // room.setMembers(getMembers(roomId));
        }
        return room;
    }

    @Override
    public List<Member> getMembers(int roomId) {
        PreparedStatement ps = null;
        List<Member> members = new ArrayList<>();
        try {
            String query = "select * from members where room_id=" + roomId + " ORDER by room_id DESC ";
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Member member;
                member = Parser.parser(rs, Member.class);
                members.add(member);
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
        if (members.size() > 0) {
            for (Member member : members) {
                member.setRoom(getRoom(member.getRoomId()));
                member.setUser(new DAOImpl().getUserDao().getUser(member.getUserId()));
            }
        }
        return members;
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
                newMsg = getMessage(rs.getInt(1));
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
        if (newMsg != null) {
            newMsg.setRoom(getRoom(newMsg.getRoomId()));
        }
        return newMsg;
    }

    @Override
    public List<Message> getMessageList(int userId) {
        PreparedStatement ps = null;
        List<Message> messages = new ArrayList<>();
        try {
            String query = "select * from message where user_id=" + userId + " ORDER by message_id DESC limit 20";
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
    public Message getMessage(int msgId) {
        PreparedStatement ps = null;
        Message message = null;
        try {
            String query = "select * from message where message_id=? ";
            ps = connection.prepareStatement(query);
            ps.setInt(1, msgId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                message = Parser.parser(rs, Message.class);
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
        if (message != null) {
            message.setRoom(getRoom(message.getRoomId()));
        }
        return message;
    }

    @Override
    public Member getMember(int memberId) {
        PreparedStatement ps = null;
        Member member = null;
        try {
            String query = "select * from members where connection_id=? ";
            ps = connection.prepareStatement(query);
            ps.setInt(1, memberId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                member = Parser.parser(rs, Member.class);
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
        if (member != null) {
            member.setRoom(getRoom(member.getRoomId()));
            member.setUser(new DAOImpl().getUserDao().getUser(member.getUserId()));
        }
        return member;
    }

    @Override
    public List<Message> getMessageByRoom(int roomId) {

        PreparedStatement ps = null;
        List<Message> messages = new ArrayList<>();
        try {
            String query = "select * from message where room_id=" + roomId + " ORDER by message_id DESC limit 20 ";
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
    public List<Message> getP2PMsgList(int roomId, int member1, int member2) {
        PreparedStatement ps = null;
        List<Message> messages = new ArrayList<>();
        try {
            String query = "select * from message WHERE user_id IN (?,?) and room_id=?  ORDER by message_id DESC LIMIT 20 ";
            ps = connection.prepareStatement(query);
            ps.setInt(1, member1);
            ps.setInt(2, member2);
            ps.setInt(3, roomId);
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

    @Override
    public List<Message> getRecentMessage(int userId) {
        PreparedStatement ps = null;
        List<Message> messages = new ArrayList<>();
        try {
            String query = "select m.* from message as m left join members as mb on m.room_id=mb.room_id where mb.user_id=? group by mb.room_id ORDER by m.message_id ASC LIMIT 20 ";
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

    @Override
    public boolean isUserExitsInThisRoom(int roomId, int userId) {
        PreparedStatement ps = null;
        Member member = null;
        try {
            String query = "select * from members where room_id=? and user_id=?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, roomId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                member = Parser.parser(rs, Member.class);
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
        return member != null;
    }
}
