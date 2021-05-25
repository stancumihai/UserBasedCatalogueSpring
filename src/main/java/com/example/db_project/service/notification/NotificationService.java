package com.example.db_project.service.notification;


import com.example.db_project.dao.notification.NotificationDao;
import com.example.db_project.model.users.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private final NotificationDao notificationDao;

    @Autowired
    public NotificationService(@Qualifier("NotificationRepo") NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    public Notification createNotification(Notification notification) {
        return notificationDao.createNotification(notification);
    }

    public List<Notification> getUserNotifications(int id) {
        return notificationDao.getUserNotifications(id);
    }

    public int deleteNotification(int id) {
        return notificationDao.deleteNotification(id);
    }

    public int markAsRead(int id) {
        return notificationDao.markAsRead(id);
    }
}
