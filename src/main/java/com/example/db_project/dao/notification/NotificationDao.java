package com.example.db_project.dao.notification;

import com.example.db_project.model.users.Notification;

import java.util.List;

public interface NotificationDao {
    Notification createNotification(Notification notification);

    List<Notification> getUserNotifications(int id);

    int deleteNotification(int id);

    int markAsRead(int id);
}
