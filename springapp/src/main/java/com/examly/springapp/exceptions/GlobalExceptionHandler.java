package com.examly.springapp.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler  {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> exception1(UserNotFoundException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(UsernameAlreadyExist.class)
    public ResponseEntity<?> exception2(UsernameAlreadyExist e){
        return ResponseEntity.status(400).body(e.getMessage());
    }
}
