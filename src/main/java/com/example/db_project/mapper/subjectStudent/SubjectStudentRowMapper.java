package com.example.db_project.mapper.subjectStudent;

import com.example.db_project.model.users.Student;
import com.example.db_project.model.course.Subject;
import com.example.db_project.model.course.SubjectStudent;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectStudentRowMapper implements RowMapper<SubjectStudent> {
    @Override
    public SubjectStudent mapRow(ResultSet resultSet, int i) throws SQLException {
        SubjectStudent subjectStudent = new SubjectStudent();

        subjectStudent.setId(resultSet.getInt("id"));
        subjectStudent.setFinalGrade(resultSet.getFloat("subjectGrade"));

        Student student = new Student();
        student.setId(resultSet.getInt("student"));
        subjectStudent.setStudent(student);

        Subject subject = new Subject();
        subject.setId(resultSet.getInt("subject"));
        subjectStudent.setSubject(subject);

        return subjectStudent;
    }
}
