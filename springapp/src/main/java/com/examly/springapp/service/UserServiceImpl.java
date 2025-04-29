package com.examly.springapp.service;

import java.util.List;
import java.util.stream.Collectors;

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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    private final PasswordEncoder encoder;

    /* Method to register a new user */
    public UserDTO registerUser(UserDTO userDTO) {
        log.info("Attempting to register new user: {}", userDTO.getUsername());
        User user = UserMapper.mapToUser(userDTO);
        user.setPassword(encoder.encode(user.getPassword()));
        /* Check if a user with the same email or username already exists */
        if (userRepo.findByEmailOrUser(userDTO.getEmail(), userDTO.getUsername()).isPresent()) {
            log.error("User with email: {} or username: {} already exists", userDTO.getEmail(), userDTO.getUsername());
            throw new UsernameAlreadyExist("User with this email or username already exists.");
        }
        /* Save the new user to the database */
        User saved = userRepo.save(user);
        log.info("User registered successfully: {}", saved);
        return UserMapper.mapToUserDTO(saved);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        /* Find the user by email */
        log.info("Loading user by email: {}", email);
        User user = userRepo.findByEmail(email);
        if (user != null) {
            /* Build and return UserDetails object */
            log.info("User found for email: {}", email);
            UserDetails userDetails = UserPrinciple.build(user);
            return userDetails;
        }
        log.error("User not found for email: {}", email);
        throw new UserNotFoundException("User not found for the email: " + email);
    }

    /* Method to get all users */
    public List<UserDTO> getAllUsers() {
        log.info("Retrieving all users...");
        List<User> users = userRepo.findAll();
        log.info("Total users retrieved: {}", users.size());
        return users.stream().map(UserMapper::mapToUserDTO).collect(Collectors.toList());
    }

    /* Method to create a TokenDTO object */
    public TokenDTO makeTokenDto(LoginDTO loginDTO, String token) {
        log.info("Creating TokenDTO for user with email: {}", loginDTO.getEmail());
        User existingUser = userRepo.findByEmail(loginDTO.getEmail());
        log.info("TokenDTO created successfully");
        return new TokenDTO(existingUser.getUserId(), existingUser.getUserRole(), token);
    }
}