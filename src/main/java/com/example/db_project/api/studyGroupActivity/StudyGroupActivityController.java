package com.example.db_project.api.studyGroupActivity;


import com.example.db_project.model.studyGroup.EnrolledStudent;
import com.example.db_project.model.users.Student;
import com.example.db_project.model.studyGroup.StudentActivityGroup;
import com.example.db_project.model.users.User;
import com.example.db_project.service.auth.AuthService;
import com.example.db_project.service.student.StudentService;
import com.example.db_project.service.studyGroup.StudyGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/group_activity")
public class StudyGroupActivityController {
    private final StudyGroupService studyGroupService;
    private final AuthService authService;
    private final StudentService studentService;

    @Autowired
    public StudyGroupActivityController(StudyGroupService studyGroupService, AuthService authService, StudentService studentService) {
        this.studyGroupService = studyGroupService;
        this.authService = authService;
        this.studentService = studentService;
    }

    @GetMapping(path = "/{activityId}/students")
    public List<EnrolledStudent> getGroupActivityEnrolledStudents(@PathVariable("activityId") int activityId) {
        return studyGroupService.getGroupActivityEnrolledStudents(activityId);
    }

    @PostMapping(path = "/{activityId}/enroll")
    public EnrolledStudent enrollStudentToGroupActivity(@PathVariable("activityId") int activityId,
                                                        @CookieValue("Session") String sessionKey) {
        User user = authService.getRequestUser(sessionKey);
        if (user == null) {
            return null;
        }
        Optional<Student> student = studentService.getStudentByUserId(user.getId());
        return student.map(value -> studyGroupService.enrollStudentToGroupActivity(activityId, value.getId())).orElse(null);
    }

    @PostMapping
    public StudentActivityGroup createStudentActivityGroup(@RequestBody StudentActivityGroup studentActivityGroup) {
        return studyGroupService.createStudyGroupActivity(studentActivityGroup);
    }

    @DeleteMapping(path = "/quit/{enrolledStudentId}")
    public int quitGroupActivity(@PathVariable("enrolledStudentId") int enrolledStudentId) {
        return studyGroupService.quitGroupActivity(enrolledStudentId);
    }

}
