package com.examly.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.examly.springapp.dto.LoginDTO;
import com.examly.springapp.dto.UserDTO;

public interface UserService {

    public  UserDTO registerUser(UserDTO userDTO);
    public LoginDTO loginUser(LoginDTO loginDTO);
}
