package com.examly.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.UserDTO;
import com.examly.springapp.service.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO>userRegister(@Valid @RequestBody UserDTO userDTO ){
        userDTO = userService.registerUser(userDTO);
        return ResponseEntity.status(201).body(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO>userLogin(@Valid @RequestBody UserDTO userDTO){
        userDTO = userService.loginUser(userDTO);
        return ResponseEntity.status(201).body(userDTO);
    }
}
