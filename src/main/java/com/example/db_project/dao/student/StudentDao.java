package com.example.db_project.dao.student;

import com.example.db_project.model.users.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDao {

    List<Student> getAllStudents();

    Optional<Student> getStudentById(int studentId);

    Optional<Student> getStudentByUserId(int userId);

    Student createStudent(Student student);

    Student updateStudent(Student student);
}
