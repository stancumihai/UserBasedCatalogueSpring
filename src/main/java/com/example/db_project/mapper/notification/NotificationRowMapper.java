package com.example.db_project.mapper.notification;

import com.example.db_project.model.users.Notification;
import com.example.db_project.model.users.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationRowMapper implements RowMapper<Notification> {
    @Override
    public Notification mapRow(ResultSet resultSet, int i) throws SQLException {
        Notification notification = new Notification();
        notification.setId(resultSet.getInt("id"));
        User user = new User();
        user.setId(resultSet.getInt("user"));
        notification.setUser(user);
        notification.setBody(resultSet.getString("body"));
        notification.setTitle(resultSet.getString("title"));
        notification.setRead(resultSet.getBoolean("isRead"));

        return notification;
    }
}
