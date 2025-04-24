package com.examly.springapp.dto;


public class LoginDTO {
    // private long userId;
    
    private String token;
    private String email;     
    private String password;
    private String userRole;


    public LoginDTO() {
    }

    public LoginDTO(Long userId,String token, String email, String password,String userRole) {
        this.token = token;
        // this.userId = userId;
        this.email = email;
        this.password = password;
        this.userRole= userRole;
 
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public String getUserRole() {
        return userRole;
    }
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    // public long getUserId() {
    //     return userId;
    // }

    // public void setUserId(long userId) {
    //     this.userId = userId;
    // }

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

}
