package com.example.db_project.api.studentSubject;


import com.example.db_project.model.course.Activity;
import com.example.db_project.model.course.StudentActivity;
import com.example.db_project.model.course.Subject;
import com.example.db_project.model.course.SubjectStudent;
import com.example.db_project.model.users.Student;
import com.example.db_project.model.users.User;
import com.example.db_project.response.ResponseBody;
import com.example.db_project.service.auth.AuthService;
import com.example.db_project.service.student.StudentService;
import com.example.db_project.service.subject.SubjectService;
import com.example.db_project.service.studentSubject.StudentSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("api/v1/user_subject")
@RestController
public class StudentSubjectController {
    private final StudentSubjectService studentSubjectService;
    private final AuthService authService;
    private final StudentService studentService;
    private final SubjectService subjectService;

    @Autowired
    public StudentSubjectController(StudentSubjectService userSubjectService, AuthService authService,
                                    StudentService studentService, SubjectService subjectService) {
        this.studentSubjectService = userSubjectService;
        this.authService = authService;
        this.studentService = studentService;
        this.subjectService = subjectService;
    }

    @GetMapping(path = "/{subjectId}/enrolled_status")
    public boolean getEnrolledStatus(@PathVariable("subjectId") int subjectId,
                                     @CookieValue(value = "Session", required = false) String sessionKey) {
        if (!authService.isAuthenticated(sessionKey)) {
            return false;
        }
        User user = authService.getRequestUser(sessionKey);

        Optional<Student> student = studentService.getStudentByUserId(user.getId());
        if (student.isEmpty()) {
            return false;
        }

        return studentSubjectService.getEnrolledStatus(subjectId, student.get().getId());
    }

    @PostMapping(path = "/{subjectId}")
    public ResponseEntity<?> enrollStudentToSubject(@PathVariable("subjectId") int subjectId,
                                                    @CookieValue(value = "Session", required = false) String sessionKey) {

        int created = studentSubjectService.enrollStudentToSubject(subjectId, sessionKey);

        if (created == -2) {
            return new ResponseEntity<>(new ResponseBody<>("Permission denied", 1), HttpStatus.OK);
        }

        if (created == -1) {
            return new ResponseEntity<>(new ResponseBody<>("No slots remaining", 1), HttpStatus.OK);
        }

        if (created == 0) {
            return new ResponseEntity<>(new ResponseBody<>("No time for activities", 1), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseBody<>("Succeeded", 0), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{subjectId}")
    public ResponseEntity<?> deleteUserFromSubject(@PathVariable("subjectId") int subjectId,
                                                   @CookieValue(value = "Session", required = false) String sessionKey) {
        User user = authService.getRequestUser(sessionKey);

        int deleted = studentSubjectService.deleteStudentFromSubject(subjectId, user);
        System.out.println(deleted);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping()
    public List<SubjectStudent> getStudentSubjects(@CookieValue(value = "Session", required = false) String sessionKey) {
        User user = authService.getRequestUser(sessionKey);
        List<SubjectStudent> subjectStudentList = studentSubjectService.getStudentSubjects(user);

        for (SubjectStudent subjectStudent : subjectStudentList) {
            subjectStudent.setSubject(subjectService.getSubjectById(subjectStudent.getSubject().getId()));
            subjectStudent.setStudent(null);
        }
        return subjectStudentList;
    }

    @GetMapping(path = "/activities")
    public List<StudentActivity> getStudentActivities(@CookieValue(value = "Session", required = false) String sessionKey) {
        User user = authService.getRequestUser(sessionKey);

        return studentSubjectService.getStudentActivities(user);
    }

    @PutMapping(path = "updateGrade")
    public void setActivityGrade(@RequestBody StudentActivity studentActivity) {
        studentSubjectService.setActivityGrade(studentActivity.getActivityGrade(), studentActivity.getId());
    }

    @GetMapping(path = "/{subjectId}/{studentId}/available_activities")
    public List<Activity> getAvailableActivities(@PathVariable("subjectId") int subjectId,
                                                 @PathVariable("studentId") int studentId) {
        Optional<Student> student = studentService.getStudentById(studentId);
        Subject subject = subjectService.getSubjectById(subjectId);
        return studentSubjectService.getAvailableActivities(student.get(), subject);
    }

    @PutMapping(path = "/activity/{studentActivityId}")
    public StudentActivity updateStudentActivity(@PathVariable("studentActivityId") int studentActivityId, @RequestBody StudentActivity studentActivity) {
        return studentSubjectService.updateStudentActivity(studentActivity);
    }

}

