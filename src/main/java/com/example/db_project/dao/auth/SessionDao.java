package com.example.db_project.dao.auth;

import com.example.db_project.model.users.Session;
import com.example.db_project.model.users.User;

import java.util.Optional;

public interface SessionDao {

    Optional<Session> getSessionBySessionKey(String sessionKey);

    Optional<Session> createUserSession(User user);

    int deleteSession(String sessionKey);

    boolean isAuthenticated(String sessionKey);

    boolean isStudent(String sessionKey);

    boolean isTeacher(String sessionKey);

    boolean isAdmin(String sessionKey);

    boolean isSuperAdmin(String sessionKey);

    boolean hasPermission(int userId, String action, String model);
}
