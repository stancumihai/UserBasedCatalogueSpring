package com.example.db_project.service.auth;

import com.example.db_project.dao.auth.SessionDao;
import com.example.db_project.dao.user.UserDao;
import com.example.db_project.model.users.Session;
import com.example.db_project.model.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService {
    private final SessionDao sessionDao;
    private final UserDao userDao;

    @Autowired
    public AuthService(@Qualifier("SessionRepo") SessionDao sessionDao, @Qualifier("UserRepo") UserDao userDao) {
        this.sessionDao = sessionDao;
        this.userDao = userDao;
    }

    public boolean isAuthenticated(String sessionKey) {
        return sessionDao.isAuthenticated(sessionKey);
    }

    public boolean isStudent(String sessionKey) {
        return sessionDao.isStudent(sessionKey);
    }

    public boolean isTeacher(String sessionKey) {
        return sessionDao.isTeacher(sessionKey);
    }


    public boolean isAdmin(String sessionKey) {
        return sessionDao.isAdmin(sessionKey);
    }

    public boolean isSuperAdmin(String sessionKey) {
        return sessionDao.isSuperAdmin(sessionKey);
    }


    public User getRequestUser(String sessionKey) {
        Optional<Session> session = sessionDao.getSessionBySessionKey(sessionKey);
        if (session.isEmpty()) {
            return null;
        }
        int userId = session.get().getUserId();

        return userDao.getUserById(userId).get();
    }

    public boolean hasPermission(int userId, String action, String model) {
        return sessionDao.hasPermission(userId, action, model);
    }
}
