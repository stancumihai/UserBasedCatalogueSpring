package com.example.db_project.dao.activity;

import com.example.db_project.model.course.Activity;
import com.example.db_project.model.course.ActivityType;

import java.util.List;
import java.util.Optional;

public interface ActivityDao {
    Optional<Activity> updateActivity(Activity activity, int id);

    Activity getActivityById(int id);

    List<Activity> getSubjectActivities(int subjectId);

    List<Activity> getSubjectActivitiesByType(int subjectId, ActivityType type);

    int createActivity(Activity activity);
}
