package com.example.dao;

import com.example.entity.Notification;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class NotificationDao {
    private Sql2o sql2o = new Sql2o("jdbc:mysql://mysql.agh.edu.pl:3306/sadowski", "sadowski", "xPjFWAM00pYfJAP6");

    public void insertNotification(String content, int importance, String authorName, String title) {
//        String insertSql = "INSERT into notification VALUES(null, :content, :groupId, :importance, :authorId, :title, null)";
        String insertSql = "INSERT into notification(content, importance, authorName, title) " +
                "VALUES(:content, :importance, :authorName, :title)";

        try (Connection con = sql2o.open()) {
            con.createQuery(insertSql)
                    .addParameter("content", content)
                    .addParameter("importance", importance)
                    .addParameter("authorName", authorName)
                    .addParameter("title", title)
                    .executeUpdate();
        }
    }


    public Notification getNewestNotification() {
        try (Connection con = sql2o.open()) {
            final String query = "SELECT notificationId, content, importance, authorName, createdOn, title FROM notification ORDER BY createdOn DESC LIMIT 1";

            return con.createQuery(query)
                    .executeAndFetch(Notification.class).get(0);
        }
    }

    public List<Notification> getNotifications() {
        try (Connection con = sql2o.open()) {
            final String query = "SELECT * FROM notification";

            return con.createQuery(query)
                    .executeAndFetch(Notification.class);
        }
    }

    public List<Notification> getNotificationsWithAuthorNames() {
        try (Connection con = sql2o.open()) {
            final String query = "SELECT notificationId, content, importance, createdOn, title, authorName FROM notification ORDER BY createdOn DESC";

            return con.createQuery(query)
                    .executeAndFetch(Notification.class);
        }
    }

    public static void main(String[] args) {
        System.out.println(new NotificationDao().getNewestNotification());
    }
}
