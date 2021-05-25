package com.example.db_project.model.studyGroup;

import com.example.db_project.model.studyGroup.StudentActivityGroup;
import com.example.db_project.model.users.Student;

public class EnrolledStudent {
    private int id;
    private StudentActivityGroup activity;
    private Student student;

    public EnrolledStudent() {

    }

    public EnrolledStudent(int id, StudentActivityGroup activity, Student student) {
        this.id = id;
        this.activity = activity;
        this.student = student;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public StudentActivityGroup getActivity() {
        return activity;
    }

    public void setActivity(StudentActivityGroup activity) {
        this.activity = activity;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "EnrolledStudent{" +
                "id=" + id +
                ", activity=" + activity +
                ", student=" + student +
                '}';
    }
}
