package com.example.db_project.dao.activity;


import com.example.db_project.mapper.subject.ActivityRowMapper;
import com.example.db_project.model.course.Activity;
import com.example.db_project.model.course.ActivityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("ActivityRepo")
public class ActivityDataAccessService implements ActivityDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public Optional<Activity> updateActivity(Activity activity, int id) {

        String updateQuery = "UPDATE activity SET frequency=? ,weekday = ?, time = ?, startDate = ?, " +
                "endDate = ?, type= ? WHERE id = ?;";
        int updated = jdbcTemplate.update
                (updateQuery, activity.getFrequency(), activity.getWeekDay(), activity.getTime()
                        , activity.getStartDate(), activity.getEndDate(), activity.getType().toString(), id);

        if (updated == 1) {
            return Optional.of(activity);
        }
        return Optional.empty();
    }

    @Override
    public Activity getActivityById(int id) {
        String sql = "select * from activity where id = ?";
        return jdbcTemplate.queryForObject(sql, new ActivityRowMapper(), id);
    }

    @Override
    public List<Activity> getSubjectActivities(int subjectId) {
        String sql = "select * from activity where subject = ?";
        return jdbcTemplate.query(sql, new ActivityRowMapper(), subjectId);
    }

    @Override
    public List<Activity> getSubjectActivitiesByType(int subjectId, ActivityType type) {
        String sql = "select * from activity where subject = ? and type = ?";
        return jdbcTemplate.query(sql, new ActivityRowMapper(), subjectId, type.toString());
    }

    @Override
    public int createActivity(Activity activity) {
        String sql = "insert into activity (subject, type, professor) values (?, ?, ?);";
        return jdbcTemplate.update(sql,
                activity.getSubject().getId(), activity.getType().toString(), activity.getProfessor().getId());
    }
}
