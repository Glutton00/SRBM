package com.srbms.dto;

public class ResourceManager extends User {

    public ResourceManager(String userID, String userName, String userPhoneNumber, String userEmail, String userPassword) {
        setUserID(userID);
        setUserName(userName);
        setUserPhoneNumber(userPhoneNumber);
        setUserEmail(userEmail);
        setUserPassword(userPassword);
        setRole("RESOURCE_MANAGER");
    }
}
