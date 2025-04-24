package com.examly.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.examly.springapp.dto.LoginDTO;
import com.examly.springapp.dto.UserDTO;
import com.examly.springapp.exceptions.UserAlreadyExists;
import com.examly.springapp.exceptions.UserNotFoundException;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;
import com.examly.springapp.utility.UserMapper;

@Service
public class UserServiceImpl {
    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder encoder;

    public  UserDTO registerUser(UserDTO userDTO) {
        User user = UserMapper.mapToUser(userDTO);
        user.setPassword(encoder.encode(user.getPassword()));

        //check if a user with same email already exists.
        User existingUser =userRepo.findByEmail(userDTO.getEmail());
        if(existingUser != null){
            throw new UserAlreadyExists("User with this email already exists.");
        }

        //check if a user with same username already exists
        User existingUserByUsername = userRepo.findByUsername(userDTO.getUsername());
        if(existingUserByUsername != null){
            throw new UserAlreadyExists("User with this username already exists."); 
        }
        User saved = userRepo.save(user);
        return UserMapper.mapToUserDTO(saved);
    }

    public LoginDTO loginUser(LoginDTO loginDTO) {
        
       User existingUser = userRepo.findByEmail(loginDTO.getEmail());
       if(existingUser==null){
        throw new UserNotFoundException("User not found.");
       }
     
        if(encoder.matches(loginDTO.getPassword(), existingUser.getPassword())){
             return UserMapper.mapToLoginDTO(existingUser);
       }
       throw new UserNotFoundException("Invalid credentials.");
    }
}

