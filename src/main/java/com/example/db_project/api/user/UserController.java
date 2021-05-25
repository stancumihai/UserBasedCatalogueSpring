package com.example.db_project.api.user;


import com.example.db_project.model.users.*;
import com.example.db_project.response.ResponseBody;
import com.example.db_project.service.professor.ProfessorService;
import com.example.db_project.service.student.StudentService;
import com.example.db_project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("api/v1/user")
@RestController
public class UserController {
    private final UserService userService;
    private final StudentService studentService;
    private final ProfessorService professorService;

    @Autowired
    public UserController(UserService userService, StudentService studentService, ProfessorService professorService) {
        this.userService = userService;
        this.studentService = studentService;
        this.professorService = professorService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        ResponseBody<List<User>> responseBody = new ResponseBody<>("", 0, users);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") int id) {
        Optional<User> user = userService.getUserById(id);

        if (user.isEmpty()) {
            return new ResponseEntity<>(new ResponseBody<>("No user with such id", 1), HttpStatus.OK);
        }
        ResponseBody<User> responseBody = new ResponseBody<>("", 0, user.get());
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping(path = "{userId}/info")
    public ResponseEntity<?> getUserInfo(@PathVariable("userId") int userId) {
        UserInfo userInfo = userService.getUserInfo(userId);
        ResponseBody<UserInfo> responseBody = new ResponseBody<>("", 0, userInfo);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping(path = "{userId}/address")
    public ResponseEntity<?> getUserAddress(@PathVariable("userId") int userId) {
        Address address = userService.getUserAddress(userId);
        ResponseBody<Address> responseBody = new ResponseBody<>("", 0, address);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping(path = "{userId}/student")
    public Optional<Student> getStudentByUserId(@PathVariable("userId") int userId) {
        return studentService.getStudentByUserId(userId);
    }

    @GetMapping(path = "{userId}/professor")
    public Optional<Professor> getProfessorByUserId(@PathVariable("userId") int userId) {
        return professorService.getProfessorByUserId(userId);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping(path = "/info")
    public UserInfo createUserInfo(@RequestBody UserInfo userInfo) {
        return userService.createUserInfo(userInfo);
    }

    @PostMapping(path = "/address")
    public Address createUserAddress(@RequestBody Address address) {
        return userService.createUserAddress(address);
    }

    @GetMapping(path = "suggestions/{studyGroup}")
    public Optional<List<User>> getSuggestionsForStudyGroup(@PathVariable("studyGroup") int studyGroup) {
        return userService.getSuggestionsForStudyGroup(studyGroup);
    }

    @PutMapping(path = "address/{id}")
    public Optional<Address> updateAddress(@RequestBody Address address,
                                           @PathVariable int id) {
        return userService.updateAddress(address, id);
    }

    @PutMapping(path = "userInfo/{id}")
    public Optional<UserInfo> updateUserInfo(@RequestBody UserInfo userInfo,
                                             @PathVariable int id) {
        return userService.updateUserInfo(userInfo, id);
    }

    @GetMapping(path = "admins")
    public List<User> getAdmins() {
        return userService.getAllAdmins();
    }

    @PostMapping(path = "admins")
    public Optional<User> createAdmin(@RequestBody User user) {
        return userService.createAdmin(user);
    }

    @DeleteMapping(path = "{userId}")
    public int deleteUser(@PathVariable("userId") int userId) {
        return userService.deleteUser(userId);
    }

}
