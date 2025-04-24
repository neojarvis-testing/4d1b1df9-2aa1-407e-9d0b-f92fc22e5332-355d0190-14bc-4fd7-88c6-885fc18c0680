package com.examly.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.examly.springapp.dto.UserDTO;
import com.examly.springapp.exceptions.UserNotFoundException;
import com.examly.springapp.exceptions.UsernameAlreadyExist;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;
import com.examly.springapp.utility.UserMapper;

@Service
public class UserServiceImpl {
    @Autowired
    UserRepo userRepo;

    public  UserDTO registerUser(UserDTO userDTO) {
        User user = UserMapper.mapToUser(userDTO);
        if(userRepo.existsByUserName(userDTO.getUsername())){
            throw new UsernameAlreadyExist("Username already exists.");
        }
        User saved = userRepo.save(user);
        return UserMapper.mapToUserDTO(saved);
    }

    public  UserDTO loginUser(UserDTO userDTO) {
       User existingUser = userRepo.findByEmail(userDTO.getEmail());
       if(existingUser==null){
        throw new UserNotFoundException("User not found.");
       }
       if(existingUser.getPassword().equals(userDTO.getPassword())){
        return UserMapper.mapToUserDTO(existingUser);
       }
       throw new UserNotFoundException("Invalid credentials.");
    }
}

