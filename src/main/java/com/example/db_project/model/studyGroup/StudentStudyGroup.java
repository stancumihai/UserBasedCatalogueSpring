package com.example.db_project.model.studyGroup;

import com.example.db_project.model.users.Student;

public class StudentStudyGroup {

    private int id;
    private Student student;
    private StudyGroup studyGroup;

    @Override
    public String toString() {
        return "StudentStudyGroup{" +
                "id=" + id +
                ", student=" + student +
                ", studyGroup=" + studyGroup +
                '}';
    }

    public StudentStudyGroup() {

    }

    public StudentStudyGroup(int id, Student student, StudyGroup group) {
        this.id = id;
        this.student = student;
        this.studyGroup = group;
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

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(StudyGroup studyGroup) {
        this.studyGroup = studyGroup;
    }
}
