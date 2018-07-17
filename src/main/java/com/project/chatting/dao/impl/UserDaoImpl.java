package com.project.chatting.dao.impl;

import com.project.chatting.core.Database;
import com.project.chatting.core.Parser;
import com.project.chatting.dao.UserDao;
import com.project.chatting.model.User;
import com.project.chatting.model.UserFriend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private Connection connection;

    public UserDaoImpl() {
        connection = Database.getConnection();
    }

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public User getUser(int userId) {
        PreparedStatement ps = null;
        User user = null;
        try {
            String query = "select * from user " +
                    "where user_id='" + userId + "' ";
            ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = Parser.parser(rs, User.class);
            }
            if (user != null)
                user.setPassword(null);
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
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        PreparedStatement ps = null;
        User user = null;
        try {
            String query = "select * from user " +
                    "where email='" + email + "' and active=1";
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
        return user;
    }

    @Override
    public User addUser(User user) {
        PreparedStatement ps = null;
        User resultUser = null;
        try {
            String query = "INSERT INTO user(full_name,email,image,contact_number,active,password) VALUES (?,?,?,?,?,?)";
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getImage());
            ps.setInt(4, user.getContactNumber());
            ps.setInt(5, user.getActive());
            ps.setString(6, user.getPassword());
            int userId = ps.executeUpdate();
            resultUser = getUser(userId);
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
        return resultUser;
    }

    @Override
    public boolean isEmailUnique(String email) {
        PreparedStatement ps = null;
        User user = null;
        try {
            String query = "select * from user " +
                    "where email='" + email + "' ";
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
        return user == null;
    }


}
