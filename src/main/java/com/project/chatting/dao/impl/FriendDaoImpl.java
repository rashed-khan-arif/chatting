package com.project.chatting.dao.impl;

import com.project.chatting.core.Database;
import com.project.chatting.core.Parser;
import com.project.chatting.dao.IFriendDao;
import com.project.chatting.dao.UserDao;
import com.project.chatting.model.FriendRequestStatus;
import com.project.chatting.model.User;
import com.project.chatting.model.UserFriend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendDaoImpl implements IFriendDao {
    Connection connection;

    public FriendDaoImpl() {
        connection = Database.getConnection();
    }

    @Override
    public boolean isRequestAlreadySent(int userId, int friendId) {
        PreparedStatement ps = null;
        User user = null;
        try {
            String query = "select * from user_friend " +
                    "where user_id='" + userId + "' and friend_id='" + friendId + "' ";
            ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = Parser.parser(rs, User.class);
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
        return user != null;
    }

    @Override
    public boolean sendRequest(UserFriend userFriend) {
        PreparedStatement ps = null;
        try {
            String query = "INSERT INTO user_friend(user_id,friend_id,request_status) VALUES (?,?,?)";
            ps = connection.prepareStatement(query);
            ps.setInt(1, userFriend.getUserId());
            ps.setInt(2, userFriend.getFriendId());
            ps.setInt(3, userFriend.getRequestStatus());
            int userId = ps.executeUpdate();
            return userId > 0;
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
        return false;
    }

    @Override
    public List<UserFriend> getFriendsByUserId(int userId) {
        PreparedStatement ps = null;
        List<UserFriend> userFriends = new ArrayList<>();
        try {
            String query = "select * from user_friend " +
                    "where user_id='" + userId + "' ";
            ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            UserFriend userFriend = null;
            while (rs.next()) {
                userFriend = Parser.parser(rs, UserFriend.class);
                userFriends.add(userFriend);
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
        UserDao userDao = new DAOImpl().getUserDao();
        for (UserFriend uf : userFriends) {
            uf.setFriend(userDao.getUser(uf.getFriendId()));
            if (uf.getFriend() != null)
                uf.getFriend().setPassword(null);
            if (uf.getUser() != null)
                uf.getUser().setPassword(null);
        }

        return userFriends;

    }

    @Override
    public List<UserFriend> getFriendsRequests(int userId, FriendRequestStatus friendRequestStatus) {
        PreparedStatement ps = null;
        List<UserFriend> userFriends = new ArrayList<>();
        try {
            String query = "select * from user_friend " +
                    "where friend_id='" + userId + "' and request_status='" + friendRequestStatus.val + "' ";
            ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            UserFriend userFriend = null;
            while (rs.next()) {
                userFriend = Parser.parser(rs, UserFriend.class);
                userFriends.add(userFriend);
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
        UserDao userDao = new DAOImpl().getUserDao();
        for (UserFriend uf : userFriends) {
            uf.setFriend(userDao.getUser(uf.getFriendId()));
            if (uf.getFriend() != null)
                uf.getFriend().setPassword(null);
        }
        return userFriends;
    }

}
