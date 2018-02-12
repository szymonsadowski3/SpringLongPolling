package pl.edu.agh.kis.application.entity;

import org.json.simple.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Notification {
    private int notificationId;
    private String createdOn;
    private String content;
    private int groupId;
    private int importance;
    private String title;
    private String authorName;

    public Notification() {
    }

    public Notification(String content, int groupId, int importance, String title) {
        this.content = content;
        this.groupId = groupId;
        this.importance = importance;
        this.title = title;
    }

    public Notification(int notificationId, String createdOn, String content,
                        int groupId, int importance, String authorName, String title) {
        this.notificationId = notificationId;
        this.createdOn = createdOn;
        this.content = content;
        this.groupId = groupId;
        this.importance = importance;
        this.authorName = authorName;
        this.title = title;
    }

    /**
     * @return Converts Object to JSONObject - (rewriting the fields to JSONObject)
     */
    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        json.put("notificationId", getNotificationId());
        json.put("createdOn", getCreatedOn());
        json.put("content", getContent());
        json.put("groupId", getGroupId());
        json.put("importance", getImportance());
        json.put("title", getTitle());
        json.put("authorName", getAuthorName());

        return json;
    }

    /**
     * @return Human readable format of timestamp, which represents datetime, when notification was created
     */
    public String getCreatedOn() {
        return createdOn;
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        return dateFormat.format(createdOn);
    }

    /**
     * Gets importance.
     *
     * @return Value of importance.
     */
    public int getImportance() {
        return importance;
    }

    /**
     * Gets authorName.
     *
     * @return Value of authorName.
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * Gets content.
     *
     * @return Value of content.
     */
    public String getContent() {
        return content;
    }

    /**
     * Gets groupId.
     *
     * @return Value of groupId.
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * Sets new importance.
     *
     * @param importance New value of importance.
     */
    public void setImportance(int importance) {
        this.importance = importance;
    }

    /**
     * Gets title.
     *
     * @return Value of title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets new notificationId.
     *
     * @param notificationId New value of notificationId.
     */
    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    /**
     * Sets new groupId.
     *
     * @param groupId New value of groupId.
     */
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    /**
     * Sets new title.
     *
     * @param title New value of title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets new content.
     *
     * @param content New value of content.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets notificationId.
     *
     * @return Value of notificationId.
     */
    public int getNotificationId() {
        return notificationId;
    }

    /**
     * Sets new authorName.
     *
     * @param authorName New value of authorName.
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
