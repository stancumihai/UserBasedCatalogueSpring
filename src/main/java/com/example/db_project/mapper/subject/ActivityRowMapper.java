package com.example.db_project.mapper.subject;

import com.example.db_project.model.course.Activity;
import com.example.db_project.model.course.ActivityType;
import com.example.db_project.model.users.Professor;
import com.example.db_project.model.course.Subject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActivityRowMapper implements RowMapper<Activity> {

    @Override
    public Activity mapRow(ResultSet resultSet, int i) throws SQLException {
        Activity activity = new Activity();

        activity.setId(resultSet.getInt("id"));
        ActivityType type = ActivityType.valueOf(resultSet.getString("type").toUpperCase());
        activity.setType(type);
        activity.setWeekDay(resultSet.getInt("weekDay"));
        activity.setStartDate(resultSet.getDate("startDate"));
        activity.setEndDate(resultSet.getDate("endDate"));
        activity.setTime(resultSet.getTime("time"));
        activity.setFrequency(resultSet.getFloat("frequency"));

        Professor professor = new Professor();
        professor.setId(resultSet.getInt("professor"));
        activity.setProfessor(professor);

        Subject subject = new Subject();
        subject.setId(resultSet.getInt("subject"));
        activity.setSubject(subject);
        activity.setDuration(resultSet.getFloat("duration"));
        return activity;
    }
}
