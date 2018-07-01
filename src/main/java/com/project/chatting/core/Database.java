package com.project.chatting.core;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private static String url = "jdbc:mysql://localhost:3306/db_chatting";
    private static String driverName = "com.mysql.jdbc.Driver";
    private static String username = "root";
    private static String password = "";
    private static Connection con;

    public static Connection getConnection() {
        try {
            Class.forName(driverName);
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            System.out.println("Driver not found.");
        }
        return con;
    }
}
