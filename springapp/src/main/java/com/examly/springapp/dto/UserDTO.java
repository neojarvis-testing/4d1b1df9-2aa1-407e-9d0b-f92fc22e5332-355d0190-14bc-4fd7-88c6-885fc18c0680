package com.examly.springapp.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserDTO {

    @Email(message = "Invalid email format.")//Email Validation
    private String email;

    @Pattern(regexp = "^[A-Za-z0-9]{8,}$",
    message = "Password must contain atleast 1 letter and 1 numeric character")   //Password size validation, minimum size should be 8.             
    private String password;

    @NotBlank(message = "Username cannot be empty")
    @Pattern(regexp = "^[A-Za-z0-9_]*$",
    message ="Username should contain alphabets, numeric characters and underscores")
    private String username;

    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits long.")
    private String mobileNumber;

    @Pattern(regexp = "^(ADMIN|USER)$", message = "User role must be 'ADMIN' or 'USER'")
    private String userRole;

    //Default constructor
public UserDTO() { }

    //Parameterised constructor
public UserDTO(String email, String password, String username, String mobileNumber, String userRole) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.mobileNumber = mobileNumber;
        this.userRole = userRole;
    }

    //getter and setters    
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
