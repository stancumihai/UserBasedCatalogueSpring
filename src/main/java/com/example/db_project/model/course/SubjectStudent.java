package com.example.db_project.model.course;


import com.example.db_project.model.users.Student;

public class SubjectStudent {
    private int id;
    private Student student;
    private Subject subject;
    private float finalGrade;

    
    public SubjectStudent(int id, Student student, Subject subject, float finalGrade) {
        this.id = id;
        this.student = student;
        this.subject = subject;
        this.finalGrade = finalGrade;
    }

    public SubjectStudent() {

    }

    public float getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(float finalGrade) {
        this.finalGrade = finalGrade;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject Subject) {
        this.subject = Subject;
    }

    @Override
    public String toString() {
        return "SubjectStudent{" +
                "id='" + id + '\'' +
                ", student=" + student +
                ", subject=" + subject +
                ", finalGrade=" + finalGrade +
                '}';
    }
}
