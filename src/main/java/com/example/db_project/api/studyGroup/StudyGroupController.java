package com.example.db_project.api.studyGroup;

import com.example.db_project.model.studyGroup.StudentActivityGroup;
import com.example.db_project.model.studyGroup.StudentStudyGroup;
import com.example.db_project.model.studyGroup.StudyGroup;
import com.example.db_project.model.users.Student;
import com.example.db_project.model.users.User;
import com.example.db_project.service.auth.AuthService;
import com.example.db_project.service.student.StudentService;
import com.example.db_project.service.studentSubject.StudentSubjectService;
import com.example.db_project.service.studyGroup.StudyGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/v1/study_group")
public class StudyGroupController {
    private final StudyGroupService studyGroupService;
    private final StudentSubjectService studentSubjectService;
    private final AuthService authService;
    private final StudentService studentService;

    @Autowired
    public StudyGroupController(StudyGroupService studyGroupService, StudentSubjectService studentSubjectService,
                                AuthService authService, StudentService studentService) {
        this.studyGroupService = studyGroupService;
        this.studentSubjectService = studentSubjectService;
        this.authService = authService;
        this.studentService = studentService;
    }

    @GetMapping(path = "/{studyGroupId}")
    public StudyGroup getStudyGroupById(@PathVariable("studyGroupId") int studyGroupId) {
        return studyGroupService.getStudyGroupById(studyGroupId);
    }

    @GetMapping(path = "/{studyGroupId}/activities")
    public List<StudentActivityGroup> getStudyGroupActivities(@PathVariable("studyGroupId") int studyGroupId) {
        return studyGroupService.getStudyGroupActivities(studyGroupId);
    }

    @GetMapping(path = "/{studyGroupId}/students")
    public List<StudentStudyGroup> getStudyGroupStudents(@PathVariable("studyGroupId") int studyGroupId) {
        return studyGroupService.getStudyGroupStudents(studyGroupId);
    }

    @GetMapping(path = "/{studyGroupId}/enrolled_status")
    public StudentStudyGroup getStudyGroupEnrolledStatus(@PathVariable("studyGroupId") int studyGroupId,
                                                         @CookieValue("Session") String sessionKey) {
        User user = authService.getRequestUser(sessionKey);
        if (user == null) {
            return null;
        }
        Optional<Student> student = studentService.getStudentByUserId(user.getId());
        return studyGroupService.getEnrolledStatus(studyGroupId, student.get().getId());
    }

    @PostMapping(path = "/enroll")
    public StudentStudyGroup enrollStudentToStudyGroup(@RequestBody StudentStudyGroup studentStudyGroup) {
        System.out.println(studentStudyGroup.toString());
        if (studentSubjectService.getEnrolledStatus(studentStudyGroup.getStudyGroup().getSubject().getId(),
                studentStudyGroup.getStudent().getId()))
            return studyGroupService.enrollStudentToStudyGroup(studentStudyGroup);
        else
            return null;
    }

    @GetMapping(path = "by_subject_id/{subjectId}")
    public List<StudyGroup> getStudyGroupsBySubjectId(@PathVariable("subjectId") int subjectId) {
        return studyGroupService.getStudyGroupsBySubjectId(subjectId);
    }

    @DeleteMapping(path = "/quit/{studentStudyGroupId}")
    public int quitStudyGroup(@PathVariable("studentStudyGroupId") int studentStudyGroupId) {
        return studyGroupService.quitStudyGroup(studentStudyGroupId);
    }

    @PostMapping
    public StudyGroup createStudyGroup(@RequestBody StudyGroup studyGroup) {
        System.out.println(studyGroup.toString());
        return studyGroupService.createStudyGroup(studyGroup);
    }

    @GetMapping(path = "{id}/suggestions")
    public List<Student> getGroupSuggestions(@PathVariable("id") int id) {
        return studyGroupService.getStudyGroupSuggestions(id);
    }

    @DeleteMapping(path = "{id}")
    public int deleteStudyGroup(@PathVariable("id") int id) {
        return studyGroupService.deleteStudyGroup(id);
    }

}
