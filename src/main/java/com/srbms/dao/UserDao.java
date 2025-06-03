package com.srbms.dao;

import java.util.List;

import com.srbms.dto.User;

public interface UserDao {
   boolean addUser(User user);
    User getUserById(String userId);
    List<User> getAllUsers();
    boolean updateUser(User user);
    boolean deleteUser(String userId);
}
