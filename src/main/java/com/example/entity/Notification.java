package com.example.entity;

import java.sql.Timestamp;

public class Notification {
    private int notificationId;
    private Timestamp createdOn;
    private String content;
    private int groupId;
    private int importance;
    private int authorId;

    public Notification() {
    }

    public Notification(int notificationId, Timestamp createdOn, String content, int groupId, int importance, int authorId) {
        this.notificationId = notificationId;
        this.createdOn = createdOn;
        this.content = content;
        this.groupId = groupId;
        this.importance = importance;
        this.authorId = authorId;
    }

    public Notification(String content, int groupId, int importance, int authorId) {
        this.notificationId = notificationId;
        this.createdOn = createdOn;
        this.content = content;
        this.groupId = groupId;
        this.importance = importance;
        this.authorId = authorId;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId=" + notificationId +
                ", createdOn=" + createdOn +
                ", content='" + content + '\'' +
                ", groupId=" + groupId +
                ", importance=" + importance +
                ", authorId=" + authorId +
                '}';
    }
}
