package com.example.db_project.service.user;

import com.example.db_project.dao.user.UserDao;
import com.example.db_project.model.permission.Group;
import com.example.db_project.model.permission.UserGroup;
import com.example.db_project.model.users.Address;
import com.example.db_project.model.users.User;
import com.example.db_project.model.users.UserInfo;
import com.example.db_project.service.group.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserDao userDao;
    private final GroupService groupService;

    @Autowired
    public UserService(@Qualifier("UserRepo") UserDao userDao, GroupService groupService) {
        this.userDao = userDao;
        this.groupService = groupService;
    }

    public List<User> getAllUsers() {
        return userDao.listAllUsers();
    }

    public Optional<User> getUserById(int id) {
        return userDao.getUserById(id);
    }

    public Optional<User> validateUserCredentials(String username, String password) {
        if (userDao.validateUserCredentials(username, password)) {
            return userDao.getUserByUsername(username);
        }
        return Optional.empty();
    }

    public UserInfo getUserInfo(int userId) {
        return userDao.getUserInfo(userId);
    }

    public Address getUserAddress(int userId) {
        return userDao.getUserAddress(userId);
    }

    public User createUser(User user) {
        return userDao.createUser(user);
    }

    public User updateUser(User user) {
        return userDao.updateUser(user);
    }

    public UserInfo createUserInfo(UserInfo userInfo) {
        return userDao.createUserInfo(userInfo);
    }

    public Address createUserAddress(Address address) {
        return userDao.createUserAddress(address);
    }

    public Optional<List<User>> getSuggestionsForStudyGroup(int studyGroup) {
        return userDao.getSuggestionsForStudyGroup(studyGroup);
    }

    public Optional<Address> updateAddress(Address address, int id) {
        return userDao.updateAddress(address, id);
    }

    public Optional<UserInfo> updateUserInfo(UserInfo userInfo, int id) {
        return userDao.updateUserInfo(userInfo, id);
    }

    public List<User> getAllAdmins() {
        return userDao.getAdmins();
    }

    public int deleteUser(int userId) {
        return userDao.deleteUser(userId);
    }

    public Optional<User> createAdmin(User user) {
        User createdUser = createUser(user);

        Group group = groupService.getGroupByName("admin");
        UserGroup userGroup = new UserGroup();

        userGroup.setGroup(group);
        userGroup.setUser(createdUser);
        groupService.saveUserRole(userGroup);
        return getUserById(createdUser.getId());
    }
}



