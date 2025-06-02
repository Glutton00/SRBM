package com.srbms.customException;

public class CartEmptyException extends Exception {

    public CartEmptyException(String message) {
        super(message);
    }
    
}
