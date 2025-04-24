package com.examly.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.examly.springapp.dto.UserDTO;
import com.examly.springapp.exceptions.UserNotFoundException;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;
import com.examly.springapp.utility.UserMapper;

@Service
public class UserServiceImpl {
    @Autowired
    UserRepo userRepo;

    public  UserDTO registerUser(UserDTO userDTO) {
        
        User user = UserMapper.mapToUser(userDTO);
        User saved = userRepo.save(user);
        return UserMapper.mapToUserDTO(saved);
    }

    public  UserDTO loginUser(UserDTO userDTO) {
       UserDTO existingUserDTO = userRepo.findByEmail(userDTO.getEmail());
       if(existingUserDTO==null){
        throw new UserNotFoundException("User not found.");
       }
       if(existingUserDTO.getPassword().equals(userDTO.getPassword())){
        return existingUserDTO;
       }
       throw new UserNotFoundException("Invalid credentials.");
    }
}

