package com.examly.springapp.service;

import java.util.List;
import java.util.stream.Collectors;

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
import com.examly.springapp.model.TokenDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;
import com.examly.springapp.utility.UserMapper;

@Service
public class UserServiceImpl implements UserDetailsService {

    /* Autowiring the UserRepo to handle database operations */
    @Autowired
    UserRepo userRepo;

    /* Autowiring the PasswordEncoder to handle password encoding */
    @Autowired
    PasswordEncoder encoder;

    /* Method to register a new user */
    public UserDTO registerUser(UserDTO userDTO) {
        User user = UserMapper.mapToUser(userDTO);
        user.setPassword(encoder.encode(user.getPassword()));
        /* Check if a user with the same email or username already exists */
        if (userRepo.findByEmailOrUser(userDTO.getEmail(), userDTO.getUsername()).isPresent()) {
            throw new UsernameAlreadyExist("User with this email or username already exists.");
        }
        /* Save the new user to the database */
        User saved = userRepo.save(user);
        return UserMapper.mapToUserDTO(saved);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        /* Find the user by email */
        User user = userRepo.findByEmail(email);
        if (user != null) {
            /* Build and return UserDetails object */
            UserDetails userDetails = UserPrinciple.build(user);
            return userDetails;
        }
        throw new UserNotFoundException("User not found for the email: " + email);
    }

    /* Method to get all users */
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream().map(UserMapper::mapToUserDTO).collect(Collectors.toList());
    }

    /* Method to create a TokenDTO object */
    public TokenDTO makeTokenDto(LoginDTO loginDTO, String token) {
        User existingUser = userRepo.findByEmail(loginDTO.getEmail());
        return new TokenDTO(existingUser.getUserId(), existingUser.getUserRole(), token);
    }
}