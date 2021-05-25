package com.example.db_project.mapper.user;

import com.example.db_project.model.users.Student;
import com.example.db_project.model.users.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet resultSet, int i) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getInt("id"));
        student.setNumberOfHours(resultSet.getInt("numberOfHours"));
        student.setYearOfStudy(resultSet.getInt("yearOfStudy"));
        User user = new User();
        user.setId(resultSet.getInt("user"));
        student.setUser(user);
        return student;
    }
}
