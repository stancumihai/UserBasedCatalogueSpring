package com.example.db_project.api.activity;


import com.example.db_project.model.course.Activity;
import com.example.db_project.model.users.User;
import com.example.db_project.service.activity.ActivityService;
import com.example.db_project.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("api/v1/activity")
@RestController
public class ActivityController {

    private final ActivityService activityService;
    private final AuthService authService;

    @Autowired
    public ActivityController(ActivityService activityService, AuthService authService) {
        this.activityService = activityService;
        this.authService = authService;
    }

    @GetMapping(path = "/{id}")
    public Activity getActivityById(@PathVariable("id") int id) {
        return activityService.getActivityById(id);
    }

    @PutMapping(path = "/{id}")
    public Optional<Activity> updateActivity(@RequestBody Activity activity,
                                             @PathVariable int id, @CookieValue("Session") String sessionKey) {
        User user = authService.getRequestUser(sessionKey);
        if (!authService.hasPermission(user.getId(), "update", "activity")) {
            return Optional.empty();
        }

        return activityService.updateActivity(activity, id);
    }
}
