package com.example.db_project.mapper.studyGroup;

import com.example.db_project.model.studyGroup.EnrolledStudent;
import com.example.db_project.model.users.Student;
import com.example.db_project.model.studyGroup.StudentActivityGroup;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class EnrolledStudentRowMapper implements RowMapper<EnrolledStudent> {
    @Override
    public EnrolledStudent mapRow(ResultSet resultSet, int i) throws SQLException {
        EnrolledStudent enrolledStudent = new EnrolledStudent();
        enrolledStudent.setId(resultSet.getInt("id"));

        Student student = new Student();
        student.setId(resultSet.getInt("student"));
        enrolledStudent.setStudent(student);

        StudentActivityGroup studentActivityGroup = new StudentActivityGroup();
        studentActivityGroup.setId(resultSet.getInt("studentActivity"));
        enrolledStudent.setActivity(studentActivityGroup);

        return enrolledStudent;
    }
}
