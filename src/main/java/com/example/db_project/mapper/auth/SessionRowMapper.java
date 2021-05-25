package com.example.db_project.mapper.auth;

import com.example.db_project.model.users.Session;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionRowMapper implements RowMapper<Session> {
    @Override
    public Session mapRow(ResultSet resultSet, int i) throws SQLException {
        Session session = new Session();
        session.setSessionKey(resultSet.getString("sessionKey"));
        session.setUserId(resultSet.getInt("userId"));
        session.setExpiryDate(resultSet.getDate("expiryDate"));
        return session;
    }
}
