package com.kostApp.kostApp.DTO;

import com.kostApp.kostApp.models.User;

import javax.persistence.*;

public class DiscussionDTO {

    private int id;

    private String name;

    private User user;

    public DiscussionDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
