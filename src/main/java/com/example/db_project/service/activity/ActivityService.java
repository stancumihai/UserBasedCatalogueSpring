package com.example.db_project.service.activity;

import com.example.db_project.dao.activity.ActivityDao;
import com.example.db_project.model.course.Activity;
import com.example.db_project.model.course.ActivityType;
import com.example.db_project.model.users.Professor;
import com.example.db_project.model.course.Subject;
import com.example.db_project.service.professor.ProfessorService;
import com.example.db_project.service.subject.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {
    private final ActivityDao activityDao;
    private final ProfessorService professorService;
    private final SubjectService subjectService;

    @Autowired
    public ActivityService(@Qualifier("ActivityRepo") ActivityDao activityDao, ProfessorService professorService, SubjectService subjectService) {
        this.activityDao = activityDao;
        this.professorService = professorService;
        this.subjectService = subjectService;
    }

    public Optional<Activity> updateActivity(Activity activity, int id){
        return activityDao.updateActivity(activity,id);
    }

    public int createActivity(Activity activity) {
        return activityDao.createActivity(activity);
    }

    public Activity getActivityById(int id) {
        Activity activity = activityDao.getActivityById(id);
        setActivityProfessor(activity);
        setActivitySubject(activity);
        return activity;
    }

    public List<Activity> getSubjectActivitiesByType(int subjectId, ActivityType type) {
        return activityDao.getSubjectActivitiesByType(subjectId, type);
    }


    public List<Activity> getSubjectActivities(int subjectId) {
        List<Activity> activityList = activityDao.getSubjectActivities(subjectId);

        for (Activity activity : activityList) {
            setActivityProfessor(activity);
            setActivitySubject(activity);
        }
        return activityList;
    }

    private void setActivityProfessor(Activity activity) {
        int professorId = activity.getProfessor().getId();
        Optional<Professor> professor = professorService.getProfessorById(professorId);
        professor.ifPresent(activity::setProfessor);
    }

    private void setActivitySubject(Activity activity) {
        int subjectID = activity.getSubject().getId();
        Subject subject = subjectService.getSubjectById(subjectID);
        activity.setSubject(subject);
    }
}
