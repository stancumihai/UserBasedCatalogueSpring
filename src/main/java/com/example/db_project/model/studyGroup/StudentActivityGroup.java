package com.example.db_project.model.studyGroup;

import com.example.db_project.model.course.GroupActivityState;
import com.example.db_project.model.users.Professor;
import com.example.db_project.model.users.Student;

import java.sql.Timestamp;

public class StudentActivityGroup {
    private int id;
    private StudyGroup studyGroup;
    private int minAttendees;
    private Timestamp startDate;
    private float duration;
    private Timestamp enrollmentDeadline;
    private GroupActivityState state = GroupActivityState.valueOf("SOON");
    private Student initiator;
    private Professor professor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(StudyGroup studyGroup) {
        this.studyGroup = studyGroup;
    }

    public int getMinAttendees() {
        return minAttendees;
    }

    public void setMinAttendees(int minAttendees) {
        this.minAttendees = minAttendees;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public Timestamp getEnrollmentDeadline() {
        return enrollmentDeadline;
    }

    public void setEnrollmentDeadline(Timestamp enrollmentDeadline) {
        this.enrollmentDeadline = enrollmentDeadline;
    }

    public GroupActivityState getState() {
        return state;
    }

    public void setState(GroupActivityState state) {
        this.state = state;
    }

    public Student getInitiator() {
        return initiator;
    }

    public void setInitiator(Student initiator) {
        this.initiator = initiator;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}
