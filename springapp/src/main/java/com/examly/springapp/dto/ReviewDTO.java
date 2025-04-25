package com.examly.springapp.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReviewDTO {
    private Long reviewId;

    @NotBlank(message = "Review text cannot be blank")
    @Size(max = 500, message = "Review text cannot exceed 500 characters")
    private String reviewText;

    @NotNull(message = "Rating cannot be null")
    @Positive(message = "Rating must be positive")
    @Max(value = 5,message = "Rating must be below 5")
    private Integer rating;

    @NotNull(message = "Date cannot be null")
    private LocalDate date;

    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    @NotNull(message = "User ID cannot be null")
    private Long userId;
}
