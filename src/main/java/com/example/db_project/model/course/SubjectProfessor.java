package com.example.db_project.model.course;

import com.example.db_project.model.users.Professor;

public class SubjectProfessor {
    private int id;
    private Subject subject;
    private Professor professor;

    public SubjectProfessor() {

    }

    public SubjectProfessor(int id, Subject subject, Professor professor) {
        this.id = id;
        this.subject = subject;
        this.professor = professor;
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

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    @Override
    public String toString() {
        return "SubjectProfessor{" +
                "id='" + id + '\'' +
                ", subject=" + subject +
                ", professor=" + professor +
                '}';
    }
}
