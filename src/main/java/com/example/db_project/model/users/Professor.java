package com.example.db_project.model.users;


public class Professor {
    private int id;
    private User user;
    private int minHours;
    private int maxHours;
    private Department department;

    public Professor() {

    }

    public Professor(int id, User user, int minHours, int maxHours, Department department) {
        this.id = id;
        this.user = user;
        this.minHours = minHours;
        this.maxHours = maxHours;
        this.department = department;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getMinHours() {
        return minHours;
    }

    public void setMinHours(int minHours) {
        this.minHours = minHours;
    }

    public int getMaxHours() {
        return maxHours;
    }

    public void setMaxHours(int maxHours) {
        this.maxHours = maxHours;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", user=" + user +
                ", minHours=" + minHours +
                ", maxHours=" + maxHours +
                ", department=" + department +
                '}';
    }
}
