package com.example.db_project.mapper.studyGroup;

import com.example.db_project.model.studyGroup.StudyGroup;
import com.example.db_project.model.course.Subject;
import com.example.db_project.model.users.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class StudyGroupRowMapper implements RowMapper<StudyGroup> {

    @Override
    public StudyGroup mapRow(ResultSet resultSet, int i) throws SQLException {

        StudyGroup studyGroup = new StudyGroup();

        studyGroup.setId(resultSet.getInt("id"));
        Subject subject = new Subject();
        subject.setId(resultSet.getInt("subject"));
        studyGroup.setSubject(subject);
        User user = new User();
        user.setId(resultSet.getInt("creator"));
        studyGroup.setCreator(user);
        studyGroup.setName(resultSet.getString("name"));

        return studyGroup;

    }
}

