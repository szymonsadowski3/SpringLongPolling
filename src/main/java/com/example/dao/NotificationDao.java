package com.example.dao;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class NotificationDao {
    private Connection dbConnection;

    private static Connection getMySqlConnection() throws Exception {
        String driver = "org.gjt.mm.mysql.Driver";
        String url = "jdbc:mysql://mysql.agh.edu.pl:3306/sadowski";
        String username = "sadowski";
        String password = "xPjFWAM00pYfJAP6";

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    public NotificationDao() throws Exception {
        this.dbConnection = getMySqlConnection();
    }

    public void insertNotificationToDb(String notification) throws Exception {
        PreparedStatement statement = dbConnection.prepareStatement("INSERT into notification VALUES(null, ?)");
        statement.setString(1, notification);
        statement.executeUpdate();
    }

    public String getNewestNotification() throws Exception {
        PreparedStatement statement = dbConnection.prepareStatement("SELECT content FROM notification ORDER BY ts LIMIT 1");
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString(1);
        } else {
            return "";
        }
    }
}
