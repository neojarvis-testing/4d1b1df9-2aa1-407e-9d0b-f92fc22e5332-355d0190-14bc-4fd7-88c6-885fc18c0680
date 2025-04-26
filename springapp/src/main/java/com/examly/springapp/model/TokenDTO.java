package com.examly.springapp.model;

public class TokenDTO {
 
    private long userId;
    private String userRole;
    private String token;
    public TokenDTO(long userId, java.lang.String userRole, String token) {
        this.userId = userId;
        this.userRole = userRole;
        this.token = token;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getUserRole() {
        return userRole;
    }
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}    
