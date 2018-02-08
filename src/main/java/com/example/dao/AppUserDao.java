package com.example.dao;

import com.example.connectivity.DbConnection;
import com.example.entity.Group;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class AppUserDao {
    private Sql2o sql2o = DbConnection.getInstance();

    public void createAppUser(String username) {
        String insertSql = "INSERT into app_user VALUES(null, :username)";

        try (Connection con = sql2o.open()) {
            con.createQuery(insertSql)
                    .addParameter("username", username)
                    .executeUpdate();
        }
    }

    public Group readAppUser(int userId) {
        try (Connection con = sql2o.open()) {
            final String query = "SELECT * FROM app_user WHERE userId = :userId";

            return con.createQuery(query)
                    .addParameter("userId", userId)
                    .executeAndFetch(Group.class).get(0);
        }
    }

    public void deleteUser(int userId) {
        String insertSql = "DELETE from app_user WHERE userId = :userId";

        try (Connection con = sql2o.open()) {
            con.createQuery(insertSql)
                    .addParameter("userId", userId)
                    .executeUpdate();
        }
    }

    public static void main(String[] args) {
        System.out.println(new NotificationDao().getNewestNotification());
    }
}
