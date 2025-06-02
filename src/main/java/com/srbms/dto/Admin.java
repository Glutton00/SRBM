package com.srbms.dto;

public class Admin extends User {

    public Admin(String userID, String userName, String userPhoneNumber, String userEmail, String userPassword) {
        setUserID(userID);
        setUserName(userName);
        setUserPhoneNumber(userPhoneNumber);
        setUserEmail(userEmail);
        setUserPassword(userPassword);
        setRole("ADMIN");
    }
}

