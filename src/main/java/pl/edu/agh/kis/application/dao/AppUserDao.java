package pl.edu.agh.kis.application.dao;

import org.springframework.stereotype.Repository;
import pl.edu.agh.kis.application.connectivity.DbConnection;
import pl.edu.agh.kis.application.entity.AppUser;
import pl.edu.agh.kis.application.external.BCrypt;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

/**
 * Data Access Object used to obtain information about AppUser entity
 */
@Repository
public class AppUserDao {
    private Sql2o sql2o = DbConnection.getInstance();

    /**
     * @param username User's name
     * @param hashed Password hashed by BCrypt
     * @return true if operation of creation succeeded, false otherwise
     */
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

    /**
     * @param username user's name
     * @param password password typed in by user
     * @return result of verification
     */
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

    /**
     * @param userId unique identificator of user
     * @return Instance of AppUser class
     */
    public AppUser readAppUser(int userId) {
        try (Connection con = sql2o.open()) {
            final String query = "SELECT * FROM app_user WHERE userId = :userId";

            return con.createQuery(query)
                    .addParameter("userId", userId)
                    .executeAndFetch(AppUser.class).get(0);
        }
    }

    /**
     * @param userId Id of user which is going to be deleted
     */
    public void deleteUser(int userId) {
        String insertSql = "DELETE from app_user WHERE userId = :userId";

        try (Connection con = sql2o.open()) {
            con.createQuery(insertSql)
                    .addParameter("userId", userId)
                    .executeUpdate();
        }
    }
}
