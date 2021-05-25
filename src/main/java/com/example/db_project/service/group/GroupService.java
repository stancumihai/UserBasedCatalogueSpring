package com.example.db_project.service.group;

import com.example.db_project.dao.group.GroupDao;
import com.example.db_project.dao.userPermissionGroup.UserPermissionGroupDao;
import com.example.db_project.model.permission.Group;
import com.example.db_project.model.permission.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    private final GroupDao groupDao;
    private final UserPermissionGroupDao userPermissionGroupDao;

    @Autowired
    public GroupService(@Qualifier("GroupRepo") GroupDao groupDao,
                        @Qualifier("UserGroupRepo") UserPermissionGroupDao userPermissionGroupDao) {
        this.groupDao = groupDao;
        this.userPermissionGroupDao = userPermissionGroupDao;
    }

    public Group getGroupByName(String name) {
        return groupDao.getGroupByName(name);
    }

    public UserGroup saveUserRole(UserGroup userGroup){
        return userPermissionGroupDao.save(userGroup);
    }
}
