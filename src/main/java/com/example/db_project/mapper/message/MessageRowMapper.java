package com.example.db_project.mapper.message;


import com.example.db_project.model.studyGroup.Message;
import com.example.db_project.model.users.Student;
import com.example.db_project.model.studyGroup.StudyGroup;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageRowMapper implements RowMapper<Message> {

    @Override
    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {

        Message message = new Message();
        message.setId(rs.getInt("id"));

        Student student = new Student();
        student.setId(rs.getInt("student"));
        message.setStudent(student);

        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setId(rs.getInt("studygroup"));
        message.setStudyGroup(studyGroup);
        message.setMessage(rs.getString("message"));

        return message;
    }
}

