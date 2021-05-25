package com.example.db_project.model.course;

public class Subject {
    private int id;
    private String name;
    private String description;
    private int maxStudents;


    public Subject() {

    }

    public Subject(int id, String name, String description, int maxStudents) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.maxStudents = maxStudents;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", maxStudents=" + maxStudents +
                '}';
    }

}
