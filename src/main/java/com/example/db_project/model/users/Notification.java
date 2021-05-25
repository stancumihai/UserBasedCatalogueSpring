package com.example.db_project.model.users;

import com.example.db_project.model.users.User;

public class Notification {
    private int id;
    private User user;
    private String body;
    private String title;
    private boolean isRead;

    public Notification(){

    }

    public Notification(int id, User user, String body, String title, boolean isRead) {
        this.id = id;
        this.user=user;
        this.body = body;
        this.title = title;
        this.isRead = isRead;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
