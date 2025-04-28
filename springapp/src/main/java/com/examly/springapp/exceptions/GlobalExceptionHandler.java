// package com.examly.springapp.exceptions;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.ControllerAdvice;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.validation.FieldError;
// import org.springframework.web.bind.MethodArgumentNotValidException;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;


// @ControllerAdvice
// public class GlobalExceptionHandler  {

//     @ExceptionHandler(UserNotFoundException.class)
//     public ResponseEntity<?> exception1(UserNotFoundException e){
//         return ResponseEntity.status(400).body(e.getMessage());
//     }

//     @ExceptionHandler(UsernameAlreadyExist.class)
//     public ResponseEntity<?> exception2(UsernameAlreadyExist e){
//         return ResponseEntity.status(400).body(e.getMessage());
//     }

//     @ExceptionHandler(MethodArgumentNotValidException.class)
//     public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException e) {
//         List<FieldError> errors = e.getBindingResult().getFieldErrors();
//         Map<String, String> map = new HashMap<>();
//         for(FieldError err: errors){
//             map.put(err.getField(), err.getDefaultMessage());
//         }
//         return ResponseEntity.status(400).body(map.toString());
//     }
    
//     @ExceptionHandler(ProductNotFoundException.class)
//     public ResponseEntity<?> exception3(ProductNotFoundException e){
//         return ResponseEntity.status(400).body(e.getMessage());
//     }

//     @ExceptionHandler(ReviewNotFoundException.class)
//     public ResponseEntity<?> exception4(ReviewNotFoundException e){
//         return ResponseEntity.status(400).body(e.getMessage());
//     }
// } 

package com.examly.springapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("        super(message);\r\n" + //
                "", e.getMessage()));
    }

    @ExceptionHandler(UsernameAlreadyExist.class)
    public ResponseEntity<Map<String, String>> handleUsernameAlreadyExistException(UsernameAlreadyExist e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError err : errors) {
            errorMap.put(err.getField(), err.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFoundException(ProductNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleReviewNotFoundException(ReviewNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleOrderNotFoundException(OrderNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
    }
}
