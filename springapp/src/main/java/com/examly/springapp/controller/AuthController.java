package com.examly.springapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.config.JwtUtils;
import com.examly.springapp.dto.LoginDTO;
import com.examly.springapp.dto.UserDTO;
import com.examly.springapp.model.TokenDTO;
import com.examly.springapp.service.UserServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final UserServiceImpl userService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    /* Endpoint to register a new user */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> userRegister(@Valid @RequestBody UserDTO userDTO) {
        userDTO = userService.registerUser(userDTO);
        return ResponseEntity.status(201).body(userDTO);
    }

    /* Endpoint to login a user and generate a JWT token */
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> loginUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.genrateToken(authentication);
        TokenDTO tokenDTO = userService.makeTokenDto(loginDTO, token);
        return ResponseEntity.status(200).body(tokenDTO);
    }

    /* Endpoint to get all users */
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> list = userService.getAllUsers();
        return ResponseEntity.status(200).body(list);
    }
}