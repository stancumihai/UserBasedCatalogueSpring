package com.example.db_project.model.users;

public class Student{
    private int id;
    private User user;
    private int yearOfStudy;
    private int numberOfHours;

    public Student() {
    }

    public Student(int id, User user, int yearOfStudy, int numberOfHours) {
        this.id = id;
        this.user = user;
        this.yearOfStudy = yearOfStudy;
        this.numberOfHours = numberOfHours;
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

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public int getNumberOfHours() {
        return numberOfHours;
    }

    public void setNumberOfHours(int numberOfHours) {
        this.numberOfHours = numberOfHours;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", user=" + user +
                ", yearOfStudy=" + yearOfStudy +
                ", numberOfHours=" + numberOfHours +
                '}';
    }
}
