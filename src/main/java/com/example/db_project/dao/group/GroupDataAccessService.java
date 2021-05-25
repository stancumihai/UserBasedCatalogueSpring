package com.example.db_project.dao.group;

import com.example.db_project.mapper.group.GroupMapper;
import com.example.db_project.model.permission.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("GroupRepo")
public class GroupDataAccessService implements GroupDao {
    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public Group getGroupByName(String name) {
        String sql = "select * from `group` where name = ?";
        return jdbcTemplate.queryForObject(sql, new GroupMapper(), name);
    }
}
