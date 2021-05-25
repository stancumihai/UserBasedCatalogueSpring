package com.example.db_project.model.studyGroup;

import com.example.db_project.model.course.Subject;
import com.example.db_project.model.users.User;

public class StudyGroup {
    private int id;
    private Subject subject;
    private User creator;
    private String name;

    public StudyGroup() {

    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public StudyGroup(int id, Subject subject) {
        this.id = id;
        this.subject = subject;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "StudyGroup{" +
                "id=" + id +
                ", subject=" + subject +
                ", creator=" + creator +
                ", name='" + name + '\'' +
                '}';
    }
}

