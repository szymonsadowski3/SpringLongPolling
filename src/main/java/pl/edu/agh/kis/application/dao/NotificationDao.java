package pl.edu.agh.kis.application.dao;

import pl.edu.agh.kis.application.entity.Notification;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

/**
 * Data Access Object used to obtain information about Notification entity
 */
@Repository
public class NotificationDao {
    private Sql2o sql2o = new Sql2o("jdbc:mysql://mysql.agh.edu.pl:3306/sadowski", "sadowski", "xPjFWAM00pYfJAP6");

    /**
     * @param content Content of notification
     * @param importance Importance level of notification (1 or 2 or 3)
     * @param authorName Username of the notification's author
     * @param title Title of notification
     */
    public void insertNotification(String content, int importance, String authorName, String title) {
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


    /**
     * @return Newest notification in database
     */
    public Notification getNewestNotification() {
        try (Connection con = sql2o.open()) {
            final String query = "SELECT notificationId, content, importance, authorName, createdOn, title FROM notification ORDER BY createdOn DESC LIMIT 1";

            return con.createQuery(query)
                    .executeAndFetch(Notification.class).get(0);
        }
    }

    /**
     * @return List of all notifications from database
     */
    public List<Notification> getNotifications() {
        try (Connection con = sql2o.open()) {
            final String query = "SELECT notificationId, content, importance, createdOn, title, authorName FROM notification ORDER BY createdOn DESC";

            return con.createQuery(query)
                    .executeAndFetch(Notification.class);
        }
    }
}
