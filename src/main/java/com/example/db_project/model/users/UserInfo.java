package com.example.db_project.model.users;

public class UserInfo {
    private int id;
    private int userId;
    private String name;
    private String surname;
    private String CNP;

    public UserInfo() {

    }

    public UserInfo(int id, int userId, String name, String surname, String CNP) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.CNP = CNP;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

}
