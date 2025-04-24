package com.examly.springapp.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler  {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> exception1(UserNotFoundException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<?> exception2(UserAlreadyExists e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        Map<String, String> map = new HashMap<>();
        for(FieldError err: errors){
            map.put(err.getField(), err.getDefaultMessage());
        }
        return ResponseEntity.status(400).body(map.toString());
    }
} 

