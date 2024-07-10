package com.revature.repository;

import com.revature.entity.User;

import java.util.List;

public interface UserDAO {
    User createUser(User newUserCredentials);
    List<User> getAllUsers();
    int getUserId(User user);
}
