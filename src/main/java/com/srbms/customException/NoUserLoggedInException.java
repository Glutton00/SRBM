package com.srbms.customException;

public class NoUserLoggedInException extends Exception{
    
    public NoUserLoggedInException(String message) {
        super(message);
    }

}
