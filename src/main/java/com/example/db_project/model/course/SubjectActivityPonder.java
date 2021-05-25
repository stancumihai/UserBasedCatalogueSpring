package com.example.db_project.model.course;

public class SubjectActivityPonder {
    private int id;
    private Subject subject;
    private ActivityType type;
    private int ponder;

    public SubjectActivityPonder(){

    }
    public SubjectActivityPonder(int id, Subject subject, ActivityType type, int pondere) {
        this.id = id;
        this.subject = subject;
        this.type = type;
        this.ponder = pondere;
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

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public int getPonder() {
        return ponder;
    }

    public void setPonder(int ponder) {
        this.ponder = ponder;
    }

    @Override
    public String toString() {
        return "SubjectActivityPonder{" +
                "id='" + id + '\'' +
                ", subject=" + subject +
                ", type=" + type +
                ", ponder=" + ponder +
                '}';
    }
}
