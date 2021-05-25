package com.example.db_project.dao.userPermissionGroup;

import com.example.db_project.model.permission.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository("UserGroupRepo")
public class UserPermissionGroupDataAccessService implements UserPermissionGroupDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public UserGroup save(UserGroup userGroup) {
        System.out.println("save userGroup" + userGroup);
        String sql = "insert into usergroup (groupId, userId) VALUES (:groupId, :userId)";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("userId", userGroup.getUser().getId())
                .addValue("groupId", userGroup.getGroup().getId());
        namedParameterJdbcTemplate.update(sql, parameters, holder);
        userGroup.setId(Objects.requireNonNull(holder.getKey()).intValue());
        return userGroup;
    }
}
