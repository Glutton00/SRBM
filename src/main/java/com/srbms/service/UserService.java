package com.srbms.service;


import com.srbms.customException.InvalidCredentialsException;
import com.srbms.customException.NoUserLoggedInException;
import com.srbms.customException.UserAlreadyExistWithEmailException;
import com.srbms.dto.User;

import java.util.List;

public interface UserService {
    boolean registerUser(User user) throws UserAlreadyExistWithEmailException;
    boolean loginUser(String email, String password) throws InvalidCredentialsException;
    void logOutUser(String userId) throws NoUserLoggedInException;
  
    boolean removeUser(String userid);
    List<User> getAllUsers();
}

