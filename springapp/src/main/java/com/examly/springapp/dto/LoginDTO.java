package com.examly.springapp.dto;

public class LoginDTO {
    private String username;
    private String token;
    private String email;     
    private String password;
    private String userRole;


    public LoginDTO() {
    }

    public LoginDTO(String username,String token, String email, String password,String userRole) {
        this.username = username;
        this.token = token;
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

}
