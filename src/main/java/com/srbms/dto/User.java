package com.srbms.dto;
import java.util.Objects;

public abstract class User {
    private String userID;
    private String userName;
    private String userPhoneNumber;
    private String userEmail;
    private String userPassword;
    private String role;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userID, user.userID) && Objects.equals(userName, user.userName) && Objects.equals(userPhoneNumber, user.userPhoneNumber) && Objects.equals(userEmail, user.userEmail) && Objects.equals(userPassword, user.userPassword) && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, userName, userPhoneNumber, userEmail, userPassword, role);
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getRole() {
        return role;
    }

    // Protected setter for role, accessible only to child classes
    protected void setRole(String role) {
        this.role = role;
    }
}


