package com.srbms.customException;

public class UserAlreadyExistWithEmailException extends Exception {
    
    public UserAlreadyExistWithEmailException(String message) {
        super(message);
    }

}
