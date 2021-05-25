package com.example.db_project.model.studyGroup;

import com.example.db_project.model.studyGroup.StudyGroup;
import com.example.db_project.model.users.Student;

public class Message {

    private int id;
    private Student student;
    private StudyGroup studyGroup;
    private String message;

    public Message() {

    }

    public Message(int id, Student student, StudyGroup studygroup, String message) {
        this.id = id;
        this.student = student;
        this.studyGroup = studygroup;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
