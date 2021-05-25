package com.example.db_project.mapper.user;

import com.example.db_project.model.users.UserInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserInfoRowMapper implements RowMapper<UserInfo> {
    @Override
    public UserInfo mapRow(ResultSet resultSet, int i) throws SQLException {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(resultSet.getInt("id"));
        userInfo.setUserId(resultSet.getInt("userId"));
        userInfo.setName(resultSet.getString("name"));
        userInfo.setSurname(resultSet.getString("surname"));
        userInfo.setCNP(resultSet.getString("cnp"));
        return userInfo;
    }
}
