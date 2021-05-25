package com.example.db_project.api.auth;

import com.example.db_project.model.*;
import com.example.db_project.model.users.Professor;
import com.example.db_project.model.users.Session;
import com.example.db_project.model.users.Student;
import com.example.db_project.model.users.User;
import com.example.db_project.response.ResponseBody;
import com.example.db_project.service.auth.AuthService;
import com.example.db_project.service.auth.SessionService;
import com.example.db_project.service.professor.ProfessorService;
import com.example.db_project.service.student.StudentService;
import com.example.db_project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequestMapping("api/v1/auth")
@RestController
public class AuthController {
    private final SessionService sessionService;
    private final UserService userService;
    private final StudentService studentService;
    private final AuthService authService;
    private final ProfessorService professorService;

    @Autowired
    public AuthController(SessionService sessionService, UserService userService, StudentService studentService, AuthService authService, ProfessorService professorService) {
        this.sessionService = sessionService;
        this.userService = userService;
        this.studentService = studentService;
        this.authService = authService;
        this.professorService = professorService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAuthCredentials(@CookieValue(value = "Session", required = false) String sessionKey) {
        if (!authService.isAuthenticated(sessionKey)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = authService.getRequestUser(sessionKey);

        if (authService.isStudent(sessionKey)) {

            Optional<Student> student = studentService.getStudentByUserId(user.getId());
            if (student.isPresent()) {
                return new ResponseEntity<>(new ResponseBody<>("student", 0, student.get()), HttpStatus.OK);
            }
        }

        if (authService.isTeacher(sessionKey)) {
            Optional<Professor> professor = professorService.getProfessorByUserId(user.getId());
            if (professor.isPresent()) {
                return new ResponseEntity<>(new ResponseBody<>("professor", 0, professor.get()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new ResponseBody<>("admin", 0, user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginBody loginBody, HttpServletResponse response) {

        Optional<User> foundUser = userService.validateUserCredentials(loginBody.getUsername(), loginBody.getPassword());

        if (foundUser.isEmpty()) {
            return new ResponseEntity<>(new ResponseBody<>("Incorrect credentials", 1), HttpStatus.OK);
        }

        Optional<Session> session = sessionService.createUserSession(foundUser.get());
        if (session.isEmpty()) {
            return new ResponseEntity<>(new ResponseBody<>("Service is not working", 1), HttpStatus.OK);
        }

        Cookie cookie = new Cookie("Session", session.get().getSessionKey());
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);

        ResponseBody<Integer> responseBody = new ResponseBody<>(
                "Login success", 0, foundUser.get().getId());

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(value = "Session") String sessionKey) {
        int deleted = sessionService.deleteSession(sessionKey);
        if (deleted == 1) {
            return new ResponseEntity<>(new ResponseBody<>("Logout Success", 0), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseBody<>("Logout failed", 1), HttpStatus.OK);
    }

}
