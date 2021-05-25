package com.example.db_project.mapper.user;

import com.example.db_project.model.users.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
//        user.setPassword(rs.getString("password"));
        user.setActive(rs.getBoolean("is_active"));
        user.setRole(rs.getString("role"));
        return user;

    }
}
