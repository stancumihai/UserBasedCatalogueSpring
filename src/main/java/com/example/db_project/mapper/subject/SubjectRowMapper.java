package com.example.db_project.mapper.subject;

import com.example.db_project.model.course.Subject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectRowMapper implements RowMapper<Subject> {
    @Override
    public Subject mapRow(ResultSet resultSet, int i) throws SQLException {
        Subject subject = new Subject();
        subject.setId(resultSet.getInt("id"));
        subject.setDescription(resultSet.getString("description"));
        subject.setMaxStudents(resultSet.getInt("maxStudents"));
        subject.setName(resultSet.getString("name"));
        return subject;
    }
}
