package com.example.entity;

public class AppUser {
    private int userId;
    private String username;
    private String hashed;

    public AppUser(int userId, String username, String hashed) {
        this.userId = userId;
        this.username = username;
        this.hashed = hashed;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashed() {
        return hashed;
    }

    public void setHashed(String hashed) {
        this.hashed = hashed;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                '}';
    }
}
