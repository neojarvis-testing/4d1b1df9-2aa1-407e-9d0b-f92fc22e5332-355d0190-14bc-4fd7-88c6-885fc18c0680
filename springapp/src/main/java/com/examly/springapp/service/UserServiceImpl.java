package com.examly.springapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.examly.springapp.config.UserPrinciple;
import com.examly.springapp.dto.LoginDTO;
import com.examly.springapp.dto.UserDTO;
import com.examly.springapp.exceptions.UserNotFoundException;
import com.examly.springapp.exceptions.UsernameAlreadyExist;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;
import com.examly.springapp.utility.UserMapper;

@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder encoder;

    public  UserDTO registerUser(UserDTO userDTO) {
        User user = UserMapper.mapToUser(userDTO);
        user.setPassword(encoder.encode(user.getPassword()));
            if(userRepo.findByEmailOrUser(userDTO.getEmail(), userDTO.getUsername()).isPresent()){
            throw new UsernameAlreadyExist("User with this email or username already exists.");
        }

        User saved = userRepo.save(user);
        return UserMapper.mapToUserDTO(saved);
    }

    // public LoginDTO loginUser(LoginDTO loginDTO) {
        
    //    User existingUser = userRepo.findByEmail(loginDTO.getEmail());
    //    if(existingUser==null){
    //     throw new UserNotFoundException("User not found.");
    //    }
     
    //     if(encoder.matches(loginDTO.getPassword(), existingUser.getPassword())){
    //          return UserMapper.mapToLoginDTO(existingUser);
    //    }
    //    throw new UserNotFoundException("Invalid credentials.");
    // }

    @Override
     public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      User user = userRepo.findByEmail(email);
      if(user!=null){
          UserDetails userDetails = UserPrinciple.build(user);
          return userDetails;
      }
        throw new UserNotFoundException("User not found for the email: "+email);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users=userRepo.findAll();
        return users.stream().map(UserMapper::mapToUserDTO).collect(Collectors.toList());
    }
}