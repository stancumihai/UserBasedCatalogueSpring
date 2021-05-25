package com.example.db_project.service.professorSubject;


import com.example.db_project.dao.userSubject.UserSubjectDao;
import com.example.db_project.model.course.Activity;
import com.example.db_project.model.users.Professor;
import com.example.db_project.model.course.StudentActivity;
import com.example.db_project.model.course.SubjectProfessor;
import com.example.db_project.service.activity.ActivityService;
import com.example.db_project.service.professor.ProfessorService;
import com.example.db_project.service.student.StudentService;
import com.example.db_project.service.subject.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorSubjectService {
    private final UserSubjectDao userSubjectDao;
    private final ProfessorService professorService;
    private final SubjectService subjectService;
    private final StudentService studentService;
    private final ActivityService activityService;

    @Autowired
    public ProfessorSubjectService(UserSubjectDao userSubjectDao, ProfessorService professorService, SubjectService subjectService, StudentService studentService, ActivityService activityService) {
        this.userSubjectDao = userSubjectDao;
        this.professorService = professorService;
        this.subjectService = subjectService;
        this.studentService = studentService;
        this.activityService = activityService;
    }

    public int assignProfessorToSubject(int subjectId, int professorId) {
        return userSubjectDao.assignProfessorToSubject(subjectId, professorId);
    }

    public List<SubjectProfessor> getSubjectProfessorBySubjectId(int subjectId) {
        List<SubjectProfessor> subjectProfessorList = userSubjectDao.getSubjectProfessorsBySubjectId(subjectId);
        for (SubjectProfessor subjectProfessor : subjectProfessorList) {
            setSubjectProfessorData(subjectProfessor);
        }
        return subjectProfessorList;
    }

    public List<SubjectProfessor> getSubjectsByProfessorId(int profId) {
        List<SubjectProfessor> subjectProfessorList = userSubjectDao.getSubjectsByProfessorId(profId);

        for (SubjectProfessor subjectProfessor : subjectProfessorList) {
            setSubjectProfessorData(subjectProfessor);
        }

        return subjectProfessorList;
    }

    public Optional<List<Activity>> getActivitiesByProfessorId(int profId) {
        return userSubjectDao.getActivitiesByProfessorId(profId);
    }

    public List<StudentActivity> getProfessorStudents(int profId) {
        List<StudentActivity> studentActivityList = userSubjectDao.getProfessorStudents(profId);
        for (StudentActivity studentActivity : studentActivityList) {
            studentActivity.setActivity(activityService.getActivityById(studentActivity.getActivity().getId()));
            studentActivity.setStudent(studentService.getStudentById(studentActivity.getStudent().getId()).get());
        }
        return studentActivityList;
    }

    private void setSubjectProfessorData(SubjectProfessor subjectProfessor) {
        int professorId = subjectProfessor.getProfessor().getId();
        Optional<Professor> professor = professorService.getProfessorById(professorId);
        subjectProfessor.setProfessor(professor.get());

        int subjectId = subjectProfessor.getSubject().getId();
        subjectProfessor.setSubject(subjectService.getSubjectById(subjectId));
    }

    public float getProfessorNumberOfHours(int professorId) {
        float numberOfHours = 0;

        List<Activity> activities = getActivitiesByProfessorId(professorId).get();
        for (Activity activity : activities) {
            numberOfHours += activity.getDuration();
        }

        return numberOfHours;
    }

}
