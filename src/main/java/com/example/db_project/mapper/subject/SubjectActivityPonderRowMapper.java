package com.example.db_project.mapper.subject;

import com.example.db_project.model.course.ActivityType;
import com.example.db_project.model.course.Subject;
import com.example.db_project.model.course.SubjectActivityPonder;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectActivityPonderRowMapper implements RowMapper<SubjectActivityPonder> {
    @Override
    public SubjectActivityPonder mapRow(ResultSet resultSet, int i) throws SQLException {
        SubjectActivityPonder subjectActivityPonder = new SubjectActivityPonder();

        subjectActivityPonder.setId(resultSet.getInt("id"));
        subjectActivityPonder.setPonder(resultSet.getInt("pondere"));
        subjectActivityPonder.setType(ActivityType.valueOf(resultSet.getString("type").toUpperCase()));
        Subject subject = new Subject();
        subject.setId(resultSet.getInt("subject"));
        subjectActivityPonder.setSubject(subject);
        return subjectActivityPonder;
    }
}
