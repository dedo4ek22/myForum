package com.kostApp.kostApp.models;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {

    private int id;

    private int discussionId;

    private String message;

    private Date createdAt;

    private int userId;

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

    public String getCreatedAtStringFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
        return sdf.format(createdAt);
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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


}
