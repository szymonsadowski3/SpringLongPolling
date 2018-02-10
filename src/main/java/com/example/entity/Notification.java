package com.example.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Notification {
    private int notificationId;
    private Timestamp createdOn;
    private String content;
    private int groupId;
    private int importance;
    private int authorId;
    private String title;
    private String authorName;

    public Notification() {
    }

    public Notification(String content, int groupId, int importance, int authorId, String title) {
        this.content = content;
        this.groupId = groupId;
        this.importance = importance;
        this.authorId = authorId;
        this.authorName = authorName;
        this.title = title;
    }

    public Notification(int notificationId, Timestamp createdOn, String content,
                        int groupId, int importance, int authorId, String authorName, String title) {
        this.notificationId = notificationId;
        this.createdOn = createdOn;
        this.content = content;
        this.groupId = groupId;
        this.importance = importance;
        this.authorId = authorId;
        this.authorName = authorName;
        this.title = title;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getCreatedOn() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        return createdOn;
        return dateFormat.format(createdOn);
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
