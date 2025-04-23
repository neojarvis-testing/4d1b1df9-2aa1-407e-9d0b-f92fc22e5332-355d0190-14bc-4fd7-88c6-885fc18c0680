package com.examly.springapp.dto;

import jakarta.validation.constraints.Email;

public class UserDTO {
    private long userId;
    @Email(message = "Invalid email format")            //Email Validation 
    private String email;
    private String password;
    private String username;
    private String mobileNumber;
    private String userRole;

    //Default constructor
public UserDTO() {
    }

    //Parameterised constructor
public UserDTO(long userId, String email, String password, String username, String mobileNumber, String userRole) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.username = username;
        this.mobileNumber = mobileNumber;
        this.userRole = userRole;
    }

    //getter and setters
public long getUserId() {
    return userId;
}
public void setUserId(long userId) {
    this.userId = userId;
}
public String getEmail() {
    return email;
}
public void setEmail(String email) {
    this.email = email;
}
public String getPassword() {
    return password;
}
public void setPassword(String password) {
    this.password = password;
}
public String getUsername() {
    return username;
}
public void setUsername(String username) {
    this.username = username;
}
public String getMobileNumber() {
    return mobileNumber;
}
public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
}
public String getUserRole() {
    return userRole;
}
public void setUserRole(String userRole) {
    this.userRole = userRole;
}
}
