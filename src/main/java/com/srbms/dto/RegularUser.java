package com.srbms.dto;

public class RegularUser extends User {

    public RegularUser(String userID, String userName, String userPhoneNumber, String userEmail, String userPassword) {
        setUserID(userID);
        setUserName(userName);
        setUserPhoneNumber(userPhoneNumber);
        setUserEmail(userEmail);
        setUserPassword(userPassword);
        setRole("REGULAR_USER");
    }
}
