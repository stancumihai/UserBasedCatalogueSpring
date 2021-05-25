package com.example.db_project.api.adminSubject;


import com.example.db_project.model.course.Subject;
import com.example.db_project.response.ResponseBody;
import com.example.db_project.service.auth.AuthService;
import com.example.db_project.service.subject.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("api/v1/admin/subject")
public class AdminSubjectController {

    private final SubjectService subjectService;

    private final AuthService authService;

    @Autowired
    public AdminSubjectController(SubjectService subjectService, AuthService authService) {
        this.subjectService = subjectService;
        this.authService = authService;
    }

    @PostMapping()
    public Optional<Subject> createSubject(@RequestBody Subject subject, @CookieValue("Session") String sessionKey) {
        if (!(authService.isAdmin(sessionKey) || authService.isSuperAdmin(sessionKey))) {
            return Optional.empty();
        }

        return subjectService.createSubject(subject);
    }

    @PutMapping(path = "/{id}")
    public Optional<Subject> updateSubject(@RequestBody Subject subject, @CookieValue("Session") String sessionKey) {
        if (!(authService.isAdmin(sessionKey) || authService.isSuperAdmin(sessionKey))) {
            return Optional.empty();
        }
        return subjectService.updateSubject(subject);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteSubject(@CookieValue("Session") String sessionKey, @PathVariable("id") int id) {
        if (!(authService.isAdmin(sessionKey) || authService.isSuperAdmin(sessionKey))) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        boolean deleted = subjectService.deleteSubject(id);
        if (deleted) {
            return new ResponseEntity<>(new ResponseBody<>("", 0, id), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseBody<>("Error", 1, null), HttpStatus.OK);

    }

}
