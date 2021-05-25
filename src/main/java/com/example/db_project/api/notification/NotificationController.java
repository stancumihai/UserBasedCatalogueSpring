package com.example.db_project.api.notification;


import com.example.db_project.model.users.Notification;
import com.example.db_project.service.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @PostMapping()
    public Notification createNotification(@RequestBody Notification notification) {
        return notificationService.createNotification(notification);
    }

    @GetMapping(path = "{id}")
    public List<Notification> getUserNotifications(@PathVariable int id) {
        return notificationService.getUserNotifications(id);
    }

    @DeleteMapping(path = "{id}")
    public int deleteNotification(@PathVariable int id) {
        return notificationService.deleteNotification(id);
    }

    @PutMapping(path = "setAsRead/{id}")
    public int markAsRead(@PathVariable int id) {
        return notificationService.markAsRead(id);
    }
}
