package com.example.db_project.model.users;


import java.util.Date;

public class Session {
    private String sessionKey;
    private int userId;
    private Date expiryDate;

    public Session(String sessionId, int userId, Date expiryDate) {
        this.sessionKey = sessionId;
        this.userId = userId;
        this.expiryDate = expiryDate;
    }

    public Session() {

    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionId='" + sessionKey + '\'' +
                ", userId=" + userId +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
