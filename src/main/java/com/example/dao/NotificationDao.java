package com.example.dao;

import com.example.entity.Notification;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

@Repository
public class NotificationDao {
    Sql2o sql2o = new Sql2o("jdbc:mysql://mysql.agh.edu.pl:3306/sadowski", "sadowski", "xPjFWAM00pYfJAP6");

    public void insertNotificationToDb(String content) {
        String insertSql = "INSERT into notification VALUES(null, :content)";

        try (Connection con = sql2o.open()) {
            con.createQuery(insertSql)
                    .addParameter("content", content)
                    .executeUpdate();
        }
    }


    public Notification getNewestNotification() {
        try (Connection con = sql2o.open()) {
            final String query = "SELECT * FROM notification ORDER BY ts DESC LIMIT 1";

            return con.createQuery(query)
                    .executeAndFetch(Notification.class).get(0);
        }
    }

    public static void main(String[] args) {
        System.out.println(new NotificationDao().getNewestNotification());
    }
}
