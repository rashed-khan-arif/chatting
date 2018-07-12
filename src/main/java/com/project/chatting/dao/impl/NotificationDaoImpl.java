package com.project.chatting.dao.impl;

import com.project.chatting.core.Database;
import com.project.chatting.core.Parser;
import com.project.chatting.dao.NotificationDao;
import com.project.chatting.dao.UserDao;
import com.project.chatting.model.FriendRequestStatus;
import com.project.chatting.model.Notification;
import com.project.chatting.model.UserFriend;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDaoImpl implements NotificationDao {
    Connection connection;

    public NotificationDaoImpl() {
        connection = Database.getConnection();
    }

    @Override
    public List<Notification> getNotificationsByUser(int userId) {
        PreparedStatement ps = null;
        List<Notification> notifications = new ArrayList<>();
        try {
            String query = "select * from notification where to_user=" + userId;
            ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            Notification notification = null;
            while (rs.next()) {
                notification = Parser.parser(rs, Notification.class);
                notifications.add(notification);
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
        for (Notification uf : notifications) {
            uf.setUser(userDao.getUser(uf.getToUser()));
            if (uf.getUser() != null)
                uf.getUser().setPassword(null);
        }
        return notifications;
    }

    @Override
    public Notification getNotification(int id) {
        return null;
    }

    @Override
    public boolean sendNotification(Notification notification) {
        PreparedStatement ps = null;
        java.sql.Date sqlDate = new java.sql.Date(notification.getNotifyDate().getTime());
        try {
            String query = "INSERT INTO notification(title,content,to_user,notify_date) VALUES (?,?,?,?)";
            ps = connection.prepareStatement(query);
            ps.setString(1, notification.getTitle());
            ps.setString(2, notification.getContent());
            ps.setInt(3, notification.getToUser());
            ps.setDate(4, sqlDate);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            int id = 0;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            return id != 0;
        } catch (Exception e) {
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
}
