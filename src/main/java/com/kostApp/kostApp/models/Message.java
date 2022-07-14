package com.kostApp.kostApp.models;

import java.util.Date;

public class Message {

    private int id;

    private int discussionId;

    private String message;

    private Date createdAt;

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(int discussionId) {
        this.discussionId = discussionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", discussionId=" + discussionId +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
