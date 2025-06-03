package com.srbms.dao;

import java.util.ArrayList;
import java.util.List;

import com.srbms.dto.User;
import com.srbms.util.CollectionUtil;

public class UserDaoImplementation implements UserDao{
  private List<User> userRepo = CollectionUtil.userRepo;

    @Override
    public boolean addUser(User user) {
        if (userRepo.contains(user)) {
            return false;
        }
        userRepo.add(user);
        return true;
    }

    @Override
    public User getUserById(String userId) {
        for (User u : userRepo) {
            if (u.getUserID().equals(userId)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userRepo);
    }

    @Override
    public boolean updateUser(User user) {
        int index = userRepo.indexOf(user);
        if (index != -1) {
            userRepo.set(index, user);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(String userId) {
        return userRepo.removeIf(u -> u.getUserID().equals(userId));
    }
  
}
