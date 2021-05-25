package com.example.db_project.api.professorSubject;

import com.example.db_project.model.course.Activity;
import com.example.db_project.model.course.StudentActivity;
import com.example.db_project.model.course.SubjectProfessor;
import com.example.db_project.service.activity.ActivityService;
import com.example.db_project.service.professorSubject.ProfessorSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/professor_subject")
public class ProfessorSubjectController {

    private final ProfessorSubjectService professorSubjectService;
    private final ActivityService activityService;

    @Autowired
    public ProfessorSubjectController(ProfessorSubjectService professorSubjectService, ActivityService activityService) {
        this.professorSubjectService = professorSubjectService;
        this.activityService = activityService;
    }

    @PostMapping()
    public int assignProfessorToSubject(@RequestBody SubjectProfessor subjectProfessor) {
        System.out.println(subjectProfessor);
        int subjectId = subjectProfessor.getSubject().getId();
        int profId = subjectProfessor.getProfessor().getId();
        return professorSubjectService.assignProfessorToSubject(subjectId, profId);
    }

    @PostMapping(path = "/activity")
    public int createActivity(@RequestBody Activity activity) {
        return activityService.createActivity(activity);
    }


    @GetMapping(path = "{profId}/activities")
    public List<Activity> getActivitiesByProfessorId(@PathVariable int profId) {
        return professorSubjectService.getActivitiesByProfessorId(profId).orElse(null);
    }

    @GetMapping(path = "{profId}/subjects")
    public List<SubjectProfessor> getSubjectsByProfessorId(@PathVariable int profId) {
        return professorSubjectService.getSubjectsByProfessorId(profId);
    }

    @GetMapping(path = "{profId}/students")
    public List<StudentActivity> getProfessorStudents(@PathVariable("profId") int profId) {
        return professorSubjectService.getProfessorStudents(profId);
    }

}
