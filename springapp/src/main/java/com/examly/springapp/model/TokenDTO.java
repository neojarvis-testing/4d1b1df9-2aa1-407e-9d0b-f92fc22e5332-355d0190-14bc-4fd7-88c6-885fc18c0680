package com.examly.springapp.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
    
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotBlank(message = "User role cannot be blank")
    private String userRole;

    @NotBlank(message = "Token cannot be blank")
    private String token;


}    
