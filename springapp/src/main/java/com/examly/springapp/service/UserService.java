package com.examly.springapp.service;
import com.examly.springapp.dto.LoginDTO;
import com.examly.springapp.dto.UserDTO;

/*Defining the UserService interface
 * Method to register a new user and
 * Method to log in an existing user
*/
public interface UserService {

    public  UserDTO registerUser(UserDTO userDTO);
    public LoginDTO loginUser(LoginDTO loginDTO);
}
