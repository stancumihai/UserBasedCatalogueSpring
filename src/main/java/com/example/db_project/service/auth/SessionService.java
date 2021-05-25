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
public class SessionService {

    private final SessionDao sessionDao;
    private final UserDao userDao;


    @Autowired
    public SessionService(@Qualifier("SessionRepo") SessionDao sessionDao, @Qualifier("UserRepo") UserDao userDao) {
        this.sessionDao = sessionDao;
        this.userDao = userDao;
    }

    public Optional<Session> getSessionBySessionKey(String sessionKey) {
        return sessionDao.getSessionBySessionKey(sessionKey);
    }

    public Optional<Session> createUserSession(User user){
        return sessionDao.createUserSession(user);
    }

    public int deleteSession(String sessionKey){
        return sessionDao.deleteSession(sessionKey);
    }

}
