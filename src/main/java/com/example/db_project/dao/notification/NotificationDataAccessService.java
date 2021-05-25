package com.example.db_project.dao.notification;

import com.example.db_project.mapper.notification.NotificationRowMapper;
import com.example.db_project.model.users.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository("NotificationRepo")
public class NotificationDataAccessService implements NotificationDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Notification createNotification(Notification notification) {
        String sql = "insert into notification(user,body,title,isRead) values(:user,:body,:title,:isRead)";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("user", notification.getUser().getId())
                .addValue("body", notification.getBody())
                .addValue("title", notification.getTitle())
                .addValue("isRead", notification.isRead());
        namedParameterJdbcTemplate.update(sql, parameters, holder);
        notification.setId(Objects.requireNonNull(holder.getKey()).intValue());
        return notification;
    }

    @Override
    public List<Notification> getUserNotifications(int id) {
        String sql = "select *from notification where user = ?";
        return jdbcTemplate.query(sql, new NotificationRowMapper(), id);
    }


    @Override
    public int deleteNotification(int id) {
        String sql = "delete from notification where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int markAsRead(int id) {
        String sql = "update notification set isRead= true where id=?";
        return jdbcTemplate.update(sql, id);
    }
}
