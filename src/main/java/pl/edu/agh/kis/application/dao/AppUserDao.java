package pl.edu.agh.kis.application.dao;

import pl.edu.agh.kis.application.connectivity.DbConnection;
import pl.edu.agh.kis.application.entity.AppUser;
import pl.edu.agh.kis.application.external.BCrypt;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class AppUserDao {
    private Sql2o sql2o = DbConnection.getInstance();

    public boolean createAppUser(String username, String hashed) {
        try (Connection con = sql2o.open()) {
            final String query =
                    "SELECT * FROM app_user WHERE username = :username";

            int usersLength = con.createQuery(query)
                    .addParameter("username", username)
                    .executeAndFetch(AppUser.class).size();

            if (usersLength > 0) {
                return false;
            } else {
                String insertSql = "INSERT into app_user VALUES(null, :username, :hashed)";
                con.createQuery(insertSql)
                        .addParameter("username", username)
                        .addParameter("hashed", hashed)
                        .executeUpdate();

                return true;
            }
        }
    }

    public boolean verifyAppUser(String username, String password) {
        try (Connection con = sql2o.open()) {
            final String query = "SELECT * FROM app_user WHERE username = :username";

            List<AppUser> fetchedUsers = con.createQuery(query)
                    .addParameter("username", username)
                    .executeAndFetch(AppUser.class);

            if (fetchedUsers.size() > 0) {
                return BCrypt.checkpw(password, fetchedUsers.get(0).getHashed());
            } else {
                return false;
            }
        }
    }

    public AppUser readAppUser(int userId) {
        try (Connection con = sql2o.open()) {
            final String query = "SELECT * FROM app_user WHERE userId = :userId";

            return con.createQuery(query)
                    .addParameter("userId", userId)
                    .executeAndFetch(AppUser.class).get(0);
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