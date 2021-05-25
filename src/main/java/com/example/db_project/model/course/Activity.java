package com.example.db_project.model.course;


import com.example.db_project.model.users.Professor;

import java.sql.Date;
import java.sql.Time;


public class Activity {
    private int id;
    private float frequency;
    private float duration;

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    private int weekDay;
    private Time time;
    private Date startDate;
    private Date endDate;
    private Subject subject;
    private ActivityType type;
    private Professor professor;


    public Activity() {
    }

    public Activity(int id, int weekDay, float frequency, Time time, Date startDate,
                    Date endDate, Subject subject, ActivityType type, Professor professor) {
        this.id = id;
        this.frequency = frequency;
        this.weekDay = weekDay;
        this.time = time;
        this.startDate = startDate;
        this.endDate = endDate;
        this.subject = subject;
        this.type = type;
        this.professor = professor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Subject getSubject() {
        return subject;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", frequency=" + frequency +
                ", time=" + time +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", subject=" + subject +
                ", type=" + type +
                ", professor=" + professor +
                '}';
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

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}
