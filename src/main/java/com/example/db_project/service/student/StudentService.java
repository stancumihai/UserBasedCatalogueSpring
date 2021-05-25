package com.example.db_project.service.student;

import com.example.db_project.dao.student.StudentDao;
import com.example.db_project.model.users.Student;
import com.example.db_project.model.users.User;
import com.example.db_project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentDao studentDao;
    private final UserService userService;

    @Autowired
    public StudentService(@Qualifier("StudentRepo") StudentDao studentDao, UserService userService) {
        this.studentDao = studentDao;
        this.userService = userService;
    }

    public Optional<Student> getStudentByUserId(int userId) {
        Optional<Student> student = studentDao.getStudentByUserId(userId);
        Optional<User> user = userService.getUserById(userId);
        if (student.isPresent() && user.isPresent()) {
            student.get().setUser(user.get());
        }
        return student;
    }

    public Optional<Student> getStudentById(int studentId) {
        Optional<Student> student = studentDao.getStudentById(studentId);

        Optional<User> user = userService.getUserById(student.get().getUser().getId());
        student.get().setUser(user.get());
        return student;
    }

    public List<Student> getAllStudents() {
        List<Student> studentList = studentDao.getAllStudents();
        for (Student student : studentList) {
            Optional<User> user = userService.getUserById(student.getUser().getId());
            if (user.isPresent()) {
                user.get().setRole("student");
                student.setUser(user.get());
            }
        }
        return studentList;
    }

    public Student createStudent(Student student) {
        Student createdStudent = studentDao.createStudent(student);
        createdStudent.setUser(userService.getUserById(student.getUser().getId()).get());
        return createdStudent;
    }

    public Student updateStudent(Student student) {
        return studentDao.updateStudent(student);
    }
}
