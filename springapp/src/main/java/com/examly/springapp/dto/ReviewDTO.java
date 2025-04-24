package com.examly.springapp.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReviewDTO {
 private Long reviewId;
 private String reviewText;
 private Integer rating;
 private LocalDate date;
 private Long productId;
 private Long userId;
}