package com.example.db_project.dao.group;

import com.example.db_project.model.permission.Group;

public interface GroupDao {
    Group getGroupByName(String name);
}
