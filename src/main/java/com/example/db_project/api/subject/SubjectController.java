package com.example.db_project.api.subject;


import com.example.db_project.model.course.*;
import com.example.db_project.service.activity.ActivityService;
import com.example.db_project.service.professorSubject.ProfessorSubjectService;
import com.example.db_project.service.subject.SubjectService;
import com.example.db_project.service.studentSubject.StudentSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RequestMapping("api/v1/subject")
@RestController
public class SubjectController {
    private final SubjectService subjectService;
    private final StudentSubjectService userSubjectService;
    private final ProfessorSubjectService professorSubjectService;
    private final ActivityService activityService;

    @Autowired
    public SubjectController(SubjectService subjectService, StudentSubjectService userSubjectService, ProfessorSubjectService professorSubjectService, ActivityService activityService) {
        this.subjectService = subjectService;
        this.userSubjectService = userSubjectService;
        this.professorSubjectService = professorSubjectService;
        this.activityService = activityService;
    }

    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping(path = "/{subjectId}")
    public Subject getSubjectById(@PathVariable("subjectId") int subjectId) {
        return subjectService.getSubjectById(subjectId);
    }

    @GetMapping(path = "/{id}/activities")
    public List<Activity> getSubjectActivities(@PathVariable("id") int id) {
        return activityService.getSubjectActivities(id);
    }

    @GetMapping(path = "/{id}/{type}")
    public List<Activity> getSubjectActivitiesByType(@PathVariable("id") int id, @PathVariable("type") String type) {
        try {
            return activityService.getSubjectActivitiesByType(id, ActivityType.valueOf(type.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Collections.emptyList();
        }
    }

    @GetMapping(path = "/{subjectId}/students")
    public List<SubjectStudent> getSubjectStudents(@PathVariable("subjectId") int subjectId) {
        return userSubjectService.getSubjectStudentsBySubjectId(subjectId);
    }

    @GetMapping(path = "/{subjectId}/professors")
    public List<SubjectProfessor> getSubjectProfessors(@PathVariable("subjectId") int subjectId) {
        return professorSubjectService.getSubjectProfessorBySubjectId(subjectId);
    }

    @GetMapping(path = "/{subjectId}/ponders")
    public List<SubjectActivityPonder> getSubjectPonders(@PathVariable("subjectId") int subjectId) {
        return subjectService.getSubjectActivityPonders(subjectId);
    }

    @PutMapping(path = "/updatePonder")
    public void insertCorrectPonder(@RequestBody SubjectActivityPonder subjectActivityPonder) {
        subjectService.insertPonder(subjectActivityPonder);
    }

}
