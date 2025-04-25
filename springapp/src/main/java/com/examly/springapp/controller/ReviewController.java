package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.ReviewDTO;
import com.examly.springapp.service.ReviewServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reviews") // Add version 
public class ReviewController {

    @Autowired // Injects the instance of service automatically
    ReviewServiceImpl reviewService;

    @PostMapping // Handles post requests
    public ResponseEntity<ReviewDTO> addReview(@Valid @RequestBody ReviewDTO reviewDTO) { // @Valid handles validations
        ReviewDTO saved = reviewService.addReview(reviewDTO); // Calls service method and passes the user data
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping("/{reviewId}") // Returns review with corresponding id
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable long reviewId) {
        ReviewDTO reviewDTO = reviewService.getReviewById(reviewId);
        return ResponseEntity.status(200).body(reviewDTO);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Boolean> deleteReviewById(@PathVariable long reviewId) {
        boolean result = reviewService.deleteReviewById(reviewId);
        if (result)
            return ResponseEntity.status(204).body(true);
        else
            return ResponseEntity.status(404).body(false);
    }

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        return ResponseEntity.status(200).body(reviews);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByUserId(@PathVariable long userId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByUserId(userId);
        return ResponseEntity.status(200).body(reviews);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByProductId(@PathVariable long productId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByProductId(productId);
        return ResponseEntity.status(200).body(reviews);
    }

}
