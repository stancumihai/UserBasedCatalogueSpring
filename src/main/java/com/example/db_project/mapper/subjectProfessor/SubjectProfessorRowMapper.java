package com.example.db_project.mapper.subjectProfessor;

import com.example.db_project.model.users.Professor;
import com.example.db_project.model.course.Subject;
import com.example.db_project.model.course.SubjectProfessor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectProfessorRowMapper implements RowMapper<SubjectProfessor> {
    @Override
    public SubjectProfessor mapRow(ResultSet resultSet, int i) throws SQLException {
        SubjectProfessor subjectProfessor = new SubjectProfessor();
        subjectProfessor.setId(resultSet.getInt("id"));
        Subject subject = new Subject();
        subject.setId(resultSet.getInt("subject"));
        subjectProfessor.setSubject(subject);
        Professor professor = new Professor();
        professor.setId(resultSet.getInt("professor"));
        subjectProfessor.setProfessor(professor);

        return subjectProfessor;
    }
}
