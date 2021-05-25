package com.example.db_project.mapper.user;

import com.example.db_project.model.users.Department;
import com.example.db_project.model.users.Professor;
import com.example.db_project.model.users.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessorRowMapper implements RowMapper<Professor> {
    @Override
    public Professor mapRow(ResultSet resultSet, int i) throws SQLException {
        Professor professor = new Professor();
        professor.setId(resultSet.getInt("id"));
        Department department = new Department();
        department.setId(resultSet.getInt("department"));
        professor.setDepartment(department);
        professor.setMaxHours(resultSet.getInt("maxHours"));
        professor.setMinHours(resultSet.getInt("minHours"));
        User user = new User();
        user.setId(resultSet.getInt("user"));
        professor.setUser(user);
        return professor;
    }
}
