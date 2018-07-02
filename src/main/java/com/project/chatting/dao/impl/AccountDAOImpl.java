package com.project.chatting.dao.impl;

import com.project.chatting.core.Database;
import com.project.chatting.core.Parser;
import com.project.chatting.dao.AccountDAO;
import com.project.chatting.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAOImpl implements AccountDAO {
    Connection connection;

    public AccountDAOImpl() {
        connection = Database.getConnection();
    }

    @Override
    public boolean createAccount(User user) {
        return false;
    }

    @Override
    public boolean updateAccountInfo(User user) {
        return false;
    }

    @Override
    public User getUser(int id) {
        return null;
    }

    @Override
    public User checkLoginInfo(String email, String password) {
        PreparedStatement ps = null;
        User user = null;
        try {
            String query = "select * from user " +
                    "where email='" + email + "' " +
                    "and password = Md5('" + password + "')" +
                    "and active=1 ";
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
}
