package com.example.dao;

import com.example.connectivity.DbConnection;
import com.example.entity.Group;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class GroupDao {
    private Sql2o sql2o = DbConnection.getInstance();

    public void createGroup(String name) {
        String insertSql = "INSERT into group VALUES(null, :name)";

        try (Connection con = sql2o.open()) {
            con.createQuery(insertSql)
                    .addParameter("name", name)
                    .executeUpdate();
        }
    }

    public Group readGroup(int groupId) {
        try (Connection con = sql2o.open()) {
            final String query = "SELECT * FROM group WHERE groupId = :groupId";

            return con.createQuery(query)
                    .addParameter("groupId", groupId)
                    .executeAndFetch(Group.class).get(0);
        }
    }

    public void deleteGroup(int groupId) {
        String insertSql = "DELETE from group WHERE groupId = :groupId";

        try (Connection con = sql2o.open()) {
            con.createQuery(insertSql)
                    .addParameter("groupId", groupId)
                    .executeUpdate();
        }
    }

    public static void main(String[] args) {
        System.out.println(new NotificationDao().getNewestNotification());
    }
}
