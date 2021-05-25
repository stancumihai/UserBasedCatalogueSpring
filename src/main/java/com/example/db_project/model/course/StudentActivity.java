package com.example.db_project.model.course;

import com.example.db_project.model.users.Student;

public class StudentActivity {
    private int id;
    private Student student;
    private Activity activity;
    private float activityGrade;

    public StudentActivity() {

    }

    public StudentActivity(int id, Student student, Activity activity, float actityGrade) {
        this.id = id;
        this.student = student;
        this.activity = activity;
        this.activityGrade = actityGrade;
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

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public float getActivityGrade() {
        return activityGrade;
    }

    public void setActivityGrade(float actityGrade) {
        this.activityGrade = actityGrade;
    }

    @Override
    public String toString() {
        return "StudentActivity{" +
                "id='" + id + '\'' +
                ", student=" + student +
                ", activity=" + activity +
                ", actityGrade=" + activityGrade +
                '}';
    }
}
