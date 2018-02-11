package pl.edu.agh.kis.application.entity;

/**
 * Representation of "app_user" table from database
 */
public class AppUser {
    private int userId;
    private String username;
    private String hashed;

    /**
     * @param userId Unique identifier of AppUser
     * @param username User's name
     * @param hashed Password hashed by BCrypt
     */
    public AppUser(int userId, String username, String hashed) {
        this.userId = userId;
        this.username = username;
        this.hashed = hashed;
    }

    /**
     * Gets userId.
     *
     * @return Value of userId.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets new username.
     *
     * @param username New value of username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets new userId.
     *
     * @param userId New value of userId.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets hashed.
     *
     * @return Value of hashed.
     */
    public String getHashed() {
        return hashed;
    }

    /**
     * Sets new hashed.
     *
     * @param hashed New value of hashed.
     */
    public void setHashed(String hashed) {
        this.hashed = hashed;
    }

    /**
     * Gets username.
     *
     * @return Value of username.
     */
    public String getUsername() {
        return username;
    }
}
