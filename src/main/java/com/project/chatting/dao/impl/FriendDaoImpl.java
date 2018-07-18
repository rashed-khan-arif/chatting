package com.project.chatting.dao.impl;

import com.mysql.jdbc.Statement;
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
    public UserFriend sendRequest(UserFriend userFriend) {
        PreparedStatement ps = null;
        UserFriend userFr = null;
        try {
            String query = "INSERT INTO user_friend(user_id,friend_id,request_status) VALUES (?,?,?)";
            ps = connection.prepareStatement(query);
            ps.setInt(1, userFriend.getUserId());
            ps.setInt(2, userFriend.getFriendId());
            ps.setInt(3, userFriend.getRequestStatus());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            int userFrnId = 0;
            if (rs.next()) {
                userFrnId = rs.getInt(1);
            }
            if (userFrnId != 0)
                userFr = getUserFriendRequest(userFrnId);
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
        return userFr;
    }

    @Override
    public UserFriend updateRequestStatus(int userFriendId, FriendRequestStatus status) {
        PreparedStatement ps = null;
        UserFriend userFr = null;
        try {
            String query = "UPDATE user_friend SET request_status=? WHERE user_friend_id=?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, status.val);
            ps.setInt(2, userFriendId);
            ps.executeUpdate();
            userFr = getUserFriendRequest(userFriendId);
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

        return userFr;
    }

    @Override
    public List<UserFriend> getFriendsByUserId(int userId) {
        PreparedStatement ps = null;
        List<UserFriend> userFriends = new ArrayList<>();
        try {
            String query = "select * from user_friend " +
                    "where  user_id='" + userId + "' or friend_id='" + userId + "' HAVING request_status='" + FriendRequestStatus.Accepted.val + "'";
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
            uf.setUser(userDao.getUser(uf.getUserId()));
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
            uf.setUser(userDao.getUser(uf.getUserId()));
            if (uf.getUser() != null)
                uf.getUser().setPassword(null);
        }
        return userFriends;
    }

    @Override
    public UserFriend getUserFriendRequest(int frnRqId) {
        PreparedStatement ps = null;
        UserFriend userFriend = null;
        try {
            String query = "select * from user_friend " +
                    "where user_friend_id=" + frnRqId;
            ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userFriend = Parser.parser(rs, UserFriend.class);
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
        if (userFriend != null) {
            UserDao userDao = new DAOImpl().getUserDao();
            userFriend.setFriend(userDao.getUser(userFriend.getFriendId()));
            userFriend.setUser(userDao.getUser(userFriend.getUserId()));
            if (userFriend.getFriend() != null)
                userFriend.getFriend().setPassword(null);
            if (userFriend.getUser() != null)
                userFriend.getUser().setPassword(null);
        }
        return userFriend;
    }
}
