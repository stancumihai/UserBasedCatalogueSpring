package com.example.db_project.model.course;

import java.sql.Timestamp;


public class FinalTest {
    private int id;
    private Timestamp date;
    private Activity activity;
    private FinalTestType type;

    public FinalTest() {

    }

    public FinalTest(int id, Timestamp date, Activity activity, FinalTestType type) {
        this.id = id;
        this.date = date;
        this.activity = activity;
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public FinalTestType getType() {
        return type;
    }

    public void setType(FinalTestType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "finalTest{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", activity=" + activity +
                ", type=" + type +
                '}';
    }
}
