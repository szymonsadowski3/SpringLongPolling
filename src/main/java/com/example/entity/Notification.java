package com.example.entity;

import java.sql.Timestamp;

public class Notification {
    public String content;
    public Timestamp ts;

    public Notification(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "content='" + content + '\'' +
                ", ts=" + ts +
                '}';
    }
}
