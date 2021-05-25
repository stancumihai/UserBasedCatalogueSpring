package com.example.db_project.dao.user;

import com.example.db_project.model.users.Address;
import com.example.db_project.model.users.User;
import com.example.db_project.model.users.UserInfo;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<User> listAllUsers();

    Optional<User> getUserById(int id);

    Optional<User> getUserByUsername(String username);

    boolean validateUserCredentials(String username, String password);

    UserInfo getUserInfo(int userId);

    Address getUserAddress(int userId);

    User createUser(User user);

    User updateUser(User user);

    UserInfo createUserInfo(UserInfo userInfo);

    Address createUserAddress(Address address);

    Optional<List<User>> getSuggestionsForStudyGroup(int studyGroup);

    Optional<Address> updateAddress(Address address, int id);

    Optional<UserInfo> updateUserInfo(UserInfo userInfo, int id);

    List<User> getAdmins();

    int deleteUser(int userId);
}
