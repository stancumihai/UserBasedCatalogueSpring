package com.example.db_project.mapper.studyGroup;

import com.example.db_project.model.users.Student;
import com.example.db_project.model.studyGroup.StudentStudyGroup;
import com.example.db_project.model.studyGroup.StudyGroup;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentStudyGroupRowMapper implements RowMapper<StudentStudyGroup> {

    @Override
    public StudentStudyGroup mapRow(ResultSet resultSet, int i) throws SQLException {
        StudentStudyGroup studentStudyGroup = new StudentStudyGroup();

        studentStudyGroup.setId(resultSet.getInt("id"));

        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setId(resultSet.getInt("studyGroup"));

        studentStudyGroup.setStudyGroup(studyGroup);

        Student student = new Student();
        student.setId(resultSet.getInt("student"));
        studentStudyGroup.setStudent(student);

        return studentStudyGroup;

    }
}
