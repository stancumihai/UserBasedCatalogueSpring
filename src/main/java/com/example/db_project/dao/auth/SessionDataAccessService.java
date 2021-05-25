package com.example.db_project.dao.auth;

import com.example.db_project.mapper.auth.SessionRowMapper;
import com.example.db_project.model.users.Session;
import com.example.db_project.model.users.User;
import com.example.db_project.utils.SessionKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository("SessionRepo")
public class SessionDataAccessService implements SessionDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Session> getSessionBySessionKey(String sessionKey) {
        try {
            String sql = "SELECT * FROM SESSION WHERE sessionKey = ?";
            Session res = jdbcTemplate.queryForObject(sql, new SessionRowMapper(), sessionKey);
            assert res != null;
            return Optional.of(res);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Session> createUserSession(User user) {
        String sql = "INSERT INTO SESSION (sessionKey, userId, expiryDate) values (?, ?, ?)";

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        String sessionKey = new SessionKeyGenerator().generateKey();

        int res = jdbcTemplate.update(sql, sessionKey, user.getId(), tomorrow);
        if (res > 0) {
            return Optional.of(new Session(sessionKey, user.getId(), tomorrow));
        }
        return Optional.empty();
    }

    @Override
    public int deleteSession(String sessionKey) {
        String sql = "DELETE FROM SESSION WHERE sessionKey = ?";
        Object[] args = new Object[]{sessionKey};

        return jdbcTemplate.update(sql, args);
    }

    @Override
    public boolean isAuthenticated(String sessionKey) {
        return isUserState(sessionKey, "isAuthenticated");
    }

    @Override
    public boolean isStudent(String sessionKey) {
        return isUserState(sessionKey, "isStudentUser");
    }

    @Override
    public boolean isTeacher(String sessionKey) {
        return isUserState(sessionKey, "isTeacherUser");
    }

    @Override
    public boolean isAdmin(String sessionKey) {
        return isUserState(sessionKey, "isAdminUser");
    }

    @Override
    public boolean isSuperAdmin(String sessionKey) {
        return isUserState(sessionKey, "isSuperAdminUser");
    }

    @Override
    public boolean hasPermission(int userId, String action, String model) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("checkPermission");
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("inUserId", userId)
                .addValue("userAction", action)
                .addValue("actionModel", model);

        Map<String, Object> out = jdbcCall.execute(in);
        System.out.println(out);
        return (int) out.get("haspermission") == 1;
    }

    private boolean isUserState(String sessionKey, String procedureName) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName);
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("inSessionKey", sessionKey);

        Map<String, Object> out = jdbcCall.execute(in);
        return (int) out.get("res") == 1;
    }

}

